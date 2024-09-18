package com.ssafy.trade_service.api.controller.order;

import com.ssafy.trade_service.api.ApiResponse;
import com.ssafy.trade_service.api.service.order.OrderService;
import com.ssafy.trade_service.api.service.order.response.OrderPickUpResponse;
import com.ssafy.trade_service.common.util.TimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trade-service/orders")
public class OrderApiController {

    private final OrderService orderService;

    @PostMapping("/{orderId}/pickup")
    public ApiResponse<OrderPickUpResponse> pickUp(@PathVariable Long orderId) {
        LocalDateTime current = TimeUtils.getCurrentDateTime();

        OrderPickUpResponse response = orderService.pickUp(orderId, current);

        return ApiResponse.ok(response);
    }
}
