package com.ssafy.user_service.domain.member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {

    MASTER("ROLE_MASTER"),
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String text;
}
