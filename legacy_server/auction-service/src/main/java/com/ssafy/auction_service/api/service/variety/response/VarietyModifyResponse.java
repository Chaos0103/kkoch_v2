package com.ssafy.auction_service.api.service.variety.response;

import com.ssafy.auction_service.domain.variety.Variety;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VarietyModifyResponse {

    private String code;
    private String plantCategory;
    private String itemName;
    private String varietyName;

    @Builder
    private VarietyModifyResponse(String code, String plantCategory, String itemName, String varietyName) {
        this.code = code;
        this.plantCategory = plantCategory;
        this.itemName = itemName;
        this.varietyName = varietyName;
    }

    public static VarietyModifyResponse of(Variety variety) {
        return new VarietyModifyResponse(variety.getCode(), variety.getKoreanPlantCategory(), variety.getItemName(), variety.getVarietyName());
    }
}
