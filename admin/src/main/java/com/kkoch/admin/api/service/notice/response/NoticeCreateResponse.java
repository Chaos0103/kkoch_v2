package com.kkoch.admin.api.service.notice.response;

import com.kkoch.admin.domain.notice.Notice;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NoticeCreateResponse {

    private final int noticeId;
    private final String title;
    private final LocalDateTime createdDateTime;

    @Builder
    private NoticeCreateResponse(int noticeId, String title, LocalDateTime createdDateTime) {
        this.noticeId = noticeId;
        this.title = title;
        this.createdDateTime = createdDateTime;
    }

    public static NoticeCreateResponse of(Notice notice) {
        return new NoticeCreateResponse(notice.getId(), notice.getTitle(), notice.getCreatedDateTime());
    }
}
