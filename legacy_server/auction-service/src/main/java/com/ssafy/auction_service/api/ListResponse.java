package com.ssafy.auction_service.api;

import lombok.Getter;

import java.util.List;

@Getter
public class ListResponse<T> {

    private final List<T> content;
    private final int size;

    private ListResponse(List<T> content) {
        this.content = content;
        this.size = content.size();
    }

    public static <T> ListResponse<T> of(List<T> content) {
        return new ListResponse<>(content);
    }
}
