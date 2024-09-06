package com.ssafy.auction_service.api.service.variety.response;

import com.ssafy.auction_service.domain.variety.Variety;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class VarietyModifyResponse {

    private String code;
    private String plantCategory;
    private String itemName;
    private String varietyName;
    private LocalDateTime modifiedDateTime;

    @Builder
    private VarietyModifyResponse(String code, String plantCategory, String itemName, String varietyName, LocalDateTime modifiedDateTime) {
        this.code = code;
        this.plantCategory = plantCategory;
        this.itemName = itemName;
        this.varietyName = varietyName;
        this.modifiedDateTime = modifiedDateTime;
    }

    public static VarietyModifyResponse of(Variety variety, LocalDateTime current) {
        return new VarietyModifyResponse(variety.getCode(), variety.getKoreanPlantCategory(), variety.getItemName(), variety.getVarietyName(), current);
    }
}
