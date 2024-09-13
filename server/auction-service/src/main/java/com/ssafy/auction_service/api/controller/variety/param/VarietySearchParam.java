package com.ssafy.auction_service.api.controller.variety.param;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VarietySearchParam {

    private String page;
    private String plantCategory;
    private String itemName;

    @Builder
    private VarietySearchParam(String page, String plantCategory, String itemName) {
        this.page = page;
        this.plantCategory = plantCategory;
        this.itemName = itemName;
    }
}
