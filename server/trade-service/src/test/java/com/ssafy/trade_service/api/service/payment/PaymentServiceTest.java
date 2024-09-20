package com.ssafy.trade_service.api.service.payment;

import com.ssafy.trade_service.IntegrationTestSupport;
import com.ssafy.trade_service.api.ApiResponse;
import com.ssafy.trade_service.api.client.response.BankAccountResponse;
import com.ssafy.trade_service.domain.order.Order;
import com.ssafy.trade_service.domain.order.OrderStatus;
import com.ssafy.trade_service.domain.order.PickUp;
import com.ssafy.trade_service.domain.order.repository.OrderRepository;
import com.ssafy.trade_service.domain.payment.Payment;
import com.ssafy.trade_service.domain.payment.PaymentStatus;
import com.ssafy.trade_service.domain.payment.repository.PaymentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class PaymentServiceTest extends IntegrationTestSupport {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @DisplayName("주문한 내역에 대해 결제를 한다.")
    @Test
    void payment() {
        //given
        LocalDateTime current = LocalDateTime.of(2024, 8, 1, 10, 0);
        Order order = createOrder();

        BankAccountResponse bankAccount = BankAccountResponse.builder()
            .bankCode("088")
            .accountNumber("12312123456")
            .build();
        given(memberServiceClient.searchBankAccount())
            .willReturn(ApiResponse.ok(bankAccount));

        //when
        PaymentCreateResponse response = paymentService.payment(order.getId(), current);

        //then
        Optional<Payment> findPayment = paymentRepository.findById(response.getId());
        assertThat(findPayment).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("bankAccount.bankCode", bankAccount.getBankCode())
            .hasFieldOrPropertyWithValue("bankAccount.accountNumber", bankAccount.getAccountNumber())
            .hasFieldOrPropertyWithValue("paymentInfo.paymentAmount", order.getTotalPrice())
            .hasFieldOrPropertyWithValue("paymentInfo.paymentStatus", PaymentStatus.PAYMENT_COMPLETED)
            .hasFieldOrPropertyWithValue("paymentInfo.paymentDateTime", current);

        Optional<Order> findOrder = orderRepository.findById(order.getId());
        assertThat(findOrder).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("orderStatus", OrderStatus.PAYMENT_COMPLETED);
    }

    private Order createOrder() {
        Order order = Order.builder()
            .isDeleted(false)
            .memberId(1L)
            .orderStatus(OrderStatus.INIT)
            .totalPrice(100_000)
            .pickUp(PickUp.builder()
                .isPickUp(false)
                .pickUpDateTime(null)
                .build())
            .build();
        return orderRepository.save(order);
    }
}