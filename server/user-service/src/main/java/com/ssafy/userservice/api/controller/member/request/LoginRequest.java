package com.ssafy.userservice.api.controller.member.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRequest {

    private final String email;
    private final String password;

    @Builder
    private LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
