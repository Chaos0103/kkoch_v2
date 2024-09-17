package com.ssafy.trade_service.domain.order.repository;

import com.ssafy.trade_service.IntegrationTestSupport;
import com.ssafy.trade_service.domain.bidresult.BidResult;
import com.ssafy.trade_service.domain.bidresult.repository.BidResultRepository;
import com.ssafy.trade_service.domain.order.Order;
import com.ssafy.trade_service.domain.order.OrderStatus;
import com.ssafy.trade_service.domain.order.PickUp;
import com.ssafy.trade_service.domain.order.repository.response.OrderResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class OrderQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private OrderQueryRepository orderQueryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BidResultRepository bidResultRepository;

    @DisplayName("회원 ID로 주문 목록을 조회한다.")
    @Test
    void findAllByMemberId() {
        //given
        Order order1 = createOrder();
        createBidResult(order1);
        createBidResult(order1);

        Order order2 = createOrder();
        createBidResult(order2);

        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        List<OrderResponse> content = orderQueryRepository.findAllByMemberId(1L, pageRequest);

        //then
        assertThat(content).hasSize(2)
            .extracting("id", "orderCount")
            .containsExactly(
                tuple(order2.getId(), 1),
                tuple(order1.getId(), 2)
            );
    }

    private Order createOrder() {
        Order order = Order.builder()
            .isDeleted(false)
            .memberId(1L)
            .orderStatus(OrderStatus.INIT)
            .totalPrice(3000)
            .pickUp(PickUp.builder()
                .isPickUp(false)
                .pickUpDateTime(null)
                .build()
            )
            .build();
        return orderRepository.save(order);
    }

    private BidResult createBidResult(Order order) {
        BidResult bidResult = BidResult.builder()
            .isDeleted(false)
            .order(order)
            .auctionVarietyId(1L)
            .bidPrice(3000)
            .bidDateTime(LocalDateTime.of(2024, 9, 18, 5, 10))
            .build();
        return bidResultRepository.save(bidResult);
    }
}