package com.ssafy.trade_service.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static org.springframework.util.StringUtils.hasText;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class StringUtils {

    public static boolean isBlank(String str) {
        return !hasText(str);
    }

    public static boolean isNumber(String str) {
        return str.chars().allMatch(Character::isDigit);
    }

    public static boolean isNotNumber(String str) {
        return !isNumber(str);
    }
}
