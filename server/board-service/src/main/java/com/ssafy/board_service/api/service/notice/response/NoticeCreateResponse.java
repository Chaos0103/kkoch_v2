package com.ssafy.board_service.api.service.notice.response;

import com.ssafy.board_service.domain.notice.Notice;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NoticeCreateResponse {

    private int id;
    private String title;
    private Boolean isFixed;
    private LocalDateTime createdDateTime;

    @Builder
    private NoticeCreateResponse(int id, String title, Boolean isFixed, LocalDateTime createdDateTime) {
        this.id = id;
        this.title = title;
        this.isFixed = isFixed;
        this.createdDateTime = createdDateTime;
    }

    public static NoticeCreateResponse of(Notice notice, LocalDateTime currentDateTime) {
        boolean isFixed = false;
        if (notice.getToFixedDateTime().isAfter(currentDateTime)) {
            isFixed = true;
        }
        return new NoticeCreateResponse(notice.getId(), notice.getNoticeTitle(), isFixed, currentDateTime);
    }
}
