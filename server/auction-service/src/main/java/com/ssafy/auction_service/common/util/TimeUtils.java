package com.ssafy.auction_service.common.util;

import com.ssafy.auction_service.common.exception.AppException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class TimeUtils {

    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    public static LocalDateTime parse(String str) {
        try {
            return LocalDateTime.parse(str);
        } catch (DateTimeParseException e) {
            throw new AppException("날짜를 올바르게 입력해주세요.", e);
        }
    }

    public static boolean isEqualsOrPast(LocalDateTime target, LocalDateTime current) {
        return current.isEqual(target) || current.isAfter(target);
    }
}
