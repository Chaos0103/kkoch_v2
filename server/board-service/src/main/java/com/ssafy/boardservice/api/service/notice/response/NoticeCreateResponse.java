package com.ssafy.boardservice.api.service.notice.response;

import com.ssafy.boardservice.domain.notice.Notice;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NoticeCreateResponse {

    private final Integer noticeId;
    private final String title;
    private final Boolean isFixed;
    private final LocalDateTime createdDateTime;

    @Builder
    private NoticeCreateResponse(Integer noticeId, String title, Boolean isFixed, LocalDateTime createdDateTime) {
        this.noticeId = noticeId;
        this.title = title;
        this.isFixed = isFixed;
        this.createdDateTime = createdDateTime;
    }

    public static NoticeCreateResponse of(Notice notice, LocalDateTime current) {
        return NoticeCreateResponse.builder()
            .noticeId(notice.getId())
            .title(notice.getTitle().getValue())
            .isFixed(notice.isFixed(current))
            .createdDateTime(notice.getCreatedDateTime())
            .build();
    }
}
