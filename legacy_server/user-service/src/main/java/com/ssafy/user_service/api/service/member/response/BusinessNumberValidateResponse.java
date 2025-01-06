package com.ssafy.user_service.api.service.member.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BusinessNumberValidateResponse {

    private String businessNumber;
    private Boolean isAvailable;
    private LocalDateTime validatedDateTime;

    @Builder
    private BusinessNumberValidateResponse(String businessNumber, Boolean isAvailable, LocalDateTime validatedDateTime) {
        this.businessNumber = businessNumber;
        this.isAvailable = isAvailable;
        this.validatedDateTime = validatedDateTime;
    }

    public static BusinessNumberValidateResponse of(String businessNumber, Boolean isAvailable, LocalDateTime validatedDateTime) {
        return new BusinessNumberValidateResponse(businessNumber, isAvailable, validatedDateTime);
    }
}
