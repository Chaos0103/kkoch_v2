package com.ssafy.userservice.domain.member.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class BankAccount {

    @Column(columnDefinition = "char(3)", length = 3)
    private final String bankCode;

    @Column(length = 14)
    private final String accountNumber;

    @Builder
    private BankAccount(String bankCode, String accountNumber) {
        //TODO: 기능 구현
//        validateBankCode(bankCode);
//        validateAccountNumber(accountNumber);

        this.bankCode = bankCode;
        this.accountNumber = accountNumber;
    }

    public static BankAccount of(String bankCode, String accountNumber) {
        return new BankAccount(bankCode, accountNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return Objects.equals(bankCode, that.bankCode) && Objects.equals(accountNumber, that.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bankCode, accountNumber);
    }
}
