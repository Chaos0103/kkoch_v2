package com.ssafy.auction_service.api.controller.variety.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VarietyCreateRequest {

    private String category;
    private String itemName;
    private String varietyName;

    @Builder
    private VarietyCreateRequest(String category, String itemName, String varietyName) {
        this.category = category;
        this.itemName = itemName;
        this.varietyName = varietyName;
    }
}
