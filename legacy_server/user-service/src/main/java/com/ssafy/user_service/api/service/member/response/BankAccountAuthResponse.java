package com.ssafy.user_service.api.service.member.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BankAccountAuthResponse {

    private LocalDateTime expiredDateTime;

    @Builder
    private BankAccountAuthResponse(LocalDateTime expiredDateTime) {
        this.expiredDateTime = expiredDateTime;
    }

    public static BankAccountAuthResponse of(LocalDateTime expiredDateTime) {
        return new BankAccountAuthResponse(expiredDateTime);
    }
}
