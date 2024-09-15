package com.ssafy.trade_service.api.service.auctionreservation;

import com.ssafy.trade_service.IntegrationTestSupport;
import com.ssafy.trade_service.api.service.auctionreservation.request.AuctionReservationServiceRequest;
import com.ssafy.trade_service.api.service.auctionreservation.response.AuctionReservationResponse;
import com.ssafy.trade_service.common.exception.AppException;
import com.ssafy.trade_service.domain.auctionreservation.AuctionReservation;
import com.ssafy.trade_service.domain.auctionreservation.PlantGrade;
import com.ssafy.trade_service.domain.auctionreservation.Price;
import com.ssafy.trade_service.domain.auctionreservation.ReservationInfo;
import com.ssafy.trade_service.domain.auctionreservation.repository.AuctionReservationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AuctionReservationServiceTest extends IntegrationTestSupport {

    @Autowired
    private AuctionReservationService auctionReservationService;

    @Autowired
    private AuctionReservationRepository auctionReservationRepository;

    @DisplayName("경매 예약 등록시 같은 경매에 최대 10건의 예약을 등록할 수 있다.")
    @Test
    void createMaximumAuctionReservation() {
        //given
        createAuctionReservation(5);
        createAuctionReservation(5);
        createAuctionReservation(5);
        createAuctionReservation(5);
        createAuctionReservation(5);
        createAuctionReservation(5);
        createAuctionReservation(5);
        createAuctionReservation(5);
        createAuctionReservation(5);
        createAuctionReservation(5);

        AuctionReservationServiceRequest request = AuctionReservationServiceRequest.builder()
            .varietyCode("10031204")
            .plantGrade(PlantGrade.SUPER)
            .plantCount(10)
            .desiredPrice(Price.of(3000))
            .build();

        //when
        assertThatThrownBy(() -> auctionReservationService.createAuctionReservation(1, request))
            .isInstanceOf(AppException.class)
            .hasMessage("경매에 등록할 수 있는 최대 예약수를 초과했습니다.");

        //then
        List<AuctionReservation> auctionReservations = auctionReservationRepository.findAll();
        assertThat(auctionReservations).hasSize(10);
    }

    @DisplayName("경매 예약 등록시 같은 경매에 최대 100단의 예약을 등록할 수 있다.")
    @Test
    void createMaximumPlantCount() {
        //given
        createAuctionReservation(20);
        createAuctionReservation(20);
        createAuctionReservation(20);
        createAuctionReservation(20);
        createAuctionReservation(20);

        AuctionReservationServiceRequest request = AuctionReservationServiceRequest.builder()
            .varietyCode("10031204")
            .plantGrade(PlantGrade.SUPER)
            .plantCount(1)
            .desiredPrice(Price.of(3000))
            .build();

        //when
        assertThatThrownBy(() -> auctionReservationService.createAuctionReservation(1, request))
            .isInstanceOf(AppException.class)
            .hasMessage("경매에 등록할 수 있는 최대 화훼단수를 초과했습니다.");

        //then
        List<AuctionReservation> auctionReservations = auctionReservationRepository.findAll();
        assertThat(auctionReservations).hasSize(5);
    }

    @DisplayName("경매 예약 정보를 입력 받아 경매 예약을 등록한다.")
    @Test
    void createAuctionReservation() {
        //given
        AuctionReservationServiceRequest request = AuctionReservationServiceRequest.builder()
            .varietyCode("10031204")
            .plantGrade(PlantGrade.SUPER)
            .plantCount(10)
            .desiredPrice(Price.of(3000))
            .build();

        //when
        AuctionReservationResponse response = auctionReservationService.createAuctionReservation(1, request);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("plantGrade", PlantGrade.SUPER)
            .hasFieldOrPropertyWithValue("plantCount", 10)
            .hasFieldOrPropertyWithValue("desiredPrice", 3000);

        List<AuctionReservation> auctionReservations = auctionReservationRepository.findAll();
        assertThat(auctionReservations).hasSize(1);
    }

    private AuctionReservation createAuctionReservation(int plantCount) {
        AuctionReservation auctionReservation = AuctionReservation.builder()
            .isDeleted(false)
            .memberId(1L)
            .auctionScheduleId(1)
            .reservationInfo(ReservationInfo.builder()
                .varietyCode("10031204")
                .plantGrade(PlantGrade.SUPER)
                .plantCount(plantCount)
                .desiredPrice(Price.of(3000))
                .build())
            .build();
        return auctionReservationRepository.save(auctionReservation);
    }
}