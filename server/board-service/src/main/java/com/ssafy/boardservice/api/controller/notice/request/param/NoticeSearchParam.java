package com.ssafy.boardservice.api.controller.notice.request.param;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NoticeSearchParam {

    private String keyword;
    private String page;

    @Builder
    private NoticeSearchParam(String keyword, String page) {
        this.keyword = keyword;
        this.page = page;
    }
}
