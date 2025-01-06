package com.ssafy.user_service.api.service.member.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BankAccountServiceRequest {

    private final String bankCode;
    private final String accountNumber;

    @Builder
    private BankAccountServiceRequest(String bankCode, String accountNumber) {
        this.bankCode = bankCode;
        this.accountNumber = accountNumber;
    }

    public static BankAccountServiceRequest of(String bankCode, String accountNumber) {
        return new BankAccountServiceRequest(bankCode, accountNumber);
    }
}
