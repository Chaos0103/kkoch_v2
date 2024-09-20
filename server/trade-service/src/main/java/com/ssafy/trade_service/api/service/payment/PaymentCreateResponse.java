package com.ssafy.trade_service.api.service.payment;

import com.ssafy.trade_service.domain.payment.PaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentCreateResponse {

    private Long id;
    private int paymentAmount;
    private PaymentStatus paymentStatus;
    private String paymentDataTime;

    @Builder
    private PaymentCreateResponse(Long id, int paymentAmount, PaymentStatus paymentStatus, String paymentDataTime) {
        this.id = id;
        this.paymentAmount = paymentAmount;
        this.paymentStatus = paymentStatus;
        this.paymentDataTime = paymentDataTime;
    }
}
