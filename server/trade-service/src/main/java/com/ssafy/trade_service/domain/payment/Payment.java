package com.ssafy.trade_service.domain.payment;

import com.ssafy.trade_service.domain.TimeBaseEntity;
import com.ssafy.trade_service.domain.order.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Embedded
    private BankAccount bankAccount;

    @Embedded
    private PaymentInfo paymentInfo;

    @Builder
    private Payment(boolean isDeleted, Order order, BankAccount bankAccount, PaymentInfo paymentInfo) {
        super(isDeleted);
        this.order = order;
        this.bankAccount = bankAccount;
        this.paymentInfo = paymentInfo;
    }

    public static Payment of(boolean isDeleted, Order order, BankAccount bankAccount, PaymentInfo paymentInfo) {
        return new Payment(isDeleted, order, bankAccount, paymentInfo);
    }
}
