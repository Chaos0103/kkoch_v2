package com.ssafy.trade_service.domain.order.repository;

import com.ssafy.trade_service.IntegrationTestSupport;
import com.ssafy.trade_service.domain.bidresult.BidResult;
import com.ssafy.trade_service.domain.bidresult.repository.BidResultRepository;
import com.ssafy.trade_service.domain.order.Order;
import com.ssafy.trade_service.domain.order.OrderStatus;
import com.ssafy.trade_service.domain.order.PickUp;
import com.ssafy.trade_service.domain.order.repository.dto.OrderDetailDto;
import com.ssafy.trade_service.domain.order.repository.response.OrderResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

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

    @DisplayName("회원 ID로 주문 목록 갯수를 조회한다.")
    @Test
    void countByMemberId() {
        //given
        createOrder();
        createOrder();

        //when
        int total = orderQueryRepository.countByMemberId(1L);

        //then
        assertThat(total).isEqualTo(2);
    }

    @DisplayName("주문 ID로 주문을 조회한다.")
    @Test
    void findById() {
        //given
        Order order = createOrder();

        //when
        Optional<OrderDetailDto> dto = orderQueryRepository.findById(order.getId());

        //then
        assertThat(dto).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("id", order.getId())
            .hasFieldOrPropertyWithValue("orderStatus", order.getOrderStatus())
            .hasFieldOrPropertyWithValue("totalPrice", order.getTotalPrice())
            .hasFieldOrPropertyWithValue("isPickUp", order.getPickUp().getIsPickUp())
            .hasFieldOrPropertyWithValue("pickUpDateTime", order.getPickUp().getPickUpDateTime());
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