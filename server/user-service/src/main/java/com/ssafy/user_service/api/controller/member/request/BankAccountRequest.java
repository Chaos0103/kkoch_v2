package com.ssafy.user_service.api.controller.member.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BankAccountRequest {

    private String bankCode;
    private String accountNumber;

    @Builder
    private BankAccountRequest(String bankCode, String accountNumber) {
        this.bankCode = bankCode;
        this.accountNumber = accountNumber;
    }
}
