package com.ssafy.live_service.api.service.auctionevent;

import com.ssafy.live_service.api.service.auctionevent.request.BidServiceRequest;
import com.ssafy.live_service.api.service.auctionevent.response.AuctionEventResponse;
import com.ssafy.live_service.api.service.auctionevent.vo.BidInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class AuctionEventService {

    private final RedisTemplate<String, BidInfo> redisTemplate;

    public boolean addQueue(String memberKey, BidServiceRequest request, LocalDateTime current, Long millis) {
        BidInfo bidInfo = request.toValue(memberKey, current);
        redisTemplate.opsForZSet().add(request.getKey(), bidInfo, millis);
        return true;
    }

    public AuctionEventResponse publish(Long auctionVarietyId) {
        return null;
    }
}
