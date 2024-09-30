package com.ssafy.live_service.api.service.live.vo.map;

public interface VideoSessionRepository {

    void save(String auctionScheduleId, String videoSessionId);

    void remove(String auctionScheduleId);

    boolean existsSessionBy(String auctionScheduleId);

    String get(String auctionScheduleId);
}
