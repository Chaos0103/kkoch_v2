package com.ssafy.auction_service.api.service.variety.request;

import lombok.Builder;

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
}
