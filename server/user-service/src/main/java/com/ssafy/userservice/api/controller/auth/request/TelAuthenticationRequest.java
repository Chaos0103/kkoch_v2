package com.ssafy.userservice.api.controller.auth.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import static com.ssafy.userservice.api.controller.BindingMessage.MemberMessage.NOT_BLANK_TEL;

@Getter
public class TelAuthenticationRequest {

    @NotBlank(message = NOT_BLANK_TEL)
    private final String tel;

    @Builder
    private TelAuthenticationRequest(String tel) {
        this.tel = tel;
    }
}
