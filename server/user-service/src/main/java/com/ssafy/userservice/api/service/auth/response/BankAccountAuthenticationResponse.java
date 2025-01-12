package com.ssafy.userservice.api.service.auth.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BankAccountAuthenticationResponse {

    private final LocalDateTime expiredDateTime;

    @Builder
    private BankAccountAuthenticationResponse(LocalDateTime expiredDateTime) {
        this.expiredDateTime = expiredDateTime;
    }

    public static BankAccountAuthenticationResponse of(LocalDateTime expiredDateTime) {
        return new BankAccountAuthenticationResponse(expiredDateTime);
    }
}
