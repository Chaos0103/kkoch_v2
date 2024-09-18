package com.ssafy.trade_service.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static org.springframework.util.StringUtils.hasText;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class StringUtils {

    private static final String KOREAN_REGEX = "^[가-힣]*$";

    public static boolean isBlank(String str) {
        return !hasText(str);
    }

    public static boolean isLengthMoreThan(String str, int maxLength) {
        return str.length() > maxLength;
    }

    public static boolean isKorean(String str) {
        return str.matches(KOREAN_REGEX);
    }

    public static boolean isNotKorean(String str) {
        return !isKorean(str);
    }

    public static boolean isNumber(String str) {
        return str.chars().allMatch(Character::isDigit);
    }

    public static boolean isNotNumber(String str) {
        return !isNumber(str);
    }
}
