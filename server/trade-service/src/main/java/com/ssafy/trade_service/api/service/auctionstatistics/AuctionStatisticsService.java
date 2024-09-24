package com.ssafy.trade_service.api.service.auctionstatistics;

import com.ssafy.trade_service.api.service.auctionstatistics.response.AuctionStatisticsCreateResponse;
import com.ssafy.trade_service.domain.auctionstatistics.AuctionStatistics;
import com.ssafy.trade_service.domain.auctionstatistics.PriceCalculatedResult;
import com.ssafy.trade_service.domain.auctionstatistics.repository.AuctionStatisticsRepository;
import com.ssafy.trade_service.domain.bidresult.BidResult;
import com.ssafy.trade_service.domain.bidresult.repository.BidResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AuctionStatisticsService {

    private final AuctionStatisticsRepository auctionStatisticsRepository;
    private final BidResultRepository bidResultRepository;

    public AuctionStatisticsCreateResponse calculateAuctionStatistics(LocalDate date) {
        List<BidResult> bidResults = bidResultRepository.findAllByBidDateTime(date.atStartOfDay(), date.plusDays(1).atStartOfDay());

        Map<String, List<BidResult>> bidResultMap = bidResults.stream()
            .collect(Collectors.groupingBy(BidResult::getVarietyCode));

        List<AuctionStatistics> auctionStatistics = new ArrayList<>();

        List<String> plantGrades = List.of("SUPER", "ADVANCED", "BASIC");
        Set<String> keySet = bidResultMap.keySet();
        for (String varietyCode : keySet) {
            List<BidResult> bidResultByVarietyCode = bidResultMap.get(varietyCode);
            for (String plantGrade : plantGrades) {
                List<BidResult> filterPlantGrade = bidResultByVarietyCode.stream()
                    .filter(bidResult -> plantGrade.equals(bidResult.getPlantGrade()))
                    .toList();

                if (filterPlantGrade.isEmpty()) {
                    continue;
                }

                int max = filterPlantGrade.stream()
                    .mapToInt(BidResult::getBidPrice)
                    .max()
                    .orElse(0);

                int min = filterPlantGrade.stream()
                    .mapToInt(BidResult::getBidPrice)
                    .min()
                    .orElse(0);

                int totalPlantCount = filterPlantGrade.stream()
                    .mapToInt(BidResult::getPlantCount)
                    .sum();

                int totalBidPrice = filterPlantGrade.stream()
                    .mapToInt(b -> b.getPlantCount() * b.getBidPrice())
                    .sum();

                int avg = totalBidPrice / totalPlantCount;

                PriceCalculatedResult priceCalculatedResult = PriceCalculatedResult.of(avg, max, min);
                auctionStatistics.add(AuctionStatistics.create(varietyCode, plantGrade, totalPlantCount, priceCalculatedResult));
            }
        }

        List<AuctionStatistics> savedAuctionStatistics = auctionStatisticsRepository.saveAll(auctionStatistics);

        return AuctionStatisticsCreateResponse.of(savedAuctionStatistics, date);
    }
}
