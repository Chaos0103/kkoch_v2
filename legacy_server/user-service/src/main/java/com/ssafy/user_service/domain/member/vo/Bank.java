package com.ssafy.user_service.domain.member.vo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Bank {

    SHINHAN("088", "신한은행"),
    KOOKMIN("004", "KB국민은행"),
    HANA("081", "하나은행"),
    WOORI("020", "우리은행"),
    NONGHYEOP("011", "NH농협은행");

    private final String code;
    private final String korean;

    public static boolean isSupported(String code) {
        for (Bank value : values()) {
            if (value.code.equals(code)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNotSupported(String code) {
        return !isSupported(code);
    }
}
