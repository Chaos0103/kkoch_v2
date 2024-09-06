package com.ssafy.auction_service.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class StringUtils {

    private static final String KOREAN_REGEX = "^[가-힣]*$";

    public static boolean isLengthMoreThan(String str, int maxLength) {
        return str.length() > maxLength;
    }

    public static boolean isKorean(String str) {
        return str.matches(KOREAN_REGEX);
    }

    public static boolean isNotKorean(String str) {
        return !isKorean(str);
    }
}
