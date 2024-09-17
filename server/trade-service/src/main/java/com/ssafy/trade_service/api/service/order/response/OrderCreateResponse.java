package com.ssafy.trade_service.api.service.order.response;

import com.ssafy.trade_service.domain.order.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCreateResponse {

    private long id;
    private OrderStatus orderStatus;
    private int totalPrice;
    private int orderCount;

    @Builder
    private OrderCreateResponse(long id, OrderStatus orderStatus, int totalPrice, int orderCount) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.orderCount = orderCount;
    }
}
