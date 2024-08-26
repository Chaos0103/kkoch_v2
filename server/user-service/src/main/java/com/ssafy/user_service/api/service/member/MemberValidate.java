package com.ssafy.user_service.api.service.member;

import com.ssafy.user_service.common.exception.LengthOutOfRangeException;
import com.ssafy.user_service.common.exception.NotSupportedException;
import com.ssafy.user_service.common.exception.StringFormatException;
import com.ssafy.user_service.domain.member.Bank;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.ssafy.user_service.common.util.StringUtils.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberValidate {

    private static final int MAX_EMAIL_LENGTH = 100;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 20;
    private static final int MAX_NAME_LENGTH = 20;
    private static final int TEL_LENGTH = 11;
    private static final int MAX_BUSINESS_NUMBER_LENGTH = 12;
    private static final int MAX_ACCOUNT_NUMBER_LENGTH = 14;

    public static boolean validateEmail(String email) {
        if (isLengthMoreThan(email, MAX_EMAIL_LENGTH)) {
            throw new LengthOutOfRangeException("이메일의 길이는 최대 100자리 입니다.");
        }

        if (isNotEmailPattern(email)) {
            throw new StringFormatException("이메일을 올바르게 입력해주세요.");
        }

        return true;
    }

    public static boolean validatePassword(String password) {
        if (isLengthLessThan(password, MIN_PASSWORD_LENGTH) || isLengthMoreThan(password, MAX_PASSWORD_LENGTH)) {
            throw new LengthOutOfRangeException("비밀번호의 길이는 최소 8자리에서 최대 20자리 입니다.");
        }

        if (isNotPasswordPattern(password)) {
            throw new StringFormatException("비밀번호를 올바르게 입력해주세요.");
        }

        return true;
    }

    public static boolean validateName(String name) {
        if (isLengthMoreThan(name, MAX_NAME_LENGTH)) {
            throw new LengthOutOfRangeException("이름의 길이는 최대 20자리 입니다.");
        }

        if (isNotKorean(name)) {
            throw new StringFormatException("이름을 올바르게 입력해주세요.");
        }

        return true;
    }

    public static boolean validateTel(String tel) {
        if (isLengthNotEquals(tel, TEL_LENGTH)) {
            throw new LengthOutOfRangeException("연락처를 올바르게 입력해주세요.");
        }

        if (isNotNumber(tel)) {
            throw new StringFormatException("연락처를 올바르게 입력해주세요.");
        }

        return true;
    }

    public static boolean validateBusinessNumber(String businessNumber) {
        if (isLengthMoreThan(businessNumber, MAX_BUSINESS_NUMBER_LENGTH)) {
            throw new LengthOutOfRangeException("사업자 번호의 길이는 최대 12자리 입니다.");
        }

        if (isNotNumber(businessNumber)) {
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

    public static boolean validateAccountNumber(String accountNumber) {
        if (isLengthMoreThan(accountNumber, MAX_ACCOUNT_NUMBER_LENGTH)) {
            throw new LengthOutOfRangeException("은행 계좌의 길이는 최대 14자리 입니다.");
        }

        if (isNotNumber(accountNumber)) {
            throw new StringFormatException("은행 계좌를 올바르게 입력해주세요.");
        }

        return true;
    }
}
