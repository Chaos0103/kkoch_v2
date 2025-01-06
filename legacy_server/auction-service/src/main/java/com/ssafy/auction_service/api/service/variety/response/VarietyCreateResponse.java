package com.ssafy.auction_service.api.service.variety.response;

import com.ssafy.auction_service.domain.variety.Variety;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class VarietyCreateResponse {

    private String code;
    private String plantCategory;
    private String itemName;
    private String varietyName;
    private LocalDateTime createdDateTime;

    @Builder
    private VarietyCreateResponse(String code, String plantCategory, String itemName, String varietyName, LocalDateTime createdDateTime) {
        this.code = code;
        this.plantCategory = plantCategory;
        this.itemName = itemName;
        this.varietyName = varietyName;
        this.createdDateTime = createdDateTime;
    }

    public static VarietyCreateResponse of(String code, String plantCategory, String itemName, String varietyName, LocalDateTime createdDateTime) {
        return new VarietyCreateResponse(code, plantCategory, itemName, varietyName, createdDateTime);
    }

    public static VarietyCreateResponse of(Variety variety) {
        return of(variety.getCode(), variety.getKoreanPlantCategory(), variety.getItemName(), variety.getVarietyName(), variety.getCreatedDateTime());
    }
}
