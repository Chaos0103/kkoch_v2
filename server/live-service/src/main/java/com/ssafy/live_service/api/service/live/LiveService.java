package com.ssafy.live_service.api.service.live;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.live_service.api.client.AuctionServiceClient;
import com.ssafy.live_service.api.client.TradeServiceClient;
import com.ssafy.live_service.api.client.response.ReservationVarietyResponse;
import com.ssafy.live_service.api.service.auction.AuctionProgressService;
import com.ssafy.live_service.api.service.auction.vo.AuctionVariety;
import com.ssafy.live_service.api.service.live.vo.Json;
import com.ssafy.live_service.api.service.live.vo.SessionInfo;
import com.ssafy.live_service.api.service.live.vo.Sessions;
import com.ssafy.live_service.common.exception.AppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class LiveService extends TextWebSocketHandler {

    private static final Map<String, Sessions> sessionMap = new ConcurrentHashMap<>(); //웹소켓 세션 저장소(key: 경매ID, value: 경매에 참가한 회원의 세션 정보 목록)
    private static final Map<String, String> videoSessionMap = new ConcurrentHashMap<>(); //비디오 세션 저장소(key: 경매ID, value: 비디오 세션)
    private static final Map<String, SessionInfo> auctionMasterAdminMap = new ConcurrentHashMap<>(); //경매 관리자(key: 경매ID, value: 경매를 최초로 오픈한 관리자 세션)

    private final AuctionProgressService auctionProgressService;
    private final AuctionServiceClient auctionServiceClient;
    private final TradeServiceClient tradeServiceClient;

    /**
     * 경매방 입장
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        if (session.getUri() == null) {
            return;
        }

        log.debug("웹소켓 연결 (sessionId = {})", session.getId());
        String auctionScheduleId = getAuctionScheduleIdByUrl(session.getUri());

        //경매의 비디오 세션이 발급됬으면
        if (!videoSessionMap.get(auctionScheduleId).isEmpty()) {
            session.sendMessage(new TextMessage(videoSessionMap.get(auctionScheduleId)));
        }

        sessionMap.putIfAbsent(auctionScheduleId, Sessions.create());
        Sessions sessions = sessionMap.get(auctionScheduleId);
        sessions.add(session);
        sessionMap.put(auctionScheduleId, sessions);
    }

    /**
     * 메세지 통신
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.debug("메세지 통신 (sessionId = {})", session.getId());
        String msg = message.getPayload();
        Json json = Json.of(msg);

        Role role = json.getRole();

        if (role.isUser()) {
            client(session, message);
            return;
        }

        if (role.isAdmin()) {
            admin(session, json);
        }
    }

    /**
     * 경매방 퇴장
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.debug("웹소켓 연결 종료 (sessionId = {})", session.getId());
        if (session.getUri() == null) {
            return;
        }
        String auctionScheduleId = getAuctionScheduleIdByUrl(session.getUri());

        String sessionId = session.getId();
        Sessions sessions = sessionMap.get(auctionScheduleId);
        sessions.remove(sessionId);

        if (auctionMasterAdminMap.get(auctionScheduleId).eqSessionId(sessionId)) {
            auctionMasterAdminMap.remove(auctionScheduleId);
            sendEndMessage(auctionScheduleId);
            sessionMap.remove(auctionScheduleId);
            videoSessionMap.remove(auctionScheduleId);
        }
    }

    private void client(WebSocketSession session, TextMessage message) throws IOException {
        if (session.getUri() == null) {
            return;
        }

        String auctionScheduleId = getAuctionScheduleIdByUrl(session.getUri());

        if (isNotProgressAuction(auctionScheduleId)) {
            sessionMap.remove(auctionScheduleId);
            session.sendMessage(new TextMessage("진행중인 경매가 아닙니다."));
            return;
        }

        log.debug("모든 세션에 메세지 전송");
        sendMessageToAllSession(auctionScheduleId, message.getPayload());
    }

    private void sendMessageToAllSession(String auctionScheduleId, String msg) {
        sessionMap.get(auctionScheduleId)
            .sendMessage(msg);
    }

    private boolean isNotProgressAuction(String auctionScheduleId) {
        return !videoSessionMap.containsKey(auctionScheduleId);
    }

    private void admin(WebSocketSession session, Json json) {
        if (session.getUri() == null) {
            return;
        }

        String auctionScheduleId = getAuctionScheduleIdByUrl(session.getUri());
        Command cmd = json.getCmd();

        if (cmd.isOpen()) {
            auctionMasterAdminMap.put(auctionScheduleId, SessionInfo.of(session));
            String videoSessionId = json.getVideoSessionId();
            videoSessionMap.put(auctionScheduleId, videoSessionId);
            log.info("[{}] 경매장 웹소켓 오픈", auctionScheduleId);

            //경매품 전체 조회 요청
            List<AuctionVariety> auctionVarieties = auctionProgressService.initAuctionVariety(Integer.parseInt(auctionScheduleId));
            log.info("[{}] 경매 품종 조회 성공 (경매 품종 {}개)", auctionScheduleId, auctionVarieties.size());

            //경매장 상태 변경 요청
            auctionServiceClient.modifyAuctionStatusToProgress(Integer.parseInt(auctionScheduleId));
            log.info("[{}] 경매장 상태 변경 -> PROGRESS", auctionScheduleId);
            return;
        }

        if (cmd.isStart()) {
            //경매품 메세지 반환
        }

        if (cmd.isNext()) {
            AuctionVariety nextVariety = auctionProgressService.getNextVariety(Integer.parseInt(auctionScheduleId));
            String jsonValue = objToJson(nextVariety);
            sendMessageToAllSession(auctionScheduleId, jsonValue);
            log.info("[{}] 다음 경매 품종 전송", auctionScheduleId);

            //예약 상품
            ReservationVarietyResponse reservationVariety = tradeServiceClient.searchReservationVariety(Integer.parseInt(auctionScheduleId), nextVariety.getVarietyCode());
            if (reservationVariety == null) {
                return;
            }
            String reservationVarietyJson = objToJson(reservationVariety);
            auctionMasterAdminMap.get(auctionScheduleId)
                .sendMessage(reservationVarietyJson);
            log.info("[{}] 예약 경매 정보 전송", auctionScheduleId);
            return;
        }

        if (cmd.isClose()) {
            auctionServiceClient.modifyAuctionStatusToComplete(Integer.parseInt(auctionScheduleId));
            sessionMap.remove(auctionScheduleId);
            videoSessionMap.remove(auctionScheduleId);
            auctionMasterAdminMap.remove(auctionScheduleId);
            log.info("[{}] 경매장 웹소켓 종료", auctionScheduleId);
        }
    }

    private String getAuctionScheduleIdByUrl(URI uri) {
        String path = uri.getPath();
        String[] pathSplit = path.split("/");
        return pathSplit[pathSplit.length - 1];
    }

    private String objToJson(Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new AppException(e);
        }
    }

    private void sendEndMessage(String auctionScheduleId) {
        sessionMap.get(auctionScheduleId)
            .sendMessage("금일 경매가 종료되었습니다.");
    }
}
