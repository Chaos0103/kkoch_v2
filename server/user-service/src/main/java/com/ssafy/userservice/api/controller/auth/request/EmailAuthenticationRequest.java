package com.ssafy.userservice.api.controller.auth.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import static com.ssafy.userservice.api.controller.BindingMessage.MemberMessage.NOT_BLANK_AUTHENTICATION_NUMBER;
import static com.ssafy.userservice.api.controller.BindingMessage.MemberMessage.NOT_BLANK_EMAIL;

@Getter
public class EmailAuthenticationRequest {

    @NotBlank(message = NOT_BLANK_EMAIL)
    private final String email;

    @NotBlank(message = NOT_BLANK_AUTHENTICATION_NUMBER)
    private final String authenticationNumber;

    @Builder
    private EmailAuthenticationRequest(String email, String authenticationNumber) {
        this.email = email;
        this.authenticationNumber = authenticationNumber;
    }
}
