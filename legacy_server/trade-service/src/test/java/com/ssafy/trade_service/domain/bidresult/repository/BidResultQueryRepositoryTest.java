package com.ssafy.trade_service.domain.bidresult.repository;

import com.ssafy.trade_service.IntegrationTestSupport;
import com.ssafy.trade_service.domain.bidresult.BidResult;
import com.ssafy.trade_service.domain.bidresult.repository.dto.BidResultDto;
import com.ssafy.trade_service.domain.order.Order;
import com.ssafy.trade_service.domain.order.OrderStatus;
import com.ssafy.trade_service.domain.order.PickUp;
import com.ssafy.trade_service.domain.order.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class BidResultQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private BidResultQueryRepository bidResultQueryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BidResultRepository bidResultRepository;

    @DisplayName("주문에 등록된 낙찰 결과 목록을 조회한다.")
    @Test
    void findAllByOrderId() {
        //given
        Order order = createOrder();
        BidResult bidResult1 = createBidResult(order);
        BidResult bidResult2 = createBidResult(order);

        //when
        List<BidResultDto> content = bidResultQueryRepository.findAllByOrderId(order.getId());

        //then
        assertThat(content).hasSize(2)
            .extracting("id", "auctionVarietyId")
            .containsExactly(
                tuple(bidResult1.getId(), bidResult1.getAuctionVarietyId()),
                tuple(bidResult2.getId(), bidResult2.getAuctionVarietyId())
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