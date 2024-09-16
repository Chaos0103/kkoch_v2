package com.ssafy.trade_service.api.service.auctionreservation;

import com.ssafy.trade_service.IntegrationTestSupport;
import com.ssafy.trade_service.api.service.auctionreservation.request.AuctionReservationCreateServiceRequest;
import com.ssafy.trade_service.api.service.auctionreservation.request.AuctionReservationModifyServiceRequest;
import com.ssafy.trade_service.api.service.auctionreservation.response.AuctionReservationCreateResponse;
import com.ssafy.trade_service.api.service.auctionreservation.response.AuctionReservationModifyResponse;
import com.ssafy.trade_service.api.service.auctionreservation.response.AuctionReservationRemoveResponse;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

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

        AuctionReservationCreateServiceRequest request = AuctionReservationCreateServiceRequest.builder()
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

        AuctionReservationCreateServiceRequest request = AuctionReservationCreateServiceRequest.builder()
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
        AuctionReservationCreateServiceRequest request = AuctionReservationCreateServiceRequest.builder()
            .varietyCode("10031204")
            .plantGrade(PlantGrade.SUPER)
            .plantCount(10)
            .desiredPrice(Price.of(3000))
            .build();

        //when
        AuctionReservationCreateResponse response = auctionReservationService.createAuctionReservation(1, request);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("plantGrade", PlantGrade.SUPER)
            .hasFieldOrPropertyWithValue("plantCount", 10)
            .hasFieldOrPropertyWithValue("desiredPrice", 3000);

        List<AuctionReservation> auctionReservations = auctionReservationRepository.findAll();
        assertThat(auctionReservations).hasSize(1);
    }

    @DisplayName("경매 예약 수정시 같은 경매에 최대 100단의 예약을 등록할 수 있다.")
    @Test
    void modifyMaximumPlantCount() {
        //given
        AuctionReservation auctionReservation = createAuctionReservation(20);
        createAuctionReservation(20);
        createAuctionReservation(20);
        createAuctionReservation(20);
        createAuctionReservation(20);

        AuctionReservationModifyServiceRequest request = AuctionReservationModifyServiceRequest.builder()
            .plantGrade(PlantGrade.SUPER)
            .plantCount(21)
            .desiredPrice(Price.of(3000))
            .build();

        //when
        assertThatThrownBy(() -> auctionReservationService.modifyAuctionReservation(auctionReservation.getId(), request))
            .isInstanceOf(AppException.class)
            .hasMessage("경매에 등록할 수 있는 최대 화훼단수를 초과했습니다.");

        //then
        Optional<AuctionReservation> findAuctionReservation = auctionReservationRepository.findById(auctionReservation.getId());
        assertThat(findAuctionReservation).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("plantCount", 20);
    }

    @DisplayName("수정할 경매 예약 정보를 입력 받아 경매 예약을 수정한다.")
    @Test
    void modifyAuctionReservation() {
        //given
        AuctionReservation auctionReservation = createAuctionReservation(10);

        AuctionReservationModifyServiceRequest request = AuctionReservationModifyServiceRequest.builder()
            .plantGrade(PlantGrade.ADVANCED)
            .plantCount(15)
            .desiredPrice(Price.of(2500))
            .build();

        //when
        AuctionReservationModifyResponse response = auctionReservationService.modifyAuctionReservation(auctionReservation.getId(), request);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("plantGrade", PlantGrade.ADVANCED)
            .hasFieldOrPropertyWithValue("plantCount", 15)
            .hasFieldOrPropertyWithValue("desiredPrice", 2500);

        List<AuctionReservation> auctionReservations = auctionReservationRepository.findAll();
        assertThat(auctionReservations).hasSize(1)
            .extracting("reservationInfo.plantGrade", "reservationInfo.plantCount", "reservationInfo.desiredPrice.value")
            .containsExactly(
                tuple(PlantGrade.ADVANCED, 15, 2500)
            );
    }

    @DisplayName("경매 예약을 삭제한다.")
    @Test
    void removeAuctionReservation() {
        //given
        AuctionReservation auctionReservation = createAuctionReservation(10);

        //when
        AuctionReservationRemoveResponse response = auctionReservationService.removeAuctionReservation(auctionReservation.getId());

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("plantGrade", PlantGrade.SUPER)
            .hasFieldOrPropertyWithValue("plantCount", 10)
            .hasFieldOrPropertyWithValue("desiredPrice", 3000);

        Optional<AuctionReservation> findAuctionReservation = auctionReservationRepository.findById(auctionReservation.getId());
        assertThat(findAuctionReservation).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("isDeleted", true);
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