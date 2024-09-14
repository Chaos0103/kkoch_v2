package com.ssafy.auction_service.api.service.variety.request;

import com.ssafy.auction_service.domain.variety.PlantCategory;
import com.ssafy.auction_service.domain.variety.Variety;
import lombok.Builder;
import lombok.Getter;

import static com.ssafy.auction_service.api.service.variety.VarietyUtils.validateItemName;
import static com.ssafy.auction_service.api.service.variety.VarietyUtils.validateVarietyName;

@Getter
public class VarietyCreateServiceRequest {

    private final PlantCategory plantCategory;
    private final String itemName;
    private final String varietyName;

    @Builder
    private VarietyCreateServiceRequest(PlantCategory plantCategory, String itemName, String varietyName) {
        this.plantCategory = plantCategory;
        this.itemName = itemName;
        this.varietyName = varietyName;
    }

    public static VarietyCreateServiceRequest of(String plantCategory, String itemName, String varietyName) {
        validateItemName(itemName);
        validateVarietyName(varietyName);
        return new VarietyCreateServiceRequest(PlantCategory.of(plantCategory), itemName, varietyName);
    }

    public Variety toEntity(int equalPlantCategoryCount) {
        return Variety.create(equalPlantCategoryCount, plantCategory, itemName, varietyName);
    }
}
