package com.ssafy.userservice.api.service.auth.vo;

import com.ssafy.userservice.domain.member.vo.BankAccount;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class BankAccountAuthentication implements Serializable {

    private final String authenticationNumber;
    private final String bankCode;
    private final String accountNumber;

    @Builder
    private BankAccountAuthentication(String authenticationNumber, String bankCode, String accountNumber) {
        this.authenticationNumber = authenticationNumber;
        this.bankCode = bankCode;
        this.accountNumber = accountNumber;
    }

    public static BankAccountAuthentication of(String authenticationNumber, BankAccount bankAccount) {
        return new BankAccountAuthentication(authenticationNumber, bankAccount.getBankCode(), bankAccount.getAccountNumber());
    }

    public BankAccount getBankAccount() {
        return BankAccount.of(bankCode, accountNumber);
    }
}
