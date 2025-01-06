package com.ssafy.user_service.api.controller.notification.param;

import com.ssafy.user_service.api.service.SearchPeriod;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.ssafy.user_service.common.util.PageUtils.PARAM_DEFAULT_PAGE_SIZE;

@Getter
@Setter
@NoArgsConstructor
public class SentNotificationSearchParam {

    private String page = PARAM_DEFAULT_PAGE_SIZE;
    private String from;
    private String to;

    @Builder
    private SentNotificationSearchParam(String page, String from, String to) {
        this.page = page;
        this.from = from;
        this.to = to;
    }

    public SearchPeriod toSearchPeriod() {
        return SearchPeriod.of(from, to);
    }
}
