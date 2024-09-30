package com.ssafy.live_service.api.service.live;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.live_service.api.client.AuctionServiceClient;
import com.ssafy.live_service.api.client.TradeServiceClient;
import com.ssafy.live_service.api.client.response.ReservationVarietyResponse;
import com.ssafy.live_service.api.service.auction.AuctionProgressService;
import com.ssafy.live_service.api.service.auction.vo.AuctionVariety;
import com.ssafy.live_service.api.service.live.vo.Json;
import com.ssafy.live_service.api.service.live.vo.map.AuctionMasterSessionRepository;
import com.ssafy.live_service.api.service.live.vo.map.ParticipantSessionRepository;
import com.ssafy.live_service.api.service.live.vo.map.VideoSessionRepository;
import com.ssafy.live_service.common.exception.AppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private final ParticipantSessionRepository participantSessionRepository;
    private final VideoSessionRepository videoSessionRepository;
    private final AuctionMasterSessionRepository auctionMasterSessionRepository;
    private final AuctionProgressService auctionProgressService;
    private final AuctionServiceClient auctionServiceClient;
    private final TradeServiceClient tradeServiceClient;

    /**
     * 경매방 입장
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.debug("웹소켓 연결 (sessionId = {})", session.getId());
        String auctionScheduleId = getAuctionScheduleIdByUrl(session);

        //경매의 비디오 세션이 발급됬으면
        if (!videoSessionRepository.existsSessionBy(auctionScheduleId)) {
            String videoSessionId = videoSessionRepository.get(auctionScheduleId);
            session.sendMessage(new TextMessage(videoSessionId));
        }

        participantSessionRepository.init(auctionScheduleId);
        participantSessionRepository.addSession(auctionScheduleId, session);
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
        String auctionScheduleId = getAuctionScheduleIdByUrl(session);

        if (auctionMasterSessionRepository.isMaster(auctionScheduleId, session)) {
            auctionMasterSessionRepository.remove(auctionScheduleId);
            participantSessionRepository.sendAuctionCompleteMessage(auctionScheduleId);
            participantSessionRepository.remove(auctionScheduleId);
            videoSessionRepository.remove(auctionScheduleId);
            return;
        }

        participantSessionRepository.removeSession(auctionScheduleId, session);
    }

    private void client(WebSocketSession session, TextMessage message) throws IOException {
        String auctionScheduleId = getAuctionScheduleIdByUrl(session);

        if (!videoSessionRepository.existsSessionBy(auctionScheduleId)) {
            participantSessionRepository.remove(auctionScheduleId);
            session.sendMessage(new TextMessage("진행중인 경매가 아닙니다."));
            return;
        }

        log.debug("모든 세션에 메세지 전송");
        sendMessageToAllSession(auctionScheduleId, message.getPayload());
    }

    private void sendMessageToAllSession(String auctionScheduleId, String msg) {
        participantSessionRepository.sendMessage(auctionScheduleId, msg);
    }

    private void admin(WebSocketSession session, Json json) throws IOException {
        String auctionScheduleId = getAuctionScheduleIdByUrl(session);
        Command cmd = json.getCmd();

        if (cmd.isOpen()) {
            auctionMasterSessionRepository.save(auctionScheduleId, session);
            String videoSessionId = json.getVideoSessionId();
            videoSessionRepository.save(auctionScheduleId, videoSessionId);
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
            auctionMasterSessionRepository.sendMessage(auctionScheduleId, reservationVarietyJson);
            log.info("[{}] 예약 경매 정보 전송", auctionScheduleId);
            return;
        }

        if (cmd.isClose()) {
            auctionServiceClient.modifyAuctionStatusToComplete(Integer.parseInt(auctionScheduleId));
            participantSessionRepository.remove(auctionScheduleId);
            videoSessionRepository.remove(auctionScheduleId);
            auctionMasterSessionRepository.remove(auctionScheduleId);
            log.info("[{}] 경매장 웹소켓 종료", auctionScheduleId);
        }
    }

    private String getAuctionScheduleIdByUrl(WebSocketSession session) {
        URI uri = session.getUri();
        if (uri == null) {
            throw new AppException("주소를 올바르게 입력해주세요.");
        }
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
}
