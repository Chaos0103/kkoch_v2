package com.kkoch.admin.api.service.notice.response;

import com.kkoch.admin.domain.notice.Notice;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NoticeModifyResponse {

    private final int noticeId;
    private final LocalDateTime modifyDateTime;

    @Builder
    private NoticeModifyResponse(int noticeId, LocalDateTime modifyDateTime) {
        this.noticeId = noticeId;
        this.modifyDateTime = modifyDateTime;
    }

    public static NoticeModifyResponse of(Notice notice) {
        return new NoticeModifyResponse(notice.getId(), notice.getLastModifiedDateTime());
    }
}
