package com.ssafy.trade_service.domain.order;

import com.ssafy.trade_service.domain.TimeBaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus orderStatus;

    @Column(nullable = false, updatable = false)
    private int totalPrice;

    @Embedded
    private PickUp pickUp;

    @Builder
    private Order(boolean isDeleted, Long memberId, OrderStatus orderStatus, int totalPrice, PickUp pickUp) {
        super(isDeleted);
        this.memberId = memberId;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.pickUp = pickUp;
    }

    public static Order of(boolean isDeleted, Long memberId, OrderStatus orderStatus, int totalPrice, PickUp pickUp) {
        return new Order(isDeleted, memberId, orderStatus, totalPrice, pickUp);
    }
}
