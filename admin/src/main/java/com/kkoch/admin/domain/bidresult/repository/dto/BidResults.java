package com.kkoch.admin.domain.bidresult.repository.dto;

import com.kkoch.admin.domain.Grade;
import com.kkoch.admin.domain.stats.Price;
import com.kkoch.admin.domain.stats.Stats;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BidResults {

    private final List<BidResultDto> bidResults;

    private BidResults(List<BidResultDto> bidResults) {
        this.bidResults = bidResults;
    }

    public static BidResults of(List<BidResultDto> bidResults) {
        return new BidResults(bidResults);
    }

    public List<Stats> getPriceMap(LocalDate currentDate) {
        //key: 품종, value: Dto
        List<Stats> entities = new ArrayList<>();

        Map<String, List<BidResultDto>> bidResultMap = createMap();
        for (String varietyCode : bidResultMap.keySet()) {
            List<BidResultDto> bidResultsFromVarietyCode = bidResultMap.get(varietyCode);

            //Key: 등급, value: Dto
            Map<Grade, List<BidResultDto>> map = createMapBy(bidResultsFromVarietyCode);
            for (Grade grade : map.keySet()) {
                List<BidResultDto> bidResultsFromGrade = map.get(grade);

                Price price = getPriceBy(bidResultsFromGrade);

                int plantCount = getTotalPlantCountBy(bidResultsFromGrade);

                Stats stats = Stats.create(grade, plantCount, price, currentDate, varietyCode);
                entities.add(stats);
            }
        }

        return entities;
    }

    private Map<String, List<BidResultDto>> createMap() {
        return bidResults.stream()
            .collect(Collectors.groupingBy(BidResultDto::getVarietyCode));
    }

    private Map<Grade, List<BidResultDto>> createMapBy(List<BidResultDto> bidResults) {
        return bidResults.stream()
            .collect(Collectors.groupingBy(BidResultDto::getGrade));
    }

    private Price getPriceBy(List<BidResultDto> bidResults) {
        IntStream stream = bidResults.stream()
            .mapToInt(BidResultDto::getBidPrice);

        int maximumBidPrice = getMaximumBidPriceBy(stream);
        int minimumBidPrice = getMinimumBidPriceBy(stream);
        int averageBidPrice = getAverageBidPriceBy(stream);

        return Price.of(averageBidPrice, maximumBidPrice, minimumBidPrice);
    }

    private int getMaximumBidPriceBy(IntStream stream) {
        return stream.max()
            .orElse(0);
    }

    private int getMinimumBidPriceBy(IntStream stream) {
        return stream.min()
            .orElse(0);
    }

    private int getAverageBidPriceBy(IntStream stream) {
        return (int) stream.average()
            .orElse(0);
    }

    private int getTotalPlantCountBy(List<BidResultDto> bidResults) {
        return bidResults.stream()
            .mapToInt(BidResultDto::getPlantCount)
            .sum();
    }
}
