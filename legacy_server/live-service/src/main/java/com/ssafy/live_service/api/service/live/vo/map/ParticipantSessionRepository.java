package com.ssafy.live_service.api.service.live.vo.map;

import org.springframework.web.socket.WebSocketSession;

public interface ParticipantSessionRepository {

    void init(String auctionScheduleId);

    void addSession(String auctionScheduleId, WebSocketSession session);

    void removeSession(String auctionScheduleId, WebSocketSession session);

    void remove(String auctionScheduleId);

    void sendMessage(String auctionScheduleId, String msg);

    void sendAuctionCompleteMessage(String auctionScheduleId);
}
