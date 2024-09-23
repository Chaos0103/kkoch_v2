package com.ssafy.trade_service.api.controller.order;

import com.ssafy.trade_service.api.ApiResponse;
import com.ssafy.trade_service.api.PageResponse;
import com.ssafy.trade_service.api.controller.order.param.OrderSearchParam;
import com.ssafy.trade_service.api.service.order.OrderQueryService;
import com.ssafy.trade_service.api.service.order.response.OrderDetailResponse;
import com.ssafy.trade_service.common.util.PageUtils;
import com.ssafy.trade_service.domain.order.repository.response.OrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trade-service/orders")
public class OrderApiQueryController {

    private final OrderQueryService orderQueryService;

    @GetMapping
    public ApiResponse<PageResponse<OrderResponse>> searchOrders(@Valid @ModelAttribute OrderSearchParam param) {
        int pageNumber = PageUtils.parsePageNumber(param.getPage());

        PageResponse<OrderResponse> response = orderQueryService.searchOrders(pageNumber);

        return ApiResponse.ok(response);
    }

    @GetMapping("/{orderId}")
    public ApiResponse<OrderDetailResponse> searchOrder(@PathVariable Long orderId) {
        return null;
    }
}
