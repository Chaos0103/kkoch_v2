package com.ssafy.trade_service.api.service.order;

import com.ssafy.trade_service.IntegrationTestSupport;
import com.ssafy.trade_service.api.service.order.response.OrderCreateResponse;
import com.ssafy.trade_service.api.service.order.response.OrderPickUpResponse;
import com.ssafy.trade_service.common.exception.AppException;
import com.ssafy.trade_service.domain.bidinfo.Bid;
import com.ssafy.trade_service.domain.bidinfo.BidInfo;
import com.ssafy.trade_service.domain.bidinfo.repository.BidRepository;
import com.ssafy.trade_service.domain.order.Order;
import com.ssafy.trade_service.domain.order.OrderStatus;
import com.ssafy.trade_service.domain.order.PickUp;
import com.ssafy.trade_service.domain.order.repository.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderServiceTest extends IntegrationTestSupport {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BidRepository bidRepository;

    @DisplayName("회원이 경매에서 낙찰받은 품종 주문을 등록한다.")
    @Test
    void createOrder() {
        //given
        Long memberId = 1L;
        BidInfo bidInfo1 = createBidInfo(1L, 1000, LocalDateTime.of(2024, 9, 17, 5, 11));
        BidInfo bidInfo2 = createBidInfo(2L, 2000, LocalDateTime.of(2024, 9, 17, 5, 12));
        BidInfo bidInfo3 = createBidInfo(3L, 3000, LocalDateTime.of(2024, 9, 17, 5, 13));
        createBid(memberId, List.of(bidInfo1, bidInfo2, bidInfo3));

        //when
        OrderCreateResponse response = orderService.createOrder(memberId);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("orderStatus", OrderStatus.INIT)
            .hasFieldOrPropertyWithValue("totalPrice", 6000)
            .hasFieldOrPropertyWithValue("orderCount", 3);

        List<Order> orders = orderRepository.findAll();
        assertThat(orders).hasSize(1);

        Optional<Bid> findBid = bidRepository.findById(memberId);
        assertThat(findBid).isEmpty();
    }

    @DisplayName("결제가 완료되지 않은 주문은 픽업할 수 없다.")
    @ValueSource(strings = {"INIT", "PAYMENT_FAILED"})
    @ParameterizedTest
    void pickUpIsNotPaymentCompleted(OrderStatus orderStatus) {
        //given
        LocalDateTime current = LocalDateTime.of(2024, 9, 18, 10, 0);
        Long memberId = 1L;

        Order order = createOrder(memberId, orderStatus);

        //when
        assertThatThrownBy(() -> orderService.pickUp(order.getId(), current))
            .isInstanceOf(AppException.class)
            .hasMessage("결제되지 않은 주문입니다.");

        //then
        Optional<Order> findOrder = orderRepository.findById(order.getId());
        assertThat(findOrder).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("pickUp.isPickUp", false)
            .hasFieldOrPropertyWithValue("pickUp.pickUpDateTime", null);
    }

    @DisplayName("결제가 완료된 주문을 픽업한다.")
    @Test
    void pickUp() {
        //given
        LocalDateTime current = LocalDateTime.of(2024, 9, 18, 10, 0);
        Long memberId = 1L;

        Order order = createOrder(memberId, OrderStatus.PAYMENT_COMPLETED);

        //when
        OrderPickUpResponse response = orderService.pickUp(order.getId(), current);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("id", order.getId())
            .hasFieldOrPropertyWithValue("pickUpDateTime", order.getPickUp().getPickUpDateTime());

        Optional<Order> findOrder = orderRepository.findById(order.getId());
        assertThat(findOrder).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("pickUp.isPickUp", true)
            .hasFieldOrPropertyWithValue("pickUp.pickUpDateTime", current);
    }

    private Bid createBid(Long memberId, List<BidInfo> infos) {
        Bid bid = Bid.builder()
            .memberId(memberId)
            .infos(infos)
            .createdDateTime(LocalDateTime.now())
            .build();
        return bidRepository.save(bid);
    }

    private BidInfo createBidInfo(Long auctionVarietyId, int bidPrice, LocalDateTime bidDateTime) {
        return BidInfo.builder()
            .auctionVarietyId(auctionVarietyId)
            .bidPrice(bidPrice)
            .bidDateTime(bidDateTime)
            .build();
    }

    private Order createOrder(Long memberId, OrderStatus orderStatus) {
        Order order = Order.builder()
            .isDeleted(false)
            .memberId(memberId)
            .orderStatus(orderStatus)
            .totalPrice(10_000)
            .pickUp(PickUp.builder()
                .isPickUp(false)
                .pickUpDateTime(null)
                .build())
            .build();
        return orderRepository.save(order);
    }
}