package com.ssafy.userservice.api.controller.auth.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import static com.ssafy.userservice.api.controller.BindingMessage.MemberMessage.NOT_BLANK_EMAIL;

@Getter
public class SendEmailAuthenticationNumberRequest {

    @NotBlank(message = NOT_BLANK_EMAIL)
    private final String email;

    @Builder
    private SendEmailAuthenticationNumberRequest(String email) {
        this.email = email;
    }
}
