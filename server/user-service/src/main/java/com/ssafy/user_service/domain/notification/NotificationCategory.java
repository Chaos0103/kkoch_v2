package com.ssafy.user_service.domain.notification;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum NotificationCategory {

    PAYMENT("결제"),
    AUCTION("경매"),
    SERVICE("서비스");

    private final String description;

    public static NotificationCategory of(String notificationCategory) {
        return Arrays.stream(values())
            .filter(category -> category.name().equals(notificationCategory))
            .findFirst()
            .orElse(null);
    }
}
