package com.ssafy.user_service.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class BankAccount {

    @Column(columnDefinition = "char(3)", length = 3)
    private final String bankCode;

    @Column(length = 14)
    private final String accountNumber;

    @Builder
    private BankAccount(String bankCode, String accountNumber) {
        this.bankCode = bankCode;
        this.accountNumber = accountNumber;
    }

    public static BankAccount of(String bankCode, String accountNumber) {
        return new BankAccount(bankCode, accountNumber);
    }
}
