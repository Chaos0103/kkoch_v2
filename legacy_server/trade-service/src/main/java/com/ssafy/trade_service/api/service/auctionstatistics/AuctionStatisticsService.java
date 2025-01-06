package com.ssafy.trade_service.api.service.auctionstatistics;

import com.ssafy.trade_service.api.service.auctionstatistics.response.AuctionStatisticsCreateResponse;
import com.ssafy.trade_service.api.service.auctionstatistics.vo.BidResults;
import com.ssafy.trade_service.domain.auctionstatistics.AuctionStatistics;
import com.ssafy.trade_service.domain.auctionstatistics.repository.AuctionStatisticsRepository;
import com.ssafy.trade_service.domain.bidresult.BidResult;
import com.ssafy.trade_service.domain.bidresult.repository.BidResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AuctionStatisticsService {

    private static final Set<String> PLANT_GRADES = Set.of("SUPER", "ADVANCED", "NORMAL");
    private final AuctionStatisticsRepository auctionStatisticsRepository;
    private final BidResultRepository bidResultRepository;

    public AuctionStatisticsCreateResponse calculateAuctionStatistics(LocalDate date) {
        BidResults bidResults = findBidResults(date);

        Map<String, BidResults> bidResultMap = bidResults.generateBidResultsMap();

        List<AuctionStatistics> auctionStatistics = new ArrayList<>();
        for (Map.Entry<String, BidResults> entry : bidResultMap.entrySet()) {
            String varietyCode = entry.getKey();
            BidResults bidResultByVarietyCode = entry.getValue();

            PLANT_GRADES.stream()
                .map(plantGrade -> bidResultByVarietyCode.calculate(varietyCode, plantGrade, date))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(auctionStatistics::add);
        }

        List<AuctionStatistics> savedAuctionStatistics = auctionStatisticsRepository.saveAll(auctionStatistics);
        return AuctionStatisticsCreateResponse.of(savedAuctionStatistics, date);
    }

    private BidResults findBidResults(LocalDate date) {
        LocalDateTime from = date.atStartOfDay();
        LocalDateTime to = from.plusDays(1);
        List<BidResult> bidResultList = bidResultRepository.findAllByBidDateTime(from, to);
        return BidResults.of(bidResultList);
    }
}
