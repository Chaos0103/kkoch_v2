package com.ssafy.boardservice.domain.notice.repository.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NoticeDisplayResponse {

    private Integer noticeId;
    private String title;
    private String content;
    private LocalDateTime createdDateTime;
}
