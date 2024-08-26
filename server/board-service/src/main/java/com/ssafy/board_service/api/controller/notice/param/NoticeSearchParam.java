package com.ssafy.board_service.api.controller.notice.param;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.ssafy.board_service.common.util.PageUtils.PARAM_DEFAULT_PAGE_SIZE;

@Getter
@Setter
@NoArgsConstructor
public class NoticeSearchParam {

    private String page = PARAM_DEFAULT_PAGE_SIZE;

    private String keyword;

    @Builder
    private NoticeSearchParam(String page, String keyword) {
        this.page = page;
        this.keyword = keyword;
    }
}
