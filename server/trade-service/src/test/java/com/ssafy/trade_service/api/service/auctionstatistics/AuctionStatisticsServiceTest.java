package com.ssafy.trade_service.api.service.auctionstatistics;

import com.ssafy.trade_service.IntegrationTestSupport;
import com.ssafy.trade_service.api.service.auctionstatistics.response.AuctionStatisticsCreateResponse;
import com.ssafy.trade_service.domain.auctionstatistics.AuctionStatistics;
import com.ssafy.trade_service.domain.auctionstatistics.repository.AuctionStatisticsRepository;
import com.ssafy.trade_service.domain.bidresult.BidResult;
import com.ssafy.trade_service.domain.bidresult.repository.BidResultRepository;
import com.ssafy.trade_service.domain.order.Order;
import com.ssafy.trade_service.domain.order.OrderStatus;
import com.ssafy.trade_service.domain.order.PickUp;
import com.ssafy.trade_service.domain.order.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class AuctionStatisticsServiceTest extends IntegrationTestSupport {

    @Autowired
    private AuctionStatisticsService auctionStatisticsService;

    @Autowired
    private AuctionStatisticsRepository auctionStatisticsRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BidResultRepository bidResultRepository;

    @DisplayName("하루 경매 내역의 통계를 계산한다.")
    @Test
    void calculateAuctionStatistics() {
        //given
        Order order = createOrder();
        createBidResult(order, 1L, "10031204", "SUPER", 10, 3000, LocalDateTime.of(2024, 8, 1, 23, 59, 59));
        createBidResult(order, 2L, "10031204", "SUPER", 10, 3000, LocalDateTime.of(2024, 8, 2, 0, 0, 0));
        createBidResult(order, 3L, "10031204", "SUPER", 10, 4000, LocalDateTime.of(2024, 8, 2, 10, 0, 0));
        createBidResult(order, 4L, "10031204", "SUPER", 10, 5000, LocalDateTime.of(2024, 8, 2, 23, 59, 59));
        createBidResult(order, 5L, "10031205", "SUPER", 10, 4000, LocalDateTime.of(2024, 8, 2, 10, 0, 4));
        createBidResult(order, 6L, "10031205", "ADVANCED", 10, 4000, LocalDateTime.of(2024, 8, 2, 10, 0, 5));
        createBidResult(order, 7L, "10031204", "SUPER", 10, 4000, LocalDateTime.of(2024, 8, 3, 0, 0, 0));

        LocalDate date = LocalDate.of(2024, 8, 2);

        //when
        AuctionStatisticsCreateResponse response = auctionStatisticsService.calculateAuctionStatistics(date);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("calculatedVarietyCount", 2)
            .hasFieldOrPropertyWithValue("calculatedDate", date);
        List<AuctionStatistics> auctionStatistics = auctionStatisticsRepository.findAll();
        assertThat(auctionStatistics).hasSize(3)
            .extracting("varietyCode", "plantGrade", "plantCount", "calculatedResult.avg", "calculatedResult.max", "calculatedResult.min")
            .containsExactlyInAnyOrder(
                tuple("10031204", "SUPER", 30, 4000, 5000, 3000),
                tuple("10031205", "SUPER", 10, 4000, 4000, 4000),
                tuple("10031205", "ADVANCED", 10, 4000, 4000, 4000)
            );
    }

    private Order createOrder() {
        Order order = Order.builder()
            .isDeleted(false)
            .memberId(1L)
            .orderStatus(OrderStatus.INIT)
            .totalPrice(10000)
            .pickUp(PickUp.builder()
                .isPickUp(false)
                .pickUpDateTime(null)
                .build())
            .build();
        return orderRepository.save(order);
    }

    private BidResult createBidResult(Order order, long auctionVarietyId, String varietyCode, String plantGrade, int plantCount, int bidPrice, LocalDateTime bidDateTime) {
        BidResult bidResult = BidResult.builder()
            .isDeleted(false)
            .order(order)
            .auctionVarietyId(auctionVarietyId)
            .varietyCode(varietyCode)
            .plantGrade(plantGrade)
            .plantCount(plantCount)
            .bidPrice(bidPrice)
            .bidDateTime(bidDateTime)
            .build();
        return bidResultRepository.save(bidResult);
    }

}