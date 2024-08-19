package com.ssafy.board_service.api.controller.notice.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeModifyRequest {

    private String title;
    private String content;
    private String toFixedDateTime;

    @Builder
    private NoticeModifyRequest(String title, String content, String toFixedDateTime) {
        this.title = title;
        this.content = content;
        this.toFixedDateTime = toFixedDateTime;
    }
}
