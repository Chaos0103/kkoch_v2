package com.ssafy.userservice.domain.member.vo;

import com.ssafy.common.global.exception.MemberException;
import com.ssafy.userservice.api.service.member.Masking;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.ssafy.common.global.exception.code.ErrorCode.INVALID_ACCOUNT_NUMBER;
import static com.ssafy.common.global.exception.code.ErrorCode.INVALID_ACCOUNT_NUMBER_LENGTH;
import static com.ssafy.common.global.utils.StringUtils.*;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class AccountNumber {

    private static final int MAX_ACCOUNT_NUMBER_LENGTH = 14;

    @Column(length = 14)
    private final String accountNumber;

    private AccountNumber(String accountNumber) {
        validation(accountNumber);
        this.accountNumber = accountNumber;
    }

    public static AccountNumber of(String accountNumber) {
        return new AccountNumber(accountNumber);
    }

    public String masking() {
        return Masking.maskingAccountNumber(accountNumber);
    }

    @Override
    public String toString() {
        return accountNumber;
    }

    private void validation(String accountNumber) {
        if (isBlank(accountNumber) || isLengthMoreThan(accountNumber, MAX_ACCOUNT_NUMBER_LENGTH)) {
            throw MemberException.of(INVALID_ACCOUNT_NUMBER_LENGTH);
        }

        if (isNotNumber(accountNumber)) {
            throw MemberException.of(INVALID_ACCOUNT_NUMBER);
        }
    }
}
