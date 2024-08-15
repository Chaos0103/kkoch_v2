package com.ssafy.user_service.domain.membernotification.repository.response;

import com.ssafy.user_service.domain.notification.NotificationCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(force = true)
public class NotificationResponse {

    private final long id;
    private final NotificationCategory category;
    private final String content;
    private final Boolean isOpened;
    private final LocalDateTime notificationDateTime;

    @Builder
    private NotificationResponse(long id, NotificationCategory category, String content, Boolean isOpened, LocalDateTime notificationDateTime) {
        this.id = id;
        this.category = category;
        this.content = content;
        this.isOpened = isOpened;
        this.notificationDateTime = notificationDateTime;
    }
}
