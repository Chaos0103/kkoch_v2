package com.ssafy.trade_service.api.service.auctionstatistics;

import com.ssafy.trade_service.api.ListResponse;
import com.ssafy.trade_service.domain.auctionstatistics.repository.AuctionStatisticsQueryRepository;
import com.ssafy.trade_service.domain.auctionstatistics.repository.cond.AuctionStatisticsSearchCond;
import com.ssafy.trade_service.domain.auctionstatistics.repository.response.AuctionStatisticsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuctionStatisticsQueryService {

    private final AuctionStatisticsQueryRepository auctionStatisticsQueryRepository;

    public final ListResponse<AuctionStatisticsResponse> searchAuctionStatistics(String varietyCode, AuctionStatisticsSearchCond cond) {
        return null;
    }
}
