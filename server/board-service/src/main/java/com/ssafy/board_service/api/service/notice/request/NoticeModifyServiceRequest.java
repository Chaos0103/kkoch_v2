package com.ssafy.board_service.api.service.notice.request;

import com.ssafy.board_service.domain.notice.Notice;
import lombok.Builder;

import java.time.LocalDateTime;

import static com.ssafy.board_service.api.service.notice.NoticeValidate.validateNoticeTitle;
import static com.ssafy.board_service.api.service.notice.NoticeValidate.validateToFixedDateTime;
import static com.ssafy.board_service.common.util.TimeUtils.parseToFixedDateTime;

public class NoticeModifyServiceRequest {

    private final String title;
    private final String content;
    private final String toFixedDateTime;

    @Builder
    private NoticeModifyServiceRequest(String title, String content, String toFixedDateTime) {
        this.title = title;
        this.content = content;
        this.toFixedDateTime = toFixedDateTime;
    }

    public static NoticeModifyServiceRequest of(String title, String content, String toFixedDateTime) {
        return new NoticeModifyServiceRequest(title, content, toFixedDateTime);
    }

    public void modifyEntity(Notice notice, Long modifiedBy, LocalDateTime currentDateTime) {
        LocalDateTime to = parseToFixedDateTime(toFixedDateTime, currentDateTime);

        validateNoticeTitle(title);
        validateToFixedDateTime(to, currentDateTime);

        notice.modify(modifiedBy, title, content, to);
    }
}
