package com.ssafy.board_service.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static org.springframework.util.StringUtils.hasText;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class TimeUtils {

    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    public static LocalDateTime parseToFixedDateTime(String s, LocalDateTime defaultValue) {
        if (!hasText(s)) {
            return defaultValue;
        }

        return parse(s);
    }

    public static boolean isNull(LocalDateTime target) {
        return target == null;
    }

    public static boolean isNotNull(LocalDateTime target) {
        return !isNull(target);
    }

    public static boolean isPast(LocalDateTime target, LocalDateTime current) {
        return current.isAfter(target);
    }

    private static LocalDateTime parse(String s) {
        return LocalDateTime.parse(s);
    }
}
