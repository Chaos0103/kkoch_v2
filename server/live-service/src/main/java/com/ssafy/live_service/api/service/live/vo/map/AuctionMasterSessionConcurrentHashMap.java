package com.ssafy.live_service.api.service.live.vo.map;

import com.ssafy.live_service.common.exception.AppException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AuctionMasterSessionConcurrentHashMap implements AuctionMasterSessionRepository {

    private static final Map<String, WebSocketSession> map = new ConcurrentHashMap<>();

    @Override
    public void save(String auctionScheduleId, WebSocketSession session) {
        map.put(auctionScheduleId, session);
    }

    @Override
    public void remove(String auctionScheduleId) {
        map.remove(auctionScheduleId);
    }

    @Override
    public void sendMessage(String auctionScheduleId, String msg) {
        try {
            map.get(auctionScheduleId)
                .sendMessage(new TextMessage(msg));
        } catch (IOException e) {
            throw new AppException(e);
        }
    }

    public boolean isMaster(String auctionScheduleId, WebSocketSession session) {
        return map.get(auctionScheduleId).getId().equals(session.getId());
    }
}
