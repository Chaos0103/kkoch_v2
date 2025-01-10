package com.ssafy.userservice.domain.member.vo;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.*;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class BankAccount {

    @Embedded
    private final BankCode bankCode;

    @Embedded
    private final AccountNumber accountNumber;

    @Builder
    private BankAccount(String bankCode, String accountNumber) {
        this.bankCode = BankCode.of(bankCode);
        this.accountNumber = AccountNumber.of(accountNumber);
    }

    public static BankAccount of(String bankCode, String accountNumber) {
        return new BankAccount(bankCode, accountNumber);
    }

    public String getBankCode() {
        return bankCode.getBankCode();
    }

    public String getMaskingAccountNumber() {
        return accountNumber.masking();
    }
}
