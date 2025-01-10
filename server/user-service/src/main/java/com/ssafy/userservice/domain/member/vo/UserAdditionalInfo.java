package com.ssafy.userservice.domain.member.vo;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.*;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class UserAdditionalInfo {

    @Embedded
    private final BusinessNumber businessNumber;

    @Embedded
    private final BankAccount bankAccount;

    @Builder
    private UserAdditionalInfo(String businessNumber, BankAccount bankAccount) {
        this.businessNumber = BusinessNumber.of(businessNumber);
        this.bankAccount = bankAccount;
    }

    public static UserAdditionalInfo of(String businessNumber, BankAccount bankAccount) {
        return UserAdditionalInfo.builder()
            .businessNumber(businessNumber)
            .bankAccount(bankAccount)
            .build();
    }

    public static UserAdditionalInfo create(String businessNumber) {
        return of(businessNumber, null);
    }

    public UserAdditionalInfo withBankAccount(String bankCode, String accountNumber) {
        BankAccount modifiedBackAccount = BankAccount.of(bankCode, accountNumber);
        return of(businessNumber.getBusinessNumber(), modifiedBackAccount);
    }
}
