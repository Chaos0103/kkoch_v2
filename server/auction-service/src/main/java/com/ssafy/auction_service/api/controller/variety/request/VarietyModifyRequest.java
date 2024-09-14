package com.ssafy.auction_service.api.controller.variety.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VarietyModifyRequest {

    private String varietyName;

    @Builder
    private VarietyModifyRequest(String varietyName) {
        this.varietyName = varietyName;
    }
}
