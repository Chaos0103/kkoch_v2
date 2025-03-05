package com.ssafy.boardservice.domain.notice.repository.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NoticeListDisplayResponse {

    private Integer noticeId;
    private String title;
    private Boolean isFixed;
    private LocalDateTime createdDateTime;
}
