package com.ssafy.userservice.api.service.auth.response;

import com.ssafy.userservice.domain.member.vo.BusinessNumber;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BusinessNumberAuthenticationResultResponse {

    private final String businessNumber;
    private final Boolean isAvailable;
    private final LocalDateTime validatedDateTime;

    @Builder
    private BusinessNumberAuthenticationResultResponse(String businessNumber, Boolean isAvailable, LocalDateTime validatedDateTime) {
        this.businessNumber = businessNumber;
        this.isAvailable = isAvailable;
        this.validatedDateTime = validatedDateTime;
    }

    public static BusinessNumberAuthenticationResultResponse of(BusinessNumber businessNumber, boolean isExist, LocalDateTime currentDateTime) {
        return new BusinessNumberAuthenticationResultResponse(businessNumber.getBusinessNumber(), !isExist, currentDateTime);
    }
}
