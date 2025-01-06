package com.ssafy.trade_service.domain.order.repository.response;

import com.ssafy.trade_service.domain.order.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class OrderResponse {

    private long id;
    private OrderStatus orderStatus;
    private int totalPrice;
    private Boolean isPickUp;
    private LocalDateTime pickUpDateTime;
    private int orderCount;

    @Builder
    private OrderResponse(long id, OrderStatus orderStatus, int totalPrice, Boolean isPickUp, LocalDateTime pickUpDateTime, int orderCount) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.isPickUp = isPickUp;
        this.pickUpDateTime = pickUpDateTime;
        this.orderCount = orderCount;
    }
}
