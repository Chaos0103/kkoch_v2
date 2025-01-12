package com.ssafy.userservice.api.controller.member.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import static com.ssafy.userservice.api.controller.BindingMessage.MemberMessage.NOT_BLANK_ACCOUNT_NUMBER;
import static com.ssafy.userservice.api.controller.BindingMessage.MemberMessage.NOT_BLANK_BANK_CODE;

@Getter
public class SendBankAccountAuthenticationNumberRequest {

    @NotBlank(message = NOT_BLANK_BANK_CODE)
    private final String bankCode;

    @NotBlank(message = NOT_BLANK_ACCOUNT_NUMBER)
    private final String accountNumber;

    @Builder
    private SendBankAccountAuthenticationNumberRequest(String bankCode, String accountNumber) {
        this.bankCode = bankCode;
        this.accountNumber = accountNumber;
    }
}
