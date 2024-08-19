package com.ssafy.board_service.api.service.notice.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NoticeModifyResponse {

    private int id;
    private String title;
    private Boolean isFixed;
    private LocalDateTime modifiedDateTime;

    @Builder
    private NoticeModifyResponse(int id, String title, Boolean isFixed, LocalDateTime modifiedDateTime) {
        this.id = id;
        this.title = title;
        this.isFixed = isFixed;
        this.modifiedDateTime = modifiedDateTime;
    }
}
