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

    private static final String AUCTION_EVENT_KEY_FORMAT = "AUCTION-EVENT-%d";
    private static final int START_INDEX = 0;
    private static final int END_INDEX = 0;

    private final RedisTemplate<String, BidInfo> redisTemplate;

    public boolean addQueue(String memberKey, BidServiceRequest request, LocalDateTime current, Long millis) {
        BidInfo bidInfo = request.toValue(memberKey, current);
        redisTemplate.opsForZSet().add(request.getKey(AUCTION_EVENT_KEY_FORMAT), bidInfo, millis);
        return true;
    }

    public AuctionEventResponse publish(Long auctionVarietyId) {
        String key = generateKey(auctionVarietyId);
        try {
            Set<BidInfo> bidInfos = redisTemplate.opsForZSet().range(key, START_INDEX, END_INDEX);
            if (CollectionUtils.isEmpty(bidInfos)) {
                return null;
            }

            return bidInfos.stream()
                .map(bidInfo -> AuctionEventResponse.of(bidInfo.getBidPrice()))
                .toList()
                .get(START_INDEX);
        } finally {
            redisClear(key);
        }
    }

    private String generateKey(Long auctionVarietyId) {
        return String.format(AUCTION_EVENT_KEY_FORMAT, auctionVarietyId);
    }

    private void redisClear(String key) {
        redisTemplate.delete(key);
    }
}
