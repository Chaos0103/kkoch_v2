package com.ssafy.board_service.api.service.notice.request;

import lombok.Builder;

public class NoticeModifyServiceRequest {

    private final String title;
    private final String content;
    private final String toFixedDateTime;

    @Builder
    private NoticeModifyServiceRequest(String title, String content, String toFixedDateTime) {
        this.title = title;
        this.content = content;
        this.toFixedDateTime = toFixedDateTime;
    }

    public static NoticeModifyServiceRequest of(String title, String content, String toFixedDateTime) {
        return new NoticeModifyServiceRequest(title, content, toFixedDateTime);
    }
}
