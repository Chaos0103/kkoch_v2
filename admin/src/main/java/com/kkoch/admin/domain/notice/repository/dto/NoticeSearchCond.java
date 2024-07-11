package com.kkoch.admin.domain.notice.repository.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NoticeSearchCond {

    private final String keyword;

    @Builder
    private NoticeSearchCond(String keyword) {
        this.keyword = keyword;
    }

    public static NoticeSearchCond of(String keyword) {
        return new NoticeSearchCond(keyword);
    }
}
