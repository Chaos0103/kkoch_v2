package com.ssafy.trade_service.domain.auctionreservation.repository;

import com.ssafy.trade_service.IntegrationTestSupport;
import com.ssafy.trade_service.domain.auctionreservation.AuctionReservation;
import com.ssafy.trade_service.domain.auctionreservation.PlantGrade;
import com.ssafy.trade_service.domain.auctionreservation.Price;
import com.ssafy.trade_service.domain.auctionreservation.ReservationInfo;
import com.ssafy.trade_service.domain.auctionreservation.repository.response.AuctionReservationResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class AuctionReservationQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private AuctionReservationQueryRepository auctionReservationQueryRepository;

    @Autowired
    private AuctionReservationRepository auctionReservationRepository;

    @DisplayName("회원 ID와 경매 일정 ID로 경매 예약 목록을 조회한다.")
    @Test
    void findAllByAuctionScheduleId() {
        //given
        AuctionReservation auctionReservation1 = createAuctionReservation(false, 1L, 1, "10031204");
        createAuctionReservation(true, 1L, 1, "10031205");
        createAuctionReservation(false, 2L, 1, "10031206");
        createAuctionReservation(false, 1L, 2, "10031207");
        AuctionReservation auctionReservation2 = createAuctionReservation(false, 1L, 1, "10031208");

        //when
        List<AuctionReservationResponse> content = auctionReservationQueryRepository.findAllByAuctionScheduleId(1L, 1);

        //then
        assertThat(content).hasSize(2)
            .extracting("id", "varietyCode")
            .containsExactly(
                tuple(auctionReservation1.getId(), auctionReservation1.getReservationInfo().getVarietyCode()),
                tuple(auctionReservation2.getId(), auctionReservation2.getReservationInfo().getVarietyCode())
            );
    }

    private AuctionReservation createAuctionReservation(boolean isDeleted, long memberId, int auctionScheduleId, String varietyCode) {
        AuctionReservation auctionReservation = AuctionReservation.builder()
            .isDeleted(isDeleted)
            .memberId(memberId)
            .auctionScheduleId(auctionScheduleId)
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