package com.ssafy.auction_service.api.service.variety.response;

import com.ssafy.auction_service.domain.variety.Variety;
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

    public static VarietyRemoveResponse of(Variety variety) {
        return new VarietyRemoveResponse(
            variety.getCode(),
            variety.getKoreanPlantCategory(),
            variety.getItemName(),
            variety.getVarietyName()
        );
    }
}
