package com.ssafy.trade_service.api.controller.order;

import com.ssafy.trade_service.api.ApiResponse;
import com.ssafy.trade_service.api.controller.order.param.OrderSearchParam;
import com.ssafy.trade_service.api.service.order.OrderQueryService;
import com.ssafy.trade_service.domain.order.repository.response.OrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trade-service/orders")
public class OrderApiQueryController {

    private final OrderQueryService orderQueryService;

    @GetMapping
    public ApiResponse<OrderResponse> searchOrders(@Valid @ModelAttribute OrderSearchParam param) {
        return null;
    }
}
