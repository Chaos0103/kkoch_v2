package com.ssafy.trade_service.api;

import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
public class PageResponse<T> {

    private final List<T> content;
    private final int currentPage;
    private final int size;
    private final Boolean isFirst;
    private final Boolean isLast;

    private PageResponse(Page<T> data) {
        this.content = data.getContent();
        this.currentPage = data.getNumber() + 1;
        this.size = data.getSize();
        this.isFirst = data.isFirst();
        this.isLast = data.isLast();
    }

    public static <T> PageResponse<T> of(Page<T> data) {
        return new PageResponse<>(data);
    }

    public static <T> PageResponse<T> create(List<T> content, Pageable pageable, long total) {
        PageImpl<T> page = new PageImpl<>(content, pageable, total);
        return of(page);
    }
}
