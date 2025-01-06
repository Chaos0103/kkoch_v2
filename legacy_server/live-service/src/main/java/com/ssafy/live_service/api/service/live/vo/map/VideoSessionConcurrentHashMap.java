package com.ssafy.live_service.api.service.live.vo.map;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class VideoSessionConcurrentHashMap implements VideoSessionRepository {

    private static final Map<String, String> map = new ConcurrentHashMap<>();

    @Override
    public void save(String auctionScheduleId, String videoSessionId) {
        map.put(auctionScheduleId, videoSessionId);
    }

    @Override
    public void remove(String auctionScheduleId) {
        map.remove(auctionScheduleId);
    }

    @Override
    public boolean existsSessionBy(String auctionScheduleId) {
        return map.containsKey(auctionScheduleId);
    }

    @Override
    public String get(String auctionScheduleId) {
        return map.get(auctionScheduleId);
    }
}
