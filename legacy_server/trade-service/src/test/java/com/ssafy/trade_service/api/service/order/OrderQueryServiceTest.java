package com.ssafy.trade_service.api.service.order;

import com.ssafy.trade_service.IntegrationTestSupport;
import com.ssafy.trade_service.api.ApiResponse;
import com.ssafy.trade_service.api.PageResponse;
import com.ssafy.trade_service.api.client.AuctionServiceClient;
import com.ssafy.trade_service.api.client.response.AuctionVarietyResponse;
import com.ssafy.trade_service.api.service.order.response.OrderDetailResponse;
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
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.BDDMockito.*;

class OrderQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private OrderQueryService orderQueryService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BidResultRepository bidResultRepository;

    @MockBean
    private AuctionServiceClient auctionServiceClient;

    @DisplayName("회원이 주문한 주문 목록을 조회한다.")
    @Test
    void searchOrders() {
        //given
        Order order1 = createOrder(3000);
        createBidResult(order1, 1L, 3000, LocalDateTime.of(2024, 9, 18, 5, 10));
        createBidResult(order1, 1L, 3000, LocalDateTime.of(2024, 9, 18, 5, 10));

        Order order2 = createOrder(3000);
        createBidResult(order2, 1L, 3000, LocalDateTime.of(2024, 9, 18, 5, 10));

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

    @DisplayName("주문 내역을 조회한다.")
    @Test
    void searchOrder() {
        //given
        Order order = createOrder(96000);
        BidResult bidResult1 = createBidResult(order, 1L, 3100, LocalDateTime.of(2024, 9, 18, 5, 11));
        BidResult bidResult2 = createBidResult(order, 2L, 3200, LocalDateTime.of(2024, 9, 18, 5, 12));
        BidResult bidResult3 = createBidResult(order, 3L, 3300, LocalDateTime.of(2024, 9, 18, 5, 13));

        AuctionVarietyResponse auctionVariety1 = createAuctionVariety(1L);
        AuctionVarietyResponse auctionVariety2 = createAuctionVariety(2L);
        AuctionVarietyResponse auctionVariety3 = createAuctionVariety(3L);
        List<AuctionVarietyResponse> auctionVarieties = List.of(auctionVariety1, auctionVariety2, auctionVariety3);
        given(auctionServiceClient.findAllByIdIn(anyList()))
            .willReturn(ApiResponse.ok(auctionVarieties));

        //when
        OrderDetailResponse response = orderQueryService.searchOrder(order.getId());

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("id", order.getId())
            .hasFieldOrPropertyWithValue("orderStatus", order.getOrderStatus())
            .hasFieldOrPropertyWithValue("totalPrice", order.getTotalPrice())
            .hasFieldOrPropertyWithValue("isPickUp", order.getPickUp().getIsPickUp())
            .hasFieldOrPropertyWithValue("pickUpDateTime", order.getPickUp().getPickUpDateTime());
        assertThat(response.getBidResults()).hasSize(3)
            .extracting("id", "bidPrice", "bidDateTime")
            .containsExactly(
                tuple(bidResult1.getId(), bidResult1.getBidPrice(), bidResult1.getBidDateTime()),
                tuple(bidResult2.getId(), bidResult2.getBidPrice(), bidResult2.getBidDateTime()),
                tuple(bidResult3.getId(), bidResult3.getBidPrice(), bidResult3.getBidDateTime())
            );
    }

    private Order createOrder(int totalPrice) {
        Order order = Order.builder()
            .isDeleted(false)
            .memberId(1L)
            .orderStatus(OrderStatus.INIT)
            .totalPrice(totalPrice)
            .pickUp(PickUp.builder()
                .isPickUp(false)
                .pickUpDateTime(null)
                .build()
            )
            .build();
        return orderRepository.save(order);
    }

    private BidResult createBidResult(Order order, long auctionVarietyId, int bidPrice, LocalDateTime bidDateTime) {
        BidResult bidResult = BidResult.builder()
            .isDeleted(false)
            .order(order)
            .auctionVarietyId(auctionVarietyId)
            .bidPrice(bidPrice)
            .bidDateTime(bidDateTime)
            .build();
        return bidResultRepository.save(bidResult);
    }

    private AuctionVarietyResponse createAuctionVariety(long auctionVarietyId) {
        return AuctionVarietyResponse.builder()
            .auctionVarietyId(auctionVarietyId)
            .varietyCode("10031204")
            .plantCategory("CUT_FLOWERS")
            .itemName("장미")
            .varietyName("하트앤소울")
            .plantGrade("SUPER")
            .plantCount(10)
            .region("광주")
            .shipper("김출하")
            .build();
    }
}