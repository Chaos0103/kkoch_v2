package com.ssafy.boardservice.api.controller;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class BindingMessage {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class NoticeMessage {
        public static final String NOT_BLANK_TITLE = "제목을 입력해주세요.";
        public static final String NOT_BLANK_CONTENT = "내용을 입력해주세요.";
    }
}
