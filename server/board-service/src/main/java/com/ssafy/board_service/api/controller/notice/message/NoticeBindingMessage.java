package com.ssafy.board_service.api.controller.notice.message;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NoticeBindingMessage {

    public static final String NOT_BLANK_TITLE = "공지사항 제목을 입력해주세요.";
    public static final String NOT_BLANK_CONTENT = "공지사항 내용을 입력해주세요.";
}
