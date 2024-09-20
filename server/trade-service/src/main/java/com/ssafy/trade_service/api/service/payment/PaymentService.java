package com.ssafy.trade_service.api.service.payment;

import com.ssafy.trade_service.api.client.MemberServiceClient;
import com.ssafy.trade_service.domain.order.repository.OrderRepository;
import com.ssafy.trade_service.domain.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final MemberServiceClient memberServiceClient;

    public PaymentCreateResponse payment(Long orderId, LocalDateTime current) {
        return null;
    }

}
