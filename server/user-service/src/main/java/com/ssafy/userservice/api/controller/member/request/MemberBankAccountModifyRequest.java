package com.ssafy.userservice.api.controller.member.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import static com.ssafy.userservice.api.controller.BindingMessage.MemberMessage.NOT_BLANK_AUTHENTICATION_NUMBER;

@Getter
public class MemberBankAccountModifyRequest {

    @NotBlank(message = NOT_BLANK_AUTHENTICATION_NUMBER)
    private final String authenticationNumber;

    @Builder
    private MemberBankAccountModifyRequest(String authenticationNumber) {
        this.authenticationNumber = authenticationNumber;
    }
}
