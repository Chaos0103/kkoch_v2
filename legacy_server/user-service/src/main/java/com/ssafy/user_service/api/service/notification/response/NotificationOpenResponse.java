package com.ssafy.user_service.api.service.notification.response;

import com.ssafy.user_service.domain.membernotification.MemberNotification;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

    public static NotificationOpenResponse of(List<MemberNotification> notifications, LocalDateTime openStatusModifiedDateTime) {
        return new NotificationOpenResponse(notifications.size(), openStatusModifiedDateTime);
    }
}
