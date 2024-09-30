package com.ssafy.live_service.api.service.live.vo.map;

import com.ssafy.live_service.api.service.live.vo.Sessions;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ParticipantSessionConcurrentHashMap implements ParticipantSessionRepository {

    private static final Map<String, Sessions> map = new ConcurrentHashMap<>();

    @Override
    public void init(String auctionScheduleId) {
        map.putIfAbsent(auctionScheduleId, Sessions.create());
    }

    @Override
    public void addSession(String auctionScheduleId, WebSocketSession session) {
        Sessions sessions = map.get(auctionScheduleId);
        sessions.add(session);
        map.put(auctionScheduleId, sessions);
    }

    @Override
    public void removeSession(String auctionScheduleId, WebSocketSession session) {
        Sessions sessions = map.get(auctionScheduleId);
        sessions.remove(session);
    }

    @Override
    public void remove(String auctionScheduleId) {
        map.remove(auctionScheduleId);
    }

    @Override
    public void sendMessage(String auctionScheduleId, String msg) {
        map.get(auctionScheduleId)
            .sendMessage(msg);
    }

    @Override
    public void sendAuctionCompleteMessage(String auctionScheduleId) {
        sendMessage(auctionScheduleId, "금일 경매가 종료되었습니다.");
    }
}
