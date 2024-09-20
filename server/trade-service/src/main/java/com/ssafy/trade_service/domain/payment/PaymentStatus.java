package com.ssafy.trade_service.domain.payment;

import lombok.Getter;

@Getter
public enum PaymentStatus {

    PAYMENT_COMPLETED("결제 완료"),
    PAYMENT_FAILED("결제 실패");

    private final String text;

    PaymentStatus(String text) {
        this.text = text;
    }
}
