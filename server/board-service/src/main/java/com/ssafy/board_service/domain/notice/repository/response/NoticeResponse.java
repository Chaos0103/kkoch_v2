package com.ssafy.board_service.domain.notice.repository.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NoticeResponse {

    private int id;
    private String title;
    private Boolean isFixed;
    private LocalDateTime createdDateTime;

    @Builder
    private NoticeResponse(int id, String title, Boolean isFixed, LocalDateTime createdDateTime) {
        this.id = id;
        this.title = title;
        this.isFixed = isFixed;
        this.createdDateTime = createdDateTime;
    }
}
