package com.ssafy.auction_service.domain.variety.repository.cond;

import com.ssafy.auction_service.domain.variety.PlantCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
public class VarietySearchCond {

    private final PlantCategory plantCategory;
    private final String itemName;

    @Builder
    private VarietySearchCond(PlantCategory plantCategory, String itemName) {
        this.plantCategory = plantCategory;
        this.itemName = itemName;
    }

    public static VarietySearchCond of(String plantCategory, String itemName) {
        return new VarietySearchCond(PlantCategory.of(plantCategory), itemName);
    }
}
