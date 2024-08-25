package com.ssafy.board_service.api.service.notice.response;

import com.ssafy.board_service.domain.notice.Notice;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NoticeRemoveResponse {

    private int id;
    private String title;
    private LocalDateTime removedDateTime;

    @Builder
    private NoticeRemoveResponse(int id, String title, LocalDateTime removedDateTime) {
        this.id = id;
        this.title = title;
        this.removedDateTime = removedDateTime;
    }

    public static NoticeRemoveResponse of(Notice notice, LocalDateTime currentDateTime) {
        return new NoticeRemoveResponse(notice.getId(), notice.getNoticeTitle(), currentDateTime);
    }
}
