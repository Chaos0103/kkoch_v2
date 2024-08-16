package com.ssafy.user_service.domain.notification.repository.response;

import com.ssafy.user_service.domain.notification.NotificationCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SentNotificationResponse {

    private long id;
    private NotificationCategory category;
    private String content;
    private long sentMemberCount;
    private LocalDateTime sentDateTime;

    @Builder
    private SentNotificationResponse(long id, NotificationCategory category, String content, long sentMemberCount, LocalDateTime sentDateTime) {
        this.id = id;
        this.category = category;
        this.content = content;
        this.sentMemberCount = sentMemberCount;
        this.sentDateTime = sentDateTime;
    }
}
