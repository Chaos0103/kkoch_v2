package com.ssafy.userservice.domain.member.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {

    MASTER("ROLE_MASTER"),
    USER("ROLE_USER"),
    BUSINESS("ROLE_BUSINESS"),
    ADMIN("ROLE_ADMIN");

    private final String text;
}
