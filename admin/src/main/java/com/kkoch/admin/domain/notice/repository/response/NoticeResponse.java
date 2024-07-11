package com.kkoch.admin.domain.notice.repository.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NoticeResponse {

    private int noticeId;
    private String title;
    private String content;
    private LocalDateTime createdDateTime;

    @Builder
    private NoticeResponse(int noticeId, String title, String content, LocalDateTime createdDateTime) {
        this.noticeId = noticeId;
        this.title = title;
        this.content = content;
        this.createdDateTime = createdDateTime;
    }
}
