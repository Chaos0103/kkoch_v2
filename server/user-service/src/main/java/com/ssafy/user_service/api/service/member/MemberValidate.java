package com.ssafy.user_service.api.service.member;

import com.ssafy.user_service.api.service.StringValidate;
import com.ssafy.user_service.common.exception.LengthOutOfRangeException;
import com.ssafy.user_service.common.exception.StringFormatException;

public abstract class MemberValidate {

    public static boolean validateEmail(String targetEmail) {
        StringValidate email = StringValidate.of(targetEmail);

        if (email.isLengthMoreThan(100)) {
            throw new LengthOutOfRangeException("이메일의 길이는 최대 100자리 입니다.");
        }

        if (email.doesNotMatches("\\w+@\\w+\\.\\w+(\\.\\w+)?")) {
            throw new StringFormatException("이메일을 올바르게 입력해주세요.");
        }

        return true;
    }

    public static boolean validatePassword(String targetPassword) {
        StringValidate password = StringValidate.of(targetPassword);

        if (password.isLengthLessThan(8) || password.isLengthMoreThan(20)) {
            throw new LengthOutOfRangeException("비밀번호의 길이는 최소 8자리에서 최대 20자리 입니다.");
        }

        if (password.doesNotMatches("^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$")) {
            throw new StringFormatException("비밀번호를 올바르게 입력해주세요.");
        }

        return true;
    }

    public static boolean validateName(String targetName) {
        StringValidate name = StringValidate.of(targetName);

        if (name.isLengthMoreThan(20)) {
            throw new LengthOutOfRangeException("이름의 길이는 최대 20자리 입니다.");
        }

        if (name.doseNotKorean()) {
            throw new StringFormatException("이름을 올바르게 입력해주세요.");
        }

        return true;
    }

    public static boolean validateTel(String targetTel) {
        StringValidate tel = StringValidate.of(targetTel);

        if (tel.isLengthNotEquals(11)) {
            throw new LengthOutOfRangeException("연락처를 올바르게 입력해주세요.");
        }

        if (tel.isNotNumber()) {
            throw new StringFormatException("연락처를 올바르게 입력해주세요.");
        }

        return true;
    }

    public static boolean validateBusinessNumber(String targetBusinessNumber) {
        StringValidate businessNumber = StringValidate.of(targetBusinessNumber);

        if (businessNumber.isLengthMoreThan(12)) {
            throw new LengthOutOfRangeException("사업자 번호의 길이는 최대 12자리 입니다.");
        }

        if (businessNumber.isNotNumber()) {
            throw new StringFormatException("사업자 번호를 올바르게 입력해주세요.");
        }

        return true;
    }
}
