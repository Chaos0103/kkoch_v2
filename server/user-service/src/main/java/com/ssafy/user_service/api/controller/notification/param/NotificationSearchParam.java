package com.ssafy.user_service.api.controller.notification.param;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotificationSearchParam {

    private String page = "1";
    private String category;

    @Builder
    private NotificationSearchParam(String page, String category) {
        this.page = page;
        this.category = category;
    }
}
