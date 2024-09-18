package com.ssafy.trade_service.api.controller.order;

import com.ssafy.trade_service.api.ApiResponse;
import com.ssafy.trade_service.api.service.order.OrderService;
import com.ssafy.trade_service.api.service.order.response.OrderPickUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trade-service/orders")
public class OrderApiController {

    private final OrderService orderService;

    @PostMapping("/{orderId}/pickup")
    public ApiResponse<OrderPickUpResponse> pickUp(@PathVariable Long orderId) {
        return null;
    }
}
