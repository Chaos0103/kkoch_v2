package com.ssafy.trade_service.api.service.order.response;

import com.ssafy.trade_service.domain.order.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class OrderPickUpResponse {

    private Long id;
    private LocalDateTime pickUpDateTime;

    @Builder
    private OrderPickUpResponse(Long id, LocalDateTime pickUpDateTime) {
        this.id = id;
        this.pickUpDateTime = pickUpDateTime;
    }

    public static OrderPickUpResponse of(Order order) {
        return new OrderPickUpResponse(order.getId(), order.getPickUp().getPickUpDateTime());
    }
}
