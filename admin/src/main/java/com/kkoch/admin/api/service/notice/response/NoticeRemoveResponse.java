package com.kkoch.admin.api.service.notice.response;

import com.kkoch.admin.domain.notice.Notice;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NoticeRemoveResponse {

    private final int noticeId;
    private final LocalDateTime removedDateTime;

    @Builder
    private NoticeRemoveResponse(int noticeId, LocalDateTime removedDateTime) {
        this.noticeId = noticeId;
        this.removedDateTime = removedDateTime;
    }

    public static NoticeRemoveResponse of(Notice notice) {
        return new NoticeRemoveResponse(notice.getId(), notice.getLastModifiedDateTime());
    }
}
