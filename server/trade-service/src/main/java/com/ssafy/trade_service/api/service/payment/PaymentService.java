package com.ssafy.trade_service.api.service.payment;

import com.ssafy.trade_service.api.ApiResponse;
import com.ssafy.trade_service.api.client.MemberServiceClient;
import com.ssafy.trade_service.api.client.response.BankAccountResponse;
import com.ssafy.trade_service.domain.order.Order;
import com.ssafy.trade_service.domain.order.repository.OrderRepository;
import com.ssafy.trade_service.domain.payment.BankAccount;
import com.ssafy.trade_service.domain.payment.Payment;
import com.ssafy.trade_service.domain.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static com.ssafy.trade_service.domain.order.repository.OrderRepository.NO_SUCH_ORDER;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final MemberServiceClient memberServiceClient;

    public PaymentCreateResponse payment(Long orderId, LocalDateTime current) {
        Order order = findOrderById(orderId);

        BankAccount bankAccount = getBankAccount();

        Payment payment = order.toPayment(bankAccount, current);
        Payment savedPayment = paymentRepository.save(payment);

        return PaymentCreateResponse.of(savedPayment);
    }

    private Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_ORDER));
    }

    private BankAccount getBankAccount() {
        ApiResponse<BankAccountResponse> response = memberServiceClient.searchBankAccount();
        return response.getData().toBankAccount();
    }
}
