package com.ssafy.live_service.api.service.auction;

import com.ssafy.live_service.api.ApiResponse;
import com.ssafy.live_service.api.client.AuctionServiceClient;
import com.ssafy.live_service.api.client.response.AuctionVarietiesResponse;
import com.ssafy.live_service.api.service.auction.vo.AuctionVariety;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionProgressService {

    private static final String AUCTION_VARIETY_KEY_FORMAT = "AUCTION-VARIETY-%d";

    private final RedisTemplate<String, AuctionVariety> template;
    private final AuctionServiceClient auctionServiceClient;

    public List<AuctionVariety> initAuctionVariety(int auctionScheduleId) {
        String key = generateKey(auctionScheduleId);

        List<AuctionVariety> auctionVarieties = findAuctionVarieties(auctionScheduleId).stream()
            .map(AuctionVarietiesResponse::toVo)
            .toList();

        auctionVarieties.forEach(auctionVariety -> template.opsForList().rightPush(key, auctionVariety));

        return auctionVarieties;
    }

    public AuctionVariety getNextVariety(int auctionScheduleId) {
        String key = generateKey(auctionScheduleId);
        return template.opsForList().index(key, 0);
    }

    public AuctionVariety getOrRemoveNextVariety(int auctionScheduleId) {
        String key = generateKey(auctionScheduleId);
        return template.opsForList().leftPop(key);
    }

    private List<AuctionVarietiesResponse> findAuctionVarieties(int auctionScheduleId) {
        ApiResponse<List<AuctionVarietiesResponse>> response = auctionServiceClient.searchAuctionVarieties(auctionScheduleId);
        return response.getData();
    }

    private String generateKey(int auctionScheduleId) {
        return String.format(AUCTION_VARIETY_KEY_FORMAT, auctionScheduleId);
    }
}
