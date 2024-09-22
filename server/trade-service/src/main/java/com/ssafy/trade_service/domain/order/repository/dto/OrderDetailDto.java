package com.ssafy.trade_service.domain.order.repository.dto;

import com.ssafy.trade_service.domain.order.OrderStatus;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class OrderDetailDto {

    private Long id;
    private OrderStatus orderStatus;
    private int totalPrice;
    private Boolean isPickUp;
    private LocalDateTime pickUpDateTime;

    @Builder
    private OrderDetailDto(Long id, OrderStatus orderStatus, int totalPrice, Boolean isPickUp, LocalDateTime pickUpDateTime) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.isPickUp = isPickUp;
        this.pickUpDateTime = pickUpDateTime;
    }
}
