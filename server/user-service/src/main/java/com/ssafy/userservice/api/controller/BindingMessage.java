package com.ssafy.userservice.api.controller;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class BindingMessage {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class MemberMessage {
        public static final String NOT_BLANK_EMAIL = "이메일을 입력해주세요.";
        public static final String NOT_BLANK_PASSWORD = "비밀번호를 입력해주세요.";
        public static final String NOT_BLANK_NAME = "이름을 입력해주세요.";
        public static final String NOT_BLANK_TEL = "연락처를 입력해주세요.";
        public static final String NOT_BLANK_ROLE = "회원 권한을 입력해주세요.";
        public static final String NOT_BLANK_CURRENT_PASSWORD = "현재 비밀번호를 입력해주세요.";
        public static final String NOT_BLANK_NEW_PASSWORD = "신규 비밀번호를 입력해주세요.";
        public static final String NOT_BLANK_BUSINESS_NUMBER = "사업자 번호를 입력해주세요.";
        public static final String NOT_BLANK_BANK_CODE = "은행 코드를 입력해주세요.";
        public static final String NOT_BLANK_ACCOUNT_NUMBER = "은행 계좌를 입력해주세요.";
        public static final String NOT_BLANK_AUTHENTICATION_NUMBER = "인증 번호를 입력해주세요.";
    }
}
