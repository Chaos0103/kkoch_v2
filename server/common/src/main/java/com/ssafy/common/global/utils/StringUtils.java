package com.ssafy.common.global.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static org.springframework.util.StringUtils.hasText;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class StringUtils {

    private static final String KOREAN_REGEX = "^[가-힣]*$";
    private static final String EMAIL_REGEX = "\\w+@\\w+\\.\\w+(\\.\\w+)?";
    private static final String PASSWORD_REGEX = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$";

    public static boolean isBlank(String str) {
        return !hasText(str);
    }

    public static boolean isLengthLessThan(String str, int minLength) {
        return str.length() < minLength;
    }

    public static boolean isLengthMoreThan(String str, int maxLength) {
        return str.length() > maxLength;
    }

    public static boolean isLengthNotEquals(String str, int length) {
        return isLengthLessThan(str, length) || isLengthMoreThan(str, length);
    }

    public static boolean isNumber(String str) {
        return str.chars().allMatch(Character::isDigit);
    }

    public static boolean isNotNumber(String str) {
        return !isNumber(str);
    }

    public static boolean isKorean(String str) {
        return str.matches(KOREAN_REGEX);
    }

    public static boolean isNotKorean(String str) {
        return !isKorean(str);
    }

    public static boolean isEmailPattern(String str) {
        return str.matches(EMAIL_REGEX);
    }

    public static boolean isNotEmailPattern(String str) {
        return !isEmailPattern(str);
    }

    public static boolean isPasswordPattern(String str) {
        return str.matches(PASSWORD_REGEX);
    }

    public static boolean isNotPasswordPattern(String str) {
        return !isPasswordPattern(str);
    }
}
