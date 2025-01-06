package com.ssafy.trade_service.api.service.payment;

import com.ssafy.trade_service.domain.payment.Payment;
import com.ssafy.trade_service.domain.payment.PaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PaymentCreateResponse {

    private Long id;
    private int paymentAmount;
    private PaymentStatus paymentStatus;
    private LocalDateTime paymentDataTime;

    @Builder
    private PaymentCreateResponse(Long id, int paymentAmount, PaymentStatus paymentStatus, LocalDateTime paymentDataTime) {
        this.id = id;
        this.paymentAmount = paymentAmount;
        this.paymentStatus = paymentStatus;
        this.paymentDataTime = paymentDataTime;
    }

    public static PaymentCreateResponse of(Payment payment) {
        return new PaymentCreateResponse(payment.getId(), payment.getPaymentInfo().getPaymentAmount(), payment.getPaymentInfo().getPaymentStatus(), payment.getPaymentInfo().getPaymentDateTime());
    }
}
