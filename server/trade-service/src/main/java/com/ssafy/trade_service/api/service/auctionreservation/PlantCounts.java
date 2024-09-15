package com.ssafy.trade_service.api.service.auctionreservation;

import java.util.List;

public class PlantCounts {

    private final List<Integer> counts;

    private PlantCounts(List<Integer> counts) {
        this.counts = counts;
    }

    public static PlantCounts of(List<Integer> counts) {
        return new PlantCounts(counts);
    }

    public boolean isSizeMoreThan(int maxSize) {
        return counts.size() > maxSize;
    }

    public boolean isSumMoreThan(int maxValue) {
        return sum() > maxValue;
    }

    private int sum() {
        return counts.stream()
            .mapToInt(count -> count)
            .sum();
    }
}
