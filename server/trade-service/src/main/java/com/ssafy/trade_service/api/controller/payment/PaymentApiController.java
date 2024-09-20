package com.ssafy.trade_service.api.controller.payment;

import com.ssafy.trade_service.api.ApiResponse;
import com.ssafy.trade_service.api.service.payment.PaymentCreateResponse;
import com.ssafy.trade_service.api.service.payment.PaymentService;
import com.ssafy.trade_service.common.util.TimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trade-service/orders/{orderId}/payment")
public class PaymentApiController {

    private final PaymentService paymentService;

    @PostMapping
    public ApiResponse<PaymentCreateResponse> payment(@PathVariable Long orderId) {
        LocalDateTime current = TimeUtils.getCurrentDateTime();

        PaymentCreateResponse response = paymentService.payment(orderId, current);

        return ApiResponse.ok(response);
    }
}
