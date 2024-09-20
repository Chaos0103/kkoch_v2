package com.ssafy.trade_service.api.controller.payment;

import com.ssafy.trade_service.api.ApiResponse;
import com.ssafy.trade_service.api.service.payment.PaymentCreateResponse;
import com.ssafy.trade_service.api.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trade-service/orders/{orderId}/payment")
public class PaymentApiController {

    private final PaymentService paymentService;

    @PostMapping
    public ApiResponse<PaymentCreateResponse> payment(@PathVariable Long orderId) {
        return null;
    }
}
