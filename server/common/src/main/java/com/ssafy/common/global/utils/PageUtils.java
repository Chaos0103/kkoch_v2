package com.ssafy.common.global.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static com.ssafy.common.global.utils.StringUtils.isBlank;
import static com.ssafy.common.global.utils.StringUtils.isNotNumber;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class PageUtils {

    public static final String PARAM_DEFAULT_PAGE_SIZE = "1";

    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int DEFAULT_PAGE_NUMBER = 0;

    public static Pageable of(int pageNumber) {
        return PageRequest.of(pageNumber, DEFAULT_PAGE_SIZE);
    }

    public static int parsePageNumber(String pageNumber) {
        if (isBlank(pageNumber) || isNotNumber(pageNumber)) {
            return DEFAULT_PAGE_NUMBER;
        }

        int intPageNumber = Integer.parseInt(pageNumber);
        return Math.max(DEFAULT_PAGE_NUMBER, intPageNumber - 1);
    }
}
