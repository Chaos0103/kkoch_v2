package com.ssafy.auction_service.api.service.auctionschedule;

import com.ssafy.auction_service.api.ListResponse;
import com.ssafy.auction_service.domain.auctionschedule.repository.AuctionScheduleQueryRepository;
import com.ssafy.auction_service.domain.auctionschedule.repository.dto.AuctionScheduleSearchCond;
import com.ssafy.auction_service.domain.auctionschedule.repository.response.AuctionScheduleDetailResponse;
import com.ssafy.auction_service.domain.auctionschedule.repository.response.AuctionScheduleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuctionScheduleQueryService {

    private final AuctionScheduleQueryRepository auctionScheduleQueryRepository;

    public ListResponse<AuctionScheduleResponse> searchAuctionSchedulesByCond(AuctionScheduleSearchCond cond) {
        List<AuctionScheduleResponse> content = auctionScheduleQueryRepository.findByCond(cond);

        return ListResponse.of(content);
    }

    public AuctionScheduleDetailResponse searchAuctionSchedule(int auctionScheduleId) {
        return null;
    }
}
