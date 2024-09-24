package com.ssafy.trade_service.api.service.auctionstatistics;

import com.ssafy.trade_service.api.service.auctionstatistics.response.AuctionStatisticsCreateResponse;
import com.ssafy.trade_service.domain.auctionstatistics.repository.AuctionStatisticsRepository;
import com.ssafy.trade_service.domain.bidresult.repository.BidResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class AuctionStatisticsService {

    private final AuctionStatisticsRepository auctionStatisticsRepository;
    private final BidResultRepository bidResultRepository;

    public AuctionStatisticsCreateResponse calculateAuctionStatistics(LocalDate date) {
        return null;
    }
}
