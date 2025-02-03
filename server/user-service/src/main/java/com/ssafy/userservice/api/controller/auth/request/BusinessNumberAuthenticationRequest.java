package com.ssafy.userservice.api.controller.auth.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import static com.ssafy.userservice.api.controller.BindingMessage.MemberMessage.NOT_BLANK_BUSINESS_NUMBER;

@Getter
public class BusinessNumberAuthenticationRequest {

    @NotBlank(message = NOT_BLANK_BUSINESS_NUMBER)
    private final String businessNumber;

    @Builder
    private BusinessNumberAuthenticationRequest(String businessNumber) {
        this.businessNumber = businessNumber;
    }
}
