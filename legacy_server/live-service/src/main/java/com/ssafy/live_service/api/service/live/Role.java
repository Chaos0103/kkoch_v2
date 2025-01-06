package com.ssafy.live_service.api.service.live;

import lombok.Getter;

@Getter
public enum Role {

    MASTER("ROLE_MASTER"),
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String text;

    Role(String text) {
        this.text = text;
    }

    public boolean isUser() {
        return this == USER;
    }

    public boolean isAdmin() {
        return this == ADMIN;
    }
}
