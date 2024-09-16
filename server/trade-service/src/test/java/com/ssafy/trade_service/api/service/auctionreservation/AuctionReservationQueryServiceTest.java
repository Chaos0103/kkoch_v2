package com.ssafy.trade_service.api.service.auctionreservation;

import com.ssafy.trade_service.IntegrationTestSupport;
import com.ssafy.trade_service.api.ListResponse;
import com.ssafy.trade_service.domain.auctionreservation.AuctionReservation;
import com.ssafy.trade_service.domain.auctionreservation.PlantGrade;
import com.ssafy.trade_service.domain.auctionreservation.Price;
import com.ssafy.trade_service.domain.auctionreservation.ReservationInfo;
import com.ssafy.trade_service.domain.auctionreservation.repository.AuctionReservationRepository;
import com.ssafy.trade_service.domain.auctionreservation.repository.response.AuctionReservationResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class AuctionReservationQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private AuctionReservationQueryService auctionReservationQueryService;

    @Autowired
    private AuctionReservationRepository auctionReservationRepository;


    @DisplayName("회원이 경매 일정에 등록한 경매 예약 목록을 조회한다.")
    @Test
    void searchAuctionReservations() {
        //given
        AuctionReservation auctionReservation1 = createAuctionReservation("10031204");
        AuctionReservation auctionReservation2 = createAuctionReservation("10031205");
        AuctionReservation auctionReservation3 = createAuctionReservation("10031206");

        //when
        ListResponse<AuctionReservationResponse> response = auctionReservationQueryService.searchAuctionReservations(1);

        //then
        assertThat(response.getContent()).hasSize(3)
            .extracting("id", "varietyCode")
            .containsExactly(
                tuple(auctionReservation1.getId(), auctionReservation1.getReservationInfo().getVarietyCode()),
                tuple(auctionReservation2.getId(), auctionReservation2.getReservationInfo().getVarietyCode()),
                tuple(auctionReservation3.getId(), auctionReservation3.getReservationInfo().getVarietyCode())
            );
    }

    private AuctionReservation createAuctionReservation(String varietyCode) {
        AuctionReservation auctionReservation = AuctionReservation.builder()
            .isDeleted(false)
            .memberId(1L)
            .auctionScheduleId(1)
            .reservationInfo(
                ReservationInfo.builder()
                    .varietyCode(varietyCode)
                    .plantGrade(PlantGrade.SUPER)
                    .plantCount(10)
                    .desiredPrice(Price.of(3000))
                    .build()
            )
            .build();
        return auctionReservationRepository.save(auctionReservation);
    }
}