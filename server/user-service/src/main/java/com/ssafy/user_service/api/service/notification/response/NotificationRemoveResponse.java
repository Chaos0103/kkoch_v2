package com.ssafy.user_service.api.service.notification.response;

import com.ssafy.user_service.domain.membernotification.MemberNotification;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

    public static NotificationRemoveResponse of(List<MemberNotification> memberNotifications, LocalDateTime removedDateTime) {
        return new NotificationRemoveResponse(memberNotifications.size(), removedDateTime);
    }
}
