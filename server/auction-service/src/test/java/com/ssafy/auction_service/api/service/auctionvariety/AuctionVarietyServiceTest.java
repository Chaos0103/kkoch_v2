package com.ssafy.auction_service.api.service.auctionvariety;

import com.ssafy.auction_service.IntegrationTestSupport;
import com.ssafy.auction_service.api.service.auctionvariety.request.AuctionVarietyCreateServiceRequest;
import com.ssafy.auction_service.api.service.auctionvariety.request.AuctionVarietyModifyServiceRequest;
import com.ssafy.auction_service.api.service.auctionvariety.response.AuctionVarietyCreateResponse;
import com.ssafy.auction_service.api.service.auctionvariety.response.AuctionVarietyModifyResponse;
import com.ssafy.auction_service.api.service.auctionvariety.response.AuctionVarietyRemoveResponse;
import com.ssafy.auction_service.common.exception.AppException;
import com.ssafy.auction_service.domain.auctionschedule.AuctionInfo;
import com.ssafy.auction_service.domain.auctionschedule.AuctionSchedule;
import com.ssafy.auction_service.domain.auctionschedule.AuctionStatus;
import com.ssafy.auction_service.domain.auctionschedule.JointMarket;
import com.ssafy.auction_service.domain.auctionschedule.repository.AuctionScheduleRepository;
import com.ssafy.auction_service.domain.auctionvariety.*;
import com.ssafy.auction_service.domain.auctionvariety.repository.AuctionVarietyRepository;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import com.ssafy.auction_service.domain.variety.Variety;
import com.ssafy.auction_service.domain.variety.VarietyInfo;
import com.ssafy.auction_service.domain.variety.repository.VarietyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AuctionVarietyServiceTest extends IntegrationTestSupport {

    @Autowired
    private AuctionVarietyService auctionVarietyService;

    @Autowired
    private AuctionVarietyRepository auctionVarietyRepository;

    @Autowired
    private VarietyRepository varietyRepository;

    @Autowired
    private AuctionScheduleRepository auctionScheduleRepository;

    @DisplayName("경매 품종 등록시 경매 상태가 INIT 상태일 때 경매 품종을 등록할 수 있다.")
    @CsvSource({"READY", "PROGRESS", "COMPLETE"})
    @ParameterizedTest
    void createAuctionVarietyAuctionStateNotInit(AuctionStatus status) {
        //given
        Variety variety = createVariety();
        AuctionSchedule auctionSchedule = createAuctionSchedule(PlantCategory.CUT_FLOWERS, status);

        AuctionVarietyCreateServiceRequest request = AuctionVarietyCreateServiceRequest.builder()
            .plantGrade(PlantGrade.SUPER)
            .plantCount(10)
            .auctionStartPrice(Price.of(4500))
            .region("광주")
            .shipper("김출하")
            .build();

        //when
        assertThatThrownBy(() -> auctionVarietyService.createAuctionVariety(variety.getCode(), auctionSchedule.getId(), request))
            .isInstanceOf(AppException.class)
            .hasMessage("경매 품종을 등록할 수 없습니다.");

        //then
        List<AuctionVariety> auctionVarieties = auctionVarietyRepository.findAll();
        assertThat(auctionVarieties).isEmpty();
    }

    @DisplayName("경매 일정의 화훼부류와 등록할 품종의 화훼부류가 다르다면 예외가 발생한다.")
    @Test
    void createAuctionVarietyNotEqualsPlantCategory() {
        //given
        Variety variety = createVariety();
        AuctionSchedule auctionSchedule = createAuctionSchedule(PlantCategory.ORCHID, AuctionStatus.INIT);

        AuctionVarietyCreateServiceRequest request = AuctionVarietyCreateServiceRequest.builder()
            .plantGrade(PlantGrade.SUPER)
            .plantCount(10)
            .auctionStartPrice(Price.of(4500))
            .region("광주")
            .shipper("김출하")
            .build();

        //when
        assertThatThrownBy(() -> auctionVarietyService.createAuctionVariety(variety.getCode(), auctionSchedule.getId(), request))
            .isInstanceOf(AppException.class)
            .hasMessage("해당 경매에 등록할 수 없는 품종입니다.");

        //then
        List<AuctionVariety> auctionVarieties = auctionVarietyRepository.findAll();
        assertThat(auctionVarieties).isEmpty();
    }

    @DisplayName("경매 품종 정보를 입력 받아 경매 품종을 등록한다.")
    @Test
    void createAuctionVariety() {
        //given
        Variety variety = createVariety();
        AuctionSchedule auctionSchedule = createAuctionSchedule(PlantCategory.CUT_FLOWERS, AuctionStatus.INIT);

        AuctionVarietyCreateServiceRequest request = AuctionVarietyCreateServiceRequest.builder()
            .plantGrade(PlantGrade.SUPER)
            .plantCount(10)
            .auctionStartPrice(Price.of(4500))
            .region("광주")
            .shipper("김출하")
            .build();

        //when
        AuctionVarietyCreateResponse response = auctionVarietyService.createAuctionVariety(variety.getCode(), auctionSchedule.getId(), request);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("listingNumber", "00001")
            .hasFieldOrPropertyWithValue("plantGrade", PlantGrade.SUPER)
            .hasFieldOrPropertyWithValue("plantCount", 10)
            .hasFieldOrPropertyWithValue("auctionStartPrice", 4500)
            .hasFieldOrPropertyWithValue("region", "광주")
            .hasFieldOrPropertyWithValue("shipper", "김출하");

        List<AuctionVariety> auctionVarieties = auctionVarietyRepository.findAll();
        assertThat(auctionVarieties).hasSize(1);
    }

    @DisplayName("경매 품종 수정시 경매 상태가 INIT 상태일 때 경매 품종을 수정할 수 있다.")
    @CsvSource({"READY", "PROGRESS", "COMPLETE"})
    @ParameterizedTest
    void modifyAuctionVarietyAuctionStateNotInit(AuctionStatus status) {
        //given
        Variety variety = createVariety();
        AuctionSchedule auctionSchedule = createAuctionSchedule(PlantCategory.CUT_FLOWERS, status);
        AuctionVariety auctionVariety = createAuctionVariety(auctionSchedule, variety);

        AuctionVarietyModifyServiceRequest request = AuctionVarietyModifyServiceRequest.builder()
            .plantGrade(PlantGrade.ADVANCED)
            .plantCount(15)
            .auctionStartPrice(Price.of(4000))
            .build();
        //when
        assertThatThrownBy(() -> auctionVarietyService.modifyAuctionVariety(auctionVariety.getId(), request))
            .isInstanceOf(AppException.class)
            .hasMessage("경매 품종을 수정할 수 없습니다.");

        //then
        Optional<AuctionVariety> findAuctionVariety = auctionVarietyRepository.findById(auctionVariety.getId());
        assertThat(findAuctionVariety).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("auctionPlant.plantGrade", PlantGrade.SUPER)
            .hasFieldOrPropertyWithValue("auctionPlant.plantCount", 10)
            .hasFieldOrPropertyWithValue("auctionPlant.auctionStartPrice.value", 4500);
    }

    @DisplayName("화훼등급, 화훼단수, 경매시작가를 입력 받아 경매 품종 정보를 수정한다.")
    @Test
    void modifyAuctionVariety() {
        //given
        Variety variety = createVariety();
        AuctionSchedule auctionSchedule = createAuctionSchedule(PlantCategory.CUT_FLOWERS, AuctionStatus.INIT);
        AuctionVariety auctionVariety = createAuctionVariety(auctionSchedule, variety);

        AuctionVarietyModifyServiceRequest request = AuctionVarietyModifyServiceRequest.builder()
            .plantGrade(PlantGrade.ADVANCED)
            .plantCount(15)
            .auctionStartPrice(Price.of(4000))
            .build();

        //when
        AuctionVarietyModifyResponse response = auctionVarietyService.modifyAuctionVariety(auctionVariety.getId(), request);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("id", auctionVariety.getId())
            .hasFieldOrPropertyWithValue("listingNumber", auctionVariety.getListingNumber())
            .hasFieldOrPropertyWithValue("plantGrade", PlantGrade.ADVANCED)
            .hasFieldOrPropertyWithValue("plantCount", 15)
            .hasFieldOrPropertyWithValue("auctionStartPrice", 4000);

        Optional<AuctionVariety> findAuctionVariety = auctionVarietyRepository.findById(auctionVariety.getId());
        assertThat(findAuctionVariety).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("auctionPlant.plantGrade", PlantGrade.ADVANCED)
            .hasFieldOrPropertyWithValue("auctionPlant.plantCount", 15)
            .hasFieldOrPropertyWithValue("auctionPlant.auctionStartPrice.value", 4000);
    }

    @DisplayName("경매 품종 삭제시 경매 상태가 INIT 상태일 때 경매 품종을 삭제할 수 있다.")
    @CsvSource({"READY", "PROGRESS", "COMPLETE"})
    @ParameterizedTest
    void removeAuctionVarietyAuctionStateNotInit(AuctionStatus status) {
        //given
        Variety variety = createVariety();
        AuctionSchedule auctionSchedule = createAuctionSchedule(PlantCategory.CUT_FLOWERS, status);
        AuctionVariety auctionVariety = createAuctionVariety(auctionSchedule, variety);

        //when
        assertThatThrownBy(() -> auctionVarietyService.removeAuctionVariety(auctionVariety.getId()))
            .isInstanceOf(AppException.class)
            .hasMessage("경매 품종을 삭제할 수 없습니다.");

        //then
        Optional<AuctionVariety> findAuctionVariety = auctionVarietyRepository.findById(auctionVariety.getId());
        assertThat(findAuctionVariety).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("isDeleted", false);
    }

    @DisplayName("경매 품종을 삭제한다.")
    @Test
    void removeAuctionVariety() {
        //given
        Variety variety = createVariety();
        AuctionSchedule auctionSchedule = createAuctionSchedule(PlantCategory.CUT_FLOWERS, AuctionStatus.INIT);
        AuctionVariety auctionVariety = createAuctionVariety(auctionSchedule, variety);

        //when
        AuctionVarietyRemoveResponse response = auctionVarietyService.removeAuctionVariety(auctionVariety.getId());

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("id", auctionVariety.getId())
            .hasFieldOrPropertyWithValue("listingNumber", auctionVariety.getListingNumber());

        Optional<AuctionVariety> findAuctionVariety = auctionVarietyRepository.findById(auctionVariety.getId());
        assertThat(findAuctionVariety).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("isDeleted", true);
    }

    private Variety createVariety() {
        Variety variety = Variety.builder()
            .isDeleted(false)
            .createdBy(1L)
            .lastModifiedBy(1L)
            .code("10000001")
            .info(
                VarietyInfo.builder()
                    .plantCategory(PlantCategory.CUT_FLOWERS)
                    .itemName("장미")
                    .varietyName("하트앤소울")
                    .build()
            )
            .build();
        return varietyRepository.save(variety);
    }

    private AuctionSchedule createAuctionSchedule(PlantCategory plantCategory, AuctionStatus auctionStatus) {
        AuctionSchedule auctionSchedule = AuctionSchedule.builder()
            .isDeleted(false)
            .createdBy(1L)
            .lastModifiedBy(1L)
            .auctionInfo(AuctionInfo.builder()
                .plantCategory(plantCategory)
                .jointMarket(JointMarket.YANGJAE)
                .auctionStartDateTime(LocalDateTime.of(2024, 7, 12, 5, 0))
                .build())
            .auctionStatus(auctionStatus)
            .auctionDescription("경매 설명")
            .build();
        return auctionScheduleRepository.save(auctionSchedule);
    }

    private AuctionVariety createAuctionVariety(AuctionSchedule auctionSchedule, Variety variety) {
        AuctionVariety auctionVariety = AuctionVariety.builder()
            .isDeleted(false)
            .createdBy(1L)
            .lastModifiedBy(1L)
            .auctionSchedule(auctionSchedule)
            .variety(variety)
            .listingNumber("00001")
            .auctionPlant(AuctionPlant.builder()
                .plantGrade(PlantGrade.SUPER)
                .plantCount(10)
                .auctionStartPrice(Price.of(4500))
                .build())
            .shipment(Shipment.builder()
                .region("광주")
                .shipper("김출하")
                .build())
            .build();
        return auctionVarietyRepository.save(auctionVariety);
    }
}