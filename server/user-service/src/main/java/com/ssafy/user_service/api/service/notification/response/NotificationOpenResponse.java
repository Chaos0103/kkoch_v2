package com.ssafy.user_service.api.service.notification.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NotificationOpenResponse {

    private int openNotificationCount;
    private LocalDateTime openStatusModifiedDateTime;

    @Builder
    private NotificationOpenResponse(int openNotificationCount, LocalDateTime openStatusModifiedDateTime) {
        this.openNotificationCount = openNotificationCount;
        this.openStatusModifiedDateTime = openStatusModifiedDateTime;
    }

    public static NotificationOpenResponse of(int openNotificationCount, LocalDateTime openStatusModifiedDateTime) {
        return new NotificationOpenResponse(openNotificationCount, openStatusModifiedDateTime);
    }
}
