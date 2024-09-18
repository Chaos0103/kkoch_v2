package com.ssafy.trade_service.api.service.order;

import com.ssafy.trade_service.IntegrationTestSupport;
import com.ssafy.trade_service.api.PageResponse;
import com.ssafy.trade_service.domain.bidresult.BidResult;
import com.ssafy.trade_service.domain.bidresult.repository.BidResultRepository;
import com.ssafy.trade_service.domain.order.Order;
import com.ssafy.trade_service.domain.order.OrderStatus;
import com.ssafy.trade_service.domain.order.PickUp;
import com.ssafy.trade_service.domain.order.repository.OrderRepository;
import com.ssafy.trade_service.domain.order.repository.response.OrderResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class OrderQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private OrderQueryService orderQueryService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BidResultRepository bidResultRepository;

    @DisplayName("회원이 주문한 주문 목록을 조회한다.")
    @Test
    void searchOrders() {
        //given
        Order order1 = createOrder();
        createBidResult(order1);
        createBidResult(order1);

        Order order2 = createOrder();
        createBidResult(order2);

        //when
        PageResponse<OrderResponse> response = orderQueryService.searchOrders(1);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("currentPage", 1)
            .hasFieldOrPropertyWithValue("size", 10)
            .hasFieldOrPropertyWithValue("isFirst", true)
            .hasFieldOrPropertyWithValue("isLast", true);
        assertThat(response.getContent()).hasSize(2)
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