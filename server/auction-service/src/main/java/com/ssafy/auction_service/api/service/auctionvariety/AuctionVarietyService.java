package com.ssafy.auction_service.api.service.auctionvariety;

import com.ssafy.auction_service.api.client.MemberServiceClient;
import com.ssafy.auction_service.api.service.auctionvariety.request.AuctionVarietyCreateServiceRequest;
import com.ssafy.auction_service.api.service.auctionvariety.response.AuctionVarietyCreateResponse;
import com.ssafy.auction_service.domain.auctionvariety.repository.AuctionVarietyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuctionVarietyService {

    private final AuctionVarietyRepository auctionVarietyRepository;
    private final MemberServiceClient memberServiceClient;

    public AuctionVarietyCreateResponse createAuctionVariety(String varietyCode, int auctionScheduleId, AuctionVarietyCreateServiceRequest request) {
        return null;
    }
}
