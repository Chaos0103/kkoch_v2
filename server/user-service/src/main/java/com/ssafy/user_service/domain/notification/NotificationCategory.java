package com.ssafy.user_service.domain.notification;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NotificationCategory {

    PAYMENT("결제"),
    AUCTION("경매"),
    SERVICE("서비스");

    private final String description;
}
