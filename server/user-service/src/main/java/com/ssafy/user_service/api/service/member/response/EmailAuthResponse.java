package com.ssafy.user_service.api.service.member.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class EmailAuthResponse {

    private LocalDateTime expiredDateTime;

    @Builder
    private EmailAuthResponse(LocalDateTime expiredDateTime) {
        this.expiredDateTime = expiredDateTime;
    }

    public static EmailAuthResponse of(LocalDateTime expiredDateTime) {
        return new EmailAuthResponse(expiredDateTime);
    }
}
