package com.ssafy.user_service.api.controller.notification.param;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.ssafy.user_service.common.util.PageUtils.PARAM_DEFAULT_PAGE_SIZE;

@Getter
@Setter
@NoArgsConstructor
public class NotificationSearchParam {

    private String page = PARAM_DEFAULT_PAGE_SIZE;

    private String category;

    @Builder
    private NotificationSearchParam(String page, String category) {
        this.page = page;
        this.category = category;
    }
}
