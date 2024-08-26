package com.ssafy.board_service.common.util;

import com.ssafy.board_service.common.exception.AppException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

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
        try {
            return LocalDateTime.parse(s);
        } catch (DateTimeParseException e) {
            throw new AppException("일시를 올바르게 입력해주세요.", e);
        }
    }
}
