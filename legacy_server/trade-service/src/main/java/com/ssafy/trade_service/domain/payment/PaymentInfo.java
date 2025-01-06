package com.ssafy.trade_service.domain.payment;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class PaymentInfo {

    @Column(nullable = false, updatable = false)
    private final int paymentAmount;

    @Column(nullable = false, updatable = false, length = 17)
    private final PaymentStatus paymentStatus;

    @Column(nullable = false, updatable = false)
    private final LocalDateTime paymentDateTime;

    @Builder
    private PaymentInfo(int paymentAmount, PaymentStatus paymentStatus, LocalDateTime paymentDateTime) {
        this.paymentAmount = paymentAmount;
        this.paymentStatus = paymentStatus;
        this.paymentDateTime = paymentDateTime;
    }

    public static PaymentInfo of(int paymentAmount, PaymentStatus paymentStatus, LocalDateTime paymentDateTime) {
        return new PaymentInfo(paymentAmount, paymentStatus, paymentDateTime);
    }

    public static PaymentInfo complete(int paymentAmount, LocalDateTime paymentDateTime) {
        return of(paymentAmount, PaymentStatus.PAYMENT_COMPLETED, paymentDateTime);
    }
}
