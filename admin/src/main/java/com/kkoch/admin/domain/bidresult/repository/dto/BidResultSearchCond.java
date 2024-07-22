package com.kkoch.admin.domain.bidresult.repository.dto;

import com.kkoch.admin.domain.variety.PlantCategory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BidResultSearchCond {

    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final PlantCategory plantCategory;
    private final String itemName;
    private final String varietyName;
    private final String region;

    @Builder
    private BidResultSearchCond(LocalDateTime startDateTime, LocalDateTime endDateTime, PlantCategory plantCategory, String itemName, String varietyName, String region) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.plantCategory = plantCategory;
        this.itemName = itemName;
        this.varietyName = varietyName;
        this.region = region;
    }

    public static BidResultSearchCond of(LocalDateTime startDateTime, LocalDateTime endDateTime, PlantCategory plantCategory, String itemName, String varietyName, String region) {
        return new BidResultSearchCond(startDateTime, endDateTime, plantCategory, itemName, varietyName, region);
    }
}
