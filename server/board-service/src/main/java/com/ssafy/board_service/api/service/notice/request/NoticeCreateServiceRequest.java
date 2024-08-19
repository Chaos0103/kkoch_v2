package com.ssafy.board_service.api.service.notice.request;

import com.ssafy.board_service.domain.notice.Notice;
import lombok.Builder;

import java.time.LocalDateTime;

import static com.ssafy.board_service.api.service.notice.NoticeValidate.validateNoticeTitle;
import static com.ssafy.board_service.api.service.notice.NoticeValidate.validateToFixedDateTime;

public class NoticeCreateServiceRequest {

    private final String title;
    private final String content;
    private final String toFixedDateTime;

    @Builder
    private NoticeCreateServiceRequest(String title, String content, String toFixedDateTime) {
        this.title = title;
        this.content = content;
        this.toFixedDateTime = toFixedDateTime;
    }

    public static NoticeCreateServiceRequest of(String title, String content, String toFixedDateTime) {
        return new NoticeCreateServiceRequest(title, content, toFixedDateTime);
    }

    public Notice toEntity(Long createdBy, LocalDateTime currentDateTime) {
        LocalDateTime to = getToFixedDateTime(currentDateTime);

        validateNoticeTitle(title);
        validateToFixedDateTime(to, currentDateTime);

        return Notice.create(createdBy, title, content, to);
    }

    private LocalDateTime getToFixedDateTime(LocalDateTime currentDateTime) {
        if (toFixedDateTime == null) {
            return currentDateTime;
        }

        return LocalDateTime.parse(toFixedDateTime);
    }
}
