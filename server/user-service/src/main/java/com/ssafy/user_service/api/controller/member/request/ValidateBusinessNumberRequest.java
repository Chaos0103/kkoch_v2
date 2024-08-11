package com.ssafy.user_service.api.controller.member.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ValidateBusinessNumberRequest {

    private String businessNumber;

    @Builder
    private ValidateBusinessNumberRequest(String businessNumber) {
        this.businessNumber = businessNumber;
    }
}
