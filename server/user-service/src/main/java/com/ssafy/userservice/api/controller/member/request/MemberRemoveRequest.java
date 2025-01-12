package com.ssafy.userservice.api.controller.member.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import static com.ssafy.userservice.api.controller.BindingMessage.MemberMessage.NOT_BLANK_PASSWORD;

@Getter
public class MemberRemoveRequest {

    @NotBlank(message = NOT_BLANK_PASSWORD)
    private final String password;

    @Builder
    private MemberRemoveRequest(String password) {
        this.password = password;
    }
}
