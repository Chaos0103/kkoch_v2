package com.ssafy.trade_service.api.service.auctionstatistics.vo;

import com.ssafy.trade_service.domain.auctionstatistics.AuctionStatistics;
import com.ssafy.trade_service.domain.auctionstatistics.PriceCalculatedResult;
import com.ssafy.trade_service.domain.bidresult.BidResult;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class BidResults {

    private final List<BidResult> values;

    private BidResults(List<BidResult> values) {
        this.values = values;
    }

    public static BidResults of(List<BidResult> values) {
        return new BidResults(values);
    }

    public Map<String, BidResults> generateBidResultsMap() {
        Map<String, List<BidResult>> map = getBidResults().stream()
            .collect(Collectors.groupingBy(BidResult::getVarietyCode));

        Map<String, BidResults> result = new HashMap<>();
        map.forEach((key, value) -> result.put(key, of(value)));
        return result;
    }

    public Optional<AuctionStatistics> calculate(String varietyCode, String plantGrade, LocalDate calculatedDate) {
        List<BidResult> filteredBidResult = filterBy(plantGrade);
        if (filteredBidResult.isEmpty()) {
            return Optional.empty();
        }

        PriceCalculatedResult calculatedResult = calculateResult(filteredBidResult);

        int totalPlantCount = calculateTotalPlantCount(filteredBidResult);
        AuctionStatistics auctionStatistics = AuctionStatistics.create(varietyCode, plantGrade, totalPlantCount, calculatedDate, calculatedResult);
        return Optional.of(auctionStatistics);
    }

    private List<BidResult> filterBy(String plantGrade) {
        return getBidResults().stream()
            .filter(b -> plantGrade.equals(b.getPlantGrade()))
            .toList();
    }

    private PriceCalculatedResult calculateResult(List<BidResult> filteredBidResult) {
        int avg = calculateAvgBidPrice(filteredBidResult);
        int max = calculateMaxBidPrice(filteredBidResult);
        int min = calculateMinBidPrice(filteredBidResult);
        return PriceCalculatedResult.of(avg, max, min);
    }

    private int calculateMaxBidPrice(List<BidResult> bidResults) {
        return bidResults.stream()
            .mapToInt(BidResult::getBidPrice)
            .max()
            .orElse(0);
    }

    private int calculateMinBidPrice(List<BidResult> bidResults) {
        return bidResults.stream()
            .mapToInt(BidResult::getBidPrice)
            .min()
            .orElse(0);
    }

    private int calculateAvgBidPrice(List<BidResult> bidResults) {
        int totalPlantCount = calculateTotalPlantCount(bidResults);

        int totalBidPrice = bidResults.stream()
            .mapToInt(b -> b.getPlantCount() * b.getBidPrice())
            .sum();

        return totalBidPrice / totalPlantCount;
    }

    private int calculateTotalPlantCount(List<BidResult> bidResults) {
        return bidResults.stream()
            .mapToInt(BidResult::getPlantCount)
            .sum();
    }

    private List<BidResult> getBidResults() {
        return new ArrayList<>(values);
    }
}
