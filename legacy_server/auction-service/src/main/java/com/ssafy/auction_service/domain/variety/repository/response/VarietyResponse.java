package com.ssafy.auction_service.domain.variety.repository.response;

import com.ssafy.auction_service.domain.variety.PlantCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VarietyResponse {

    private String code;
    private PlantCategory plantCategory;
    private String itemName;
    private String varietyName;

    @Builder
    private VarietyResponse(String code, PlantCategory plantCategory, String itemName, String varietyName) {
        this.code = code;
        this.plantCategory = plantCategory;
        this.itemName = itemName;
        this.varietyName = varietyName;
    }
}
