package com.ssafy.board_service.domain.notice.repository.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NoticeDetailResponse {

    private int id;
    private String title;
    private String content;
    private LocalDateTime createdDateTime;

    @Builder
    private NoticeDetailResponse(int id, String title, String content, LocalDateTime createdDateTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdDateTime = createdDateTime;
    }
}
