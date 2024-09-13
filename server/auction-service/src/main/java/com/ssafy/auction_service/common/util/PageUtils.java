package com.ssafy.auction_service.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class PageUtils {

    private static final int DEFAULT_PAGE_SIZE = 10;

    public static Pageable of(int pageNumber) {
        return PageRequest.of(pageNumber - 1, DEFAULT_PAGE_SIZE);
    }
}
