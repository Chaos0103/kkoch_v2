package com.ssafy.board_service.api.service.notice.request;

import lombok.Builder;

import java.time.LocalDateTime;

public class NoticeCreateServiceRequest {

    private final String title;
    private final String content;
    private final LocalDateTime toFixedDateTime;

    @Builder
    private NoticeCreateServiceRequest(String title, String content, LocalDateTime toFixedDateTime) {
        this.title = title;
        this.content = content;
        this.toFixedDateTime = toFixedDateTime;
    }

    public static NoticeCreateServiceRequest of(String title, String content, LocalDateTime toFixedDateTime) {
        return new NoticeCreateServiceRequest(title, content, toFixedDateTime);
    }
}
