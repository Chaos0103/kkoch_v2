package com.ssafy.userservice.domain.member.enums;

import com.ssafy.common.global.exception.MemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.ssafy.common.global.exception.code.ErrorCode.INVALID_MEMBER_ROLE;

@Slf4j
@RequiredArgsConstructor
public enum Role {

    MASTER("ROLE_MASTER"),
    USER("ROLE_USER"),
    BUSINESS("ROLE_BUSINESS"),
    ADMIN("ROLE_ADMIN");

    private final String text;

    public static Role of(String name) {
        try {
            return Role.valueOf(name);
        } catch (IllegalArgumentException e) {
            log.error("지원하지 않는 권한 등록 요청 시도 [role = {}]", name);
            throw MemberException.of(INVALID_MEMBER_ROLE);
        }
    }
}
