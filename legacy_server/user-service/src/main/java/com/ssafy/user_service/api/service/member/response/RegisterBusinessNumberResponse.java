package com.ssafy.user_service.api.service.member.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RegisterBusinessNumberResponse {

    private final String businessNumber;
    private final LocalDateTime registeredDateTime;

    @Builder
    private RegisterBusinessNumberResponse(String businessNumber, LocalDateTime registeredDateTime) {
        this.businessNumber = businessNumber;
        this.registeredDateTime = registeredDateTime;
    }

    public static RegisterBusinessNumberResponse of(String businessNumber, LocalDateTime registeredDateTime) {
        return RegisterBusinessNumberResponse.builder()
            .businessNumber(businessNumber)
            .registeredDateTime(registeredDateTime)
            .build();
    }
}
