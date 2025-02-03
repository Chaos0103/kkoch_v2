package com.ssafy.userservice.api.service.auth.response;

import com.ssafy.userservice.domain.member.vo.Email;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EmailAuthenticationResultResponse {

    private final String email;
    private final Boolean isAvailable;
    private final LocalDateTime validatedDateTime;

    @Builder
    private EmailAuthenticationResultResponse(String email, Boolean isAvailable, LocalDateTime validatedDateTime) {
        this.email = email;
        this.isAvailable = isAvailable;
        this.validatedDateTime = validatedDateTime;
    }

    public static EmailAuthenticationResultResponse of(Email email, boolean isExist, LocalDateTime currentDateTime) {
        return new EmailAuthenticationResultResponse(email.getEmail(), !isExist, currentDateTime);
    }
}
