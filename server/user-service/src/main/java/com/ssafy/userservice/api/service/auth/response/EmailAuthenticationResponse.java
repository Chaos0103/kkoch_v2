package com.ssafy.userservice.api.service.auth.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EmailAuthenticationResponse {

    private final LocalDateTime expiredDateTime;

    @Builder
    private EmailAuthenticationResponse(LocalDateTime expiredDateTime) {
        this.expiredDateTime = expiredDateTime;
    }

    public static EmailAuthenticationResponse of(LocalDateTime expiredDateTime) {
        return new EmailAuthenticationResponse(expiredDateTime);
    }
}
