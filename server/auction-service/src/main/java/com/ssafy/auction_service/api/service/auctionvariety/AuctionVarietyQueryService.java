package com.ssafy.auction_service.api.service.auctionvariety;

import com.ssafy.auction_service.api.PageResponse;
import com.ssafy.auction_service.domain.auctionvariety.repository.AuctionVarietyQueryRepository;
import com.ssafy.auction_service.domain.auctionvariety.repository.response.AuctionVarietyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuctionVarietyQueryService {

    private final AuctionVarietyQueryRepository auctionVarietyQueryRepository;

    public PageResponse<AuctionVarietyResponse> searchAuctionVarieties(int auctionScheduleId, int pageNumber) {
        return null;
    }
}
