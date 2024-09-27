package com.ssafy.live_service.api.service.auctionevent;

import com.ssafy.live_service.api.service.auctionevent.request.BidServiceRequest;
import com.ssafy.live_service.api.service.auctionevent.response.AuctionEventResponse;
import com.ssafy.live_service.api.service.auctionevent.vo.BidInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Set;

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
        String key = String.valueOf(auctionVarietyId);
        try {
            Set<BidInfo> bidInfos = redisTemplate.opsForZSet().range(key, 0, 0);
            if (CollectionUtils.isEmpty(bidInfos)) {
                return null;
            }

            return bidInfos.stream()
                .map(bidInfo -> AuctionEventResponse.of(bidInfo.getBidPrice()))
                .toList()
                .get(0);
        } finally {
            Long size = redisTemplate.opsForZSet().size(key);
            redisTemplate.opsForZSet().removeRange(key, 0, size);
        }
    }
}
