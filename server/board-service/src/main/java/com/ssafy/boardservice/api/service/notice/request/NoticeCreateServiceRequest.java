package com.ssafy.boardservice.api.service.notice.request;

import com.ssafy.boardservice.domain.notice.Notice;
import com.ssafy.boardservice.domain.notice.vo.NoticeFixedDateTime;
import com.ssafy.boardservice.domain.notice.vo.NoticeTitle;
import lombok.Builder;

import java.time.LocalDateTime;

public class NoticeCreateServiceRequest {

    private final String title;
    private final String content;
    private final LocalDateTime toFixedDateTime;

    @Builder
    private NoticeCreateServiceRequest(String title, String content, LocalDateTime toFixedDateTime) {
        this.title = title;
        this.content = content;
        this.toFixedDateTime = toFixedDateTime;
    }

    public static NoticeCreateServiceRequest of(String title, String content, LocalDateTime toFixedDateTime) {
        return new NoticeCreateServiceRequest(title, content, toFixedDateTime);
    }

    public Notice toEntity(Long memberId, LocalDateTime current) {
        return Notice.create(memberId, NoticeTitle.of(title), content, NoticeFixedDateTime.of(toFixedDateTime, current));
    }
}
