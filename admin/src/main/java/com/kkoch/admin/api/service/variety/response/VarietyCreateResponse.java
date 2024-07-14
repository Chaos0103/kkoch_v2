package com.kkoch.admin.api.service.variety.response;

import com.kkoch.admin.domain.variety.PlantCategory;
import com.kkoch.admin.domain.variety.Variety;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class VarietyCreateResponse {

    private final String varietyCode;
    private final PlantCategory plantCategory;
    private final String itemName;
    private final String varietyName;
    private final LocalDateTime createdDateTime;

    @Builder
    private VarietyCreateResponse(String varietyCode, PlantCategory plantCategory, String itemName, String varietyName, LocalDateTime createdDateTime) {
        this.varietyCode = varietyCode;
        this.plantCategory = plantCategory;
        this.itemName = itemName;
        this.varietyName = varietyName;
        this.createdDateTime = createdDateTime;
    }

    public static VarietyCreateResponse of(Variety variety) {
        return VarietyCreateResponse.builder()
            .varietyCode(variety.getCode())
            .plantCategory(variety.getPlantCategory())
            .itemName(variety.getItemName())
            .varietyName(variety.getVarietyName())
            .createdDateTime(variety.getCreatedDateTime())
            .build();
    }
}
