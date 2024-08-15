package com.ssafy.user_service.api.controller.notification.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class NotificationOpenRequest {

    private List<Long> ids;

    @Builder
    private NotificationOpenRequest(List<Long> ids) {
        this.ids = ids;
    }
}
