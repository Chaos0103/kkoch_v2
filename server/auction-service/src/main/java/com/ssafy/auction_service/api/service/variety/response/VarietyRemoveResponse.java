package com.ssafy.auction_service.api.service.variety.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VarietyRemoveResponse {

    private String code;
    private String plantCategory;
    private String itemName;
    private String varietyName;

    @Builder
    private VarietyRemoveResponse(String code, String plantCategory, String itemName, String varietyName) {
        this.code = code;
        this.plantCategory = plantCategory;
        this.itemName = itemName;
        this.varietyName = varietyName;
    }
}
