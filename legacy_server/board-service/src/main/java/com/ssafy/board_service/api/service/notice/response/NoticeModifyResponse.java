package com.ssafy.board_service.api.service.notice.response;

import com.ssafy.board_service.domain.notice.Notice;
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

    public static NoticeModifyResponse of(Notice notice, LocalDateTime currentDateTime) {
        return new NoticeModifyResponse(notice.getId(), notice.getNoticeTitle(), notice.isFixed(currentDateTime), currentDateTime);
    }
}
