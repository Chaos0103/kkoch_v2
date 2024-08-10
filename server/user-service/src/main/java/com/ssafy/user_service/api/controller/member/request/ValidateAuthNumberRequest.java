package com.ssafy.user_service.api.controller.member.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ValidateAuthNumberRequest {

    private String email;
    private String authNumber;

    @Builder
    private ValidateAuthNumberRequest(String email, String authNumber) {
        this.email = email;
        this.authNumber = authNumber;
    }
}
