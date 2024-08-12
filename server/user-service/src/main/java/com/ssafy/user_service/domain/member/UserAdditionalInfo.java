package com.ssafy.user_service.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class UserAdditionalInfo {

    @Column(unique = true, length = 12)
    private final String businessNumber;

    @Embedded
    private final BankAccount bankAccount;

    @Builder
    private UserAdditionalInfo(String businessNumber, BankAccount bankAccount) {
        this.businessNumber = businessNumber;
        this.bankAccount = bankAccount;
    }

    public static UserAdditionalInfo of(String businessNumber, BankAccount bankAccount) {
        return new UserAdditionalInfo(businessNumber, bankAccount);
    }

    public static UserAdditionalInfo init(String businessNumber) {
        return of(businessNumber, null);
    }

    public UserAdditionalInfo withBankAccount(String bankCode, String accountNumber) {
        BankAccount bankAccount = BankAccount.of(bankCode, accountNumber);
        return of(businessNumber, bankAccount);
    }
}
