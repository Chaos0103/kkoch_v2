package com.ssafy.auction_service.api.service.auctionschedule;

import com.ssafy.auction_service.IntegrationTestSupport;
import com.ssafy.auction_service.api.ListResponse;
import com.ssafy.auction_service.domain.auctionschedule.AuctionInfo;
import com.ssafy.auction_service.domain.auctionschedule.AuctionSchedule;
import com.ssafy.auction_service.domain.auctionschedule.AuctionStatus;
import com.ssafy.auction_service.domain.auctionschedule.JointMarket;
import com.ssafy.auction_service.domain.auctionschedule.repository.AuctionScheduleRepository;
import com.ssafy.auction_service.domain.auctionschedule.repository.dto.AuctionScheduleSearchCond;
import com.ssafy.auction_service.domain.auctionschedule.repository.response.AuctionScheduleDetailResponse;
import com.ssafy.auction_service.domain.auctionschedule.repository.response.AuctionScheduleResponse;
import com.ssafy.auction_service.domain.auctionvariety.*;
import com.ssafy.auction_service.domain.auctionvariety.repository.AuctionVarietyRepository;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import com.ssafy.auction_service.domain.variety.Variety;
import com.ssafy.auction_service.domain.variety.VarietyInfo;
import com.ssafy.auction_service.domain.variety.repository.VarietyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

class AuctionScheduleQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private AuctionScheduleQueryService auctionScheduleQueryService;

    @Autowired
    private AuctionScheduleRepository auctionScheduleRepository;

    @Autowired
    private VarietyRepository varietyRepository;

    @Autowired
    private AuctionVarietyRepository auctionVarietyRepository;

    @DisplayName("검색 조건으로 경매 일정 목록을 조회한다.")
    @Test
    void searchAuctionSchedulesByCond() {
        //given
        AuctionSchedule auctionSchedule1 = createAuctionSchedule(false, PlantCategory.CUT_FLOWERS, JointMarket.YANGJAE, AuctionStatus.INIT, LocalDateTime.of(2024, 8, 10, 5, 0));
        createAuctionSchedule(false, PlantCategory.ORCHID, JointMarket.YANGJAE, AuctionStatus.INIT, LocalDateTime.of(2024, 8, 11, 5, 0));
        createAuctionSchedule(false, PlantCategory.FOLIAGE, JointMarket.YANGJAE, AuctionStatus.INIT, LocalDateTime.of(2024, 8, 12, 5, 0));
        createAuctionSchedule(false, PlantCategory.VERNALIZATION, JointMarket.YANGJAE, AuctionStatus.INIT, LocalDateTime.of(2024, 8, 13, 5, 0));
        AuctionSchedule auctionSchedule2 = createAuctionSchedule(false, PlantCategory.CUT_FLOWERS, JointMarket.YANGJAE, AuctionStatus.READY, LocalDateTime.of(2024, 8, 14, 5, 0));
        AuctionSchedule auctionSchedule3 = createAuctionSchedule(false, PlantCategory.CUT_FLOWERS, JointMarket.YANGJAE, AuctionStatus.PROGRESS, LocalDateTime.of(2024, 8, 15, 5, 0));
        createAuctionSchedule(false, PlantCategory.CUT_FLOWERS, JointMarket.YANGJAE, AuctionStatus.COMPLETE, LocalDateTime.of(2024, 8, 16, 5, 0));
        createAuctionSchedule(false, PlantCategory.CUT_FLOWERS, JointMarket.GWANGJU, AuctionStatus.INIT, LocalDateTime.of(2024, 8, 17, 5, 0));
        createAuctionSchedule(true, PlantCategory.CUT_FLOWERS, JointMarket.YANGJAE, AuctionStatus.INIT, LocalDateTime.of(2024, 8, 18, 5, 0));

        AuctionScheduleSearchCond cond = AuctionScheduleSearchCond.builder()
            .plantCategory(PlantCategory.CUT_FLOWERS)
            .jointMarket(JointMarket.YANGJAE)
            .build();

        //when
        ListResponse<AuctionScheduleResponse> response = auctionScheduleQueryService.searchAuctionSchedulesByCond(cond);

        //then
        assertThat(response.getContent()).hasSize(3)
            .extracting("id", "plantCategory", "jointMarket", "auctionStartDateTime", "auctionStatus")
            .containsExactly(
                tuple(auctionSchedule3.getId(), PlantCategory.CUT_FLOWERS, JointMarket.YANGJAE, LocalDateTime.of(2024, 8, 15, 5, 0), AuctionStatus.PROGRESS),
                tuple(auctionSchedule2.getId(), PlantCategory.CUT_FLOWERS, JointMarket.YANGJAE, LocalDateTime.of(2024, 8, 14, 5, 0), AuctionStatus.READY),
                tuple(auctionSchedule1.getId(), PlantCategory.CUT_FLOWERS, JointMarket.YANGJAE, LocalDateTime.of(2024, 8, 10, 5, 0), AuctionStatus.INIT)
            );
    }

    @DisplayName("경매 일정 ID로 경매 일정 조회시 조회된 데이터가 없으면 예외가 발생한다.")
    @Test
    void searchAuctionScheduleNoSuchElement() {
        //given //when //then
        assertThatThrownBy(() -> auctionScheduleQueryService.searchAuctionSchedule(1))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessage("등록되지 않은 경매 일정입니다.");
    }

    @DisplayName("경매 일정 ID로 경매 일정을 조회한다.")
    @Test
    void searchAuctionSchedule() {
        //given
        Variety variety = createVariety();
        AuctionSchedule auctionSchedule = createAuctionSchedule(false, PlantCategory.CUT_FLOWERS, JointMarket.YANGJAE, AuctionStatus.INIT, LocalDateTime.of(2024, 8, 10, 5, 0));
        createAuctionVariety(false, auctionSchedule, variety, "00001");
        createAuctionVariety(false, auctionSchedule, variety, "00002");
        createAuctionVariety(true, auctionSchedule, variety, "00003");

        //when
        AuctionScheduleDetailResponse response = auctionScheduleQueryService.searchAuctionSchedule(auctionSchedule.getId());

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("id", auctionSchedule.getId())
            .hasFieldOrPropertyWithValue("plantCategory", auctionSchedule.getAuctionInfo().getPlantCategory())
            .hasFieldOrPropertyWithValue("jointMarket", auctionSchedule.getAuctionInfo().getJointMarket())
            .hasFieldOrPropertyWithValue("auctionStartDateTime", auctionSchedule.getAuctionInfo().getAuctionStartDateTime())
            .hasFieldOrPropertyWithValue("auctionStatus", auctionSchedule.getAuctionStatus())
            .hasFieldOrPropertyWithValue("auctionDescription", auctionSchedule.getAuctionDescription())
            .hasFieldOrPropertyWithValue("auctionVarietyCount", 2);
    }

    private AuctionSchedule createAuctionSchedule(boolean isDeleted, PlantCategory plantCategory, JointMarket jointMarket, AuctionStatus auctionStatus, LocalDateTime auctionStartDateTime) {
        AuctionSchedule auctionSchedule = AuctionSchedule.builder()
            .isDeleted(isDeleted)
            .createdBy(1L)
            .lastModifiedBy(1L)
            .auctionInfo(AuctionInfo.builder()
                .plantCategory(plantCategory)
                .jointMarket(jointMarket)
                .auctionStartDateTime(auctionStartDateTime)
                .build())
            .auctionStatus(auctionStatus)
            .auctionDescription("경매 설명입니다.")
            .build();
        return auctionScheduleRepository.save(auctionSchedule);
    }

    private Variety createVariety() {
        Variety variety = Variety.builder()
            .isDeleted(false)
            .createdBy(1L)
            .lastModifiedBy(1L)
            .code("10000001")
            .info(VarietyInfo.builder()
                .plantCategory(PlantCategory.CUT_FLOWERS)
                .itemName("장미")
                .varietyName("하트앤소울")
                .build())
            .build();
        return varietyRepository.save(variety);
    }

    private AuctionVariety createAuctionVariety(boolean isDeleted, AuctionSchedule auctionSchedule, Variety variety, String listingNumber) {
        AuctionVariety auctionVariety = AuctionVariety.builder()
            .isDeleted(isDeleted)
            .createdBy(1L)
            .lastModifiedBy(1L)
            .auctionSchedule(auctionSchedule)
            .variety(variety)
            .listingNumber(listingNumber)
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