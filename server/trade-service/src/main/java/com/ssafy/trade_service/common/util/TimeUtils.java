package com.ssafy.trade_service.common.util;

import com.ssafy.trade_service.common.exception.AppException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class TimeUtils {

    private static final String DATE_TIME_PARSE_EXCEPTION = "날짜를 올바르게 입력해주세요.";

    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    public static LocalDateTime parse(String str) {
        try {
            return LocalDateTime.parse(str);
        } catch (DateTimeParseException e) {
            throw new AppException(DATE_TIME_PARSE_EXCEPTION, e);
        }
    }

    public static boolean comparePastOrPresent(LocalDateTime target, LocalDateTime current) {
        return current.isEqual(target) || current.isAfter(target);
    }
}
