package com.ssafy.auction_service.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class StringUtils {

    public static boolean isLengthMoreThan(String str, int maxLength) {
        return str.length() > maxLength;
    }
}
