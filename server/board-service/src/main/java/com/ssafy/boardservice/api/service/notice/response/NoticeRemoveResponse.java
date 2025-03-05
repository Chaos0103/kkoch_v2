package com.ssafy.boardservice.api.service.notice.response;

import com.ssafy.boardservice.domain.notice.Notice;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NoticeRemoveResponse {

    private final Integer noticeId;

    @Builder
    private NoticeRemoveResponse(Integer noticeId) {
        this.noticeId = noticeId;
    }


    public static NoticeRemoveResponse of(Notice notice) {
        return new NoticeRemoveResponse(notice.getId());
    }
}

