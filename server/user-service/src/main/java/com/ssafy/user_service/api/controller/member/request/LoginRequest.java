package com.ssafy.user_service.api.controller.member.request;

import lombok.Getter;

@Getter
public class LoginRequest {

    private String email;
    private String password;
}
