package com.ssafy.user_service.api.service.member.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BusinessNumberValidateResponse {

    private String tel;
    private Boolean isAvailable;
    private LocalDateTime validatedDateTime;

    @Builder
    private BusinessNumberValidateResponse(String tel, Boolean isAvailable, LocalDateTime validatedDateTime) {
        this.tel = tel;
        this.isAvailable = isAvailable;
        this.validatedDateTime = validatedDateTime;
    }

    public static BusinessNumberValidateResponse of(String tel, Boolean isAvailable, LocalDateTime validatedDateTime) {
        return new BusinessNumberValidateResponse(tel, isAvailable, validatedDateTime);
    }
}
