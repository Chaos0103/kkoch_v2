package com.ssafy.trade_service.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class TimeUtils {

    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
}
