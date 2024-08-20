package com.ssafy.board_service.common.util;

import com.ssafy.board_service.api.service.StringValidate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageUtils {

    public static final String PARAM_DEFAULT_PAGE_SIZE = "1";

    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int DEFAULT_PAGE_NUMBER = 0;

    public static Pageable of(int pageNumber) {
        return PageRequest.of(pageNumber, DEFAULT_PAGE_SIZE);
    }

    public static int parsePageNumber(String pageNumber) {
        StringValidate strPageNumber = StringValidate.of(pageNumber);
        if (strPageNumber.isBlank() || strPageNumber.isNotNumber()) {
            return DEFAULT_PAGE_NUMBER;
        }

        int intPageNumber = Integer.parseInt(pageNumber);
        return Math.max(DEFAULT_PAGE_NUMBER, intPageNumber - 1);
    }
}
