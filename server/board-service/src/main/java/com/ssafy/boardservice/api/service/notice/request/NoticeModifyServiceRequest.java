package com.ssafy.boardservice.api.service.notice.request;

import com.ssafy.boardservice.domain.notice.vo.NoticeFixedDateTime;
import com.ssafy.boardservice.domain.notice.vo.NoticeTitle;
import lombok.Builder;

import java.time.LocalDateTime;

public class NoticeModifyServiceRequest {

    private final String title;
    private final String content;
    private final LocalDateTime toFixedDateTime;

    @Builder
    private NoticeModifyServiceRequest(String title, String content, LocalDateTime toFixedDateTime) {
        this.title = title;
        this.content = content;
        this.toFixedDateTime = toFixedDateTime;
    }

    public static NoticeModifyServiceRequest of(String title, String content, LocalDateTime toFixedDateTime) {
        return new NoticeModifyServiceRequest(title, content, toFixedDateTime);
    }

    public NoticeTitle getTitle() {
        return NoticeTitle.of(title);
    }

    public String getContent() {
        return content;
    }

    public NoticeFixedDateTime getFixedDateTime(LocalDateTime current) {
        return NoticeFixedDateTime.of(toFixedDateTime, current);
    }
}
