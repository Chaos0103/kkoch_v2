package com.ssafy.trade_service.domain.order;

import lombok.Getter;

@Getter
public enum OrderStatus {

    INIT("생성"),
    PAYMENT_COMPLETED("결제 완료"),
    PAYMENT_FAILED("결제 실패");

    private final String text;

    OrderStatus(String text) {
        this.text = text;
    }

    public boolean canPickUp() {
        return this == PAYMENT_COMPLETED;
    }
}
