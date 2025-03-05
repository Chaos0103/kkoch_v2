package com.ssafy.boardservice.api.service.notice.response;

import com.ssafy.boardservice.domain.notice.Notice;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NoticeModifyResponse {

    private final Integer noticeId;
    private final String title;
    private final Boolean isFixed;

    @Builder
    private NoticeModifyResponse(Integer noticeId, String title, Boolean isFixed) {
        this.noticeId = noticeId;
        this.title = title;
        this.isFixed = isFixed;
    }


    public static NoticeModifyResponse of(Notice notice, LocalDateTime current) {
        return NoticeModifyResponse.builder()
            .noticeId(notice.getId())
            .title(notice.getTitle().getValue())
            .isFixed(notice.isFixed(current))
            .build();
    }

}
