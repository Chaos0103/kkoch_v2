package com.ssafy.user_service.api.service.notification.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NotificationRemoveResponse {

    private int removedNotificationCount;
    private LocalDateTime removedDateTime;

    @Builder
    private NotificationRemoveResponse(int removedNotificationCount, LocalDateTime removedDateTime) {
        this.removedNotificationCount = removedNotificationCount;
        this.removedDateTime = removedDateTime;
    }

    public static NotificationRemoveResponse of(int removedNotificationCount, LocalDateTime removedDateTime) {
        return new NotificationRemoveResponse(removedNotificationCount, removedDateTime);
    }
}
