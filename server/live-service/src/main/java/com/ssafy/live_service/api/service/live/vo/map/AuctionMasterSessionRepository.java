package com.ssafy.live_service.api.service.live.vo.map;

import org.springframework.web.socket.WebSocketSession;

public interface AuctionMasterSessionRepository {

    void save(String auctionScheduleId, WebSocketSession session);

    void remove(String auctionScheduleId);

    void sendMessage(String auctionScheduleId, String msg);

    boolean isMaster(String auctionScheduleId, WebSocketSession session);
}
