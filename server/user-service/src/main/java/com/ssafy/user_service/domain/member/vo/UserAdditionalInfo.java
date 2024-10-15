package com.ssafy.user_service.domain.member.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static com.ssafy.user_service.domain.member.MemberValidate.validateBusinessNumber;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class UserAdditionalInfo {

    @Column(unique = true, length = 12)
    private final String businessNumber;

    @Embedded
    private final BankAccount bankAccount;

    @Builder
    private UserAdditionalInfo(String businessNumber, BankAccount bankAccount) {
        this.businessNumber = validateBusinessNumber(businessNumber);
        this.bankAccount = bankAccount;
    }

    public static UserAdditionalInfo of(String businessNumber, BankAccount bankAccount) {
        return UserAdditionalInfo.builder()
            .businessNumber(businessNumber)
            .build();
    }

    public static UserAdditionalInfo create(String businessNumber) {
        return of(businessNumber, null);
    }

    public UserAdditionalInfo withBankAccount(String bankCode, String accountNumber) {
        BankAccount modifiedBackAccount = BankAccount.of(bankCode, accountNumber);
        return of(businessNumber, modifiedBackAccount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAdditionalInfo that = (UserAdditionalInfo) o;
        return Objects.equals(businessNumber, that.businessNumber) && Objects.equals(bankAccount, that.bankAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(businessNumber, bankAccount);
    }
}
