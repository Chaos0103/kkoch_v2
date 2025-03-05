package com.ssafy.common.global.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class TimeUtils {

    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    public static boolean isNull(LocalDateTime target) {
        return target == null;
    }

    public static boolean isPast(LocalDateTime target, LocalDateTime current) {
        return current.isAfter(target);
    }

    public static LocalDateTime atStartOfDay(LocalDate date) {
        return date.atStartOfDay();
    }

    public static LocalDateTime atEndOfDay(LocalDate date) {
        return date.atStartOfDay()
            .plusDays(1)
            .minusSeconds(1);
    }

    public static LocalDate parse(String str) {
        try {
            return LocalDate.parse(str);
        } catch (DateTimeParseException e) {
            //TODO: 기능 구현
            throw new RuntimeException("날짜를 올바르게 입력해주세요.", e);
        }
    }
}
