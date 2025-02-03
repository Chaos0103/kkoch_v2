package com.ssafy.userservice.api.service.auth.response;

import com.ssafy.userservice.domain.member.vo.Tel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TelAuthenticationResultResponse {

    private final String tel;
    private final Boolean isAvailable;
    private final LocalDateTime validatedDateTime;

    @Builder
    private TelAuthenticationResultResponse(String tel, Boolean isAvailable, LocalDateTime validatedDateTime) {
        this.tel = tel;
        this.isAvailable = isAvailable;
        this.validatedDateTime = validatedDateTime;
    }

    public static TelAuthenticationResultResponse of(Tel tel, boolean isExist, LocalDateTime currentDateTime) {
        return new TelAuthenticationResultResponse(tel.getTel(), !isExist, currentDateTime);
    }
}
