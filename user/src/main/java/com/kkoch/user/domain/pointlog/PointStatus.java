package com.kkoch.user.domain.pointlog;

public enum PointStatus {

    CHARGE("충전"),
    RETURN("반환"),
    PAYMENT("결제");

    private final String description;

    PointStatus(String description) {
        this.description = description;
    }
}
