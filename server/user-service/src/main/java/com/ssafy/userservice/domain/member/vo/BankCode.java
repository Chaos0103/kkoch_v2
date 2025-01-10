package com.ssafy.userservice.domain.member.vo;

import com.ssafy.common.global.exception.MemberException;
import com.ssafy.userservice.domain.member.enums.Bank;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.ssafy.common.global.exception.code.ErrorCode.INVALID_BANK_CODE;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class BankCode {

    @Column(columnDefinition = "char(3)", length = 3)
    private final String bankCode;

    private BankCode(String bankCode) {
        validation(bankCode);
        this.bankCode = bankCode;
    }

    public static BankCode of(String bankCode) {
        return new BankCode(bankCode);
    }

    private void validation(String bankCode) {
        if (Bank.isNotSupported(bankCode)) {
            throw MemberException.of(INVALID_BANK_CODE);
        }
    }
}
