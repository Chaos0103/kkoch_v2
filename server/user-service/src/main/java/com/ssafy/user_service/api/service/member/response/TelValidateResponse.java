package com.ssafy.user_service.api.service.member.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TelValidateResponse {

    private String tel;
    private Boolean isAvailable;
    private LocalDateTime validatedDateTime;

    @Builder
    private TelValidateResponse(String tel, Boolean isAvailable, LocalDateTime validatedDateTime) {
        this.tel = tel;
        this.isAvailable = isAvailable;
        this.validatedDateTime = validatedDateTime;
    }

    public static TelValidateResponse of(String tel, Boolean isAvailable, LocalDateTime validatedDateTime) {
        return new TelValidateResponse(tel, isAvailable, validatedDateTime);
    }
}
