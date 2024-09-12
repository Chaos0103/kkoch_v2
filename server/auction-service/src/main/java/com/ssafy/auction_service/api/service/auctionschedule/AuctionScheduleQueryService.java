package com.ssafy.auction_service.api.service.auctionschedule;

import com.ssafy.auction_service.api.ListResponse;
import com.ssafy.auction_service.domain.auctionschedule.repository.AuctionScheduleQueryRepository;
import com.ssafy.auction_service.domain.auctionschedule.repository.dto.AuctionScheduleSearchCond;
import com.ssafy.auction_service.domain.auctionschedule.repository.response.AuctionScheduleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuctionScheduleQueryService {

    private final AuctionScheduleQueryRepository auctionScheduleQueryRepository;

    public ListResponse<AuctionScheduleResponse> searchAuctionSchedulesByCond(AuctionScheduleSearchCond cond) {
        return null;
    }
}
