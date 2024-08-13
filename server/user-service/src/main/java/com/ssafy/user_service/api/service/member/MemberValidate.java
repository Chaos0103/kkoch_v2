package com.ssafy.user_service.api.service.member;

import com.ssafy.user_service.api.service.StringValidate;
import com.ssafy.user_service.common.exception.LengthOutOfRangeException;
import com.ssafy.user_service.common.exception.NotSupportedException;
import com.ssafy.user_service.common.exception.StringFormatException;
import com.ssafy.user_service.domain.member.Bank;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberValidate {

    private static final int MAX_EMAIL_LENGTH = 100;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 20;
    private static final int MAX_NAME_LENGTH = 20;
    private static final int TEL_LENGTH = 11;
    private static final int MAX_BUSINESS_NUMBER_LENGTH = 12;
    private static final int MAX_ACCOUNT_NUMBER_LENGTH = 14;

    private static final String EMAIL_REGEX = "\\w+@\\w+\\.\\w+(\\.\\w+)?";
    private static final String PASSWORD_REGEX = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$";

    public static boolean validateEmail(String targetEmail) {
        StringValidate email = StringValidate.of(targetEmail);

        if (email.isLengthMoreThan(MAX_EMAIL_LENGTH)) {
            throw new LengthOutOfRangeException("이메일의 길이는 최대 100자리 입니다.");
        }

        if (email.isNotMatches(EMAIL_REGEX)) {
            throw new StringFormatException("이메일을 올바르게 입력해주세요.");
        }

        return true;
    }

    public static boolean validatePassword(String targetPassword) {
        StringValidate password = StringValidate.of(targetPassword);

        if (password.isLengthLessThan(MIN_PASSWORD_LENGTH) || password.isLengthMoreThan(MAX_PASSWORD_LENGTH)) {
            throw new LengthOutOfRangeException("비밀번호의 길이는 최소 8자리에서 최대 20자리 입니다.");
        }

        if (password.isNotMatches(PASSWORD_REGEX)) {
            throw new StringFormatException("비밀번호를 올바르게 입력해주세요.");
        }

        return true;
    }

    public static boolean validateName(String targetName) {
        StringValidate name = StringValidate.of(targetName);

        if (name.isLengthMoreThan(MAX_NAME_LENGTH)) {
            throw new LengthOutOfRangeException("이름의 길이는 최대 20자리 입니다.");
        }

        if (name.isNotKorean()) {
            throw new StringFormatException("이름을 올바르게 입력해주세요.");
        }

        return true;
    }

    public static boolean validateTel(String targetTel) {
        StringValidate tel = StringValidate.of(targetTel);

        if (tel.isLengthNotEquals(TEL_LENGTH)) {
            throw new LengthOutOfRangeException("연락처를 올바르게 입력해주세요.");
        }

        if (tel.isNotNumber()) {
            throw new StringFormatException("연락처를 올바르게 입력해주세요.");
        }

        return true;
    }

    public static boolean validateBusinessNumber(String targetBusinessNumber) {
        StringValidate businessNumber = StringValidate.of(targetBusinessNumber);

        if (businessNumber.isLengthMoreThan(MAX_BUSINESS_NUMBER_LENGTH)) {
            throw new LengthOutOfRangeException("사업자 번호의 길이는 최대 12자리 입니다.");
        }

        if (businessNumber.isNotNumber()) {
            throw new StringFormatException("사업자 번호를 올바르게 입력해주세요.");
        }

        return true;
    }

    public static boolean validateBankCode(String bankCode) {
        if (Bank.isNotSupported(bankCode)) {
            throw new NotSupportedException("지원하지 않는 은행 코드입니다.");
        }

        return true;
    }

    public static boolean validateAccountNumber(String targetAccountNumber) {
        StringValidate accountNumber = StringValidate.of(targetAccountNumber);

        if (accountNumber.isLengthMoreThan(MAX_ACCOUNT_NUMBER_LENGTH)) {
            throw new LengthOutOfRangeException("은행 계좌의 길이는 최대 14자리 입니다.");
        }

        if (accountNumber.isNotNumber()) {
            throw new StringFormatException("은행 계좌를 올바르게 입력해주세요.");
        }

        return true;
    }
}
