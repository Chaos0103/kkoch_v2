package com.ssafy.user_service.api.controller.notification.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class NotificationRemoveRequest {

    private List<Long> ids;

    @Builder
    private NotificationRemoveRequest(List<Long> ids) {
        this.ids = ids;
    }
}
