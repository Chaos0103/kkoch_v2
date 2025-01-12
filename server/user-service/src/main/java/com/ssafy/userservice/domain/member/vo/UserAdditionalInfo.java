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
    private UserAdditionalInfo(BusinessNumber businessNumber, BankAccount bankAccount) {
        this.businessNumber = businessNumber;
        this.bankAccount = bankAccount;
    }

    public static UserAdditionalInfo of(BusinessNumber businessNumber, BankAccount bankAccount) {
        return new UserAdditionalInfo(businessNumber, bankAccount);
    }

    public static UserAdditionalInfo create(BusinessNumber businessNumber) {
        return of(businessNumber, null);
    }

    public UserAdditionalInfo withBankAccount(BankAccount bankAccount) {
        return of(businessNumber, bankAccount);
    }

    public String getBusinessNumber() {
        return businessNumber == null ? null : businessNumber.getBusinessNumber();
    }
}
