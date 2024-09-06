package com.ssafy.auction_service.api.service.variety.request;

import com.ssafy.auction_service.domain.variety.PlantCategory;
import com.ssafy.auction_service.domain.variety.Variety;
import com.ssafy.auction_service.domain.variety.VarietyInfo;
import lombok.Builder;
import lombok.Getter;

import static com.ssafy.auction_service.api.service.variety.VarietyUtils.*;

@Getter
public class VarietyCreateServiceRequest {

    private final String plantCategory;
    private final String itemName;
    private final String varietyName;

    @Builder
    private VarietyCreateServiceRequest(String plantCategory, String itemName, String varietyName) {
        this.plantCategory = plantCategory;
        this.itemName = itemName;
        this.varietyName = varietyName;
    }

    public static VarietyCreateServiceRequest of(String plantCategory, String itemName, String varietyName) {
        return new VarietyCreateServiceRequest(plantCategory, itemName, varietyName);
    }

    public Variety toEntity(Long createdBy, String code) {
        valid();
        return Variety.create(createdBy, code, PlantCategory.of(plantCategory), itemName, varietyName);
    }

    public VarietyInfo getVarietyInfo() {
        return VarietyInfo.of(getPlantCategory(), itemName, varietyName);
    }

    public PlantCategory getPlantCategory() {
        return PlantCategory.of(plantCategory);
    }

    private void valid() {
        validatePlantCategory(plantCategory);
        validateItemName(itemName);
        validateVarietyName(varietyName);
    }
}
