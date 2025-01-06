package com.ssafy.user_service.api.service.member.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class EmailValidateResponse {

    private String email;
    private Boolean isAvailable;
    private LocalDateTime validatedDateTime;

    @Builder
    private EmailValidateResponse(String email, Boolean isAvailable, LocalDateTime validatedDateTime) {
        this.email = email;
        this.isAvailable = isAvailable;
        this.validatedDateTime = validatedDateTime;
    }

    public static EmailValidateResponse of(String email, Boolean isAvailable, LocalDateTime validatedDateTime) {
        return new EmailValidateResponse(email, isAvailable, validatedDateTime);
    }
}
