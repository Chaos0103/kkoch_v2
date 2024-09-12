package com.ssafy.auction_service.domain.auctionschedule.repository;

import com.ssafy.auction_service.IntegrationTestSupport;
import com.ssafy.auction_service.domain.auctionschedule.AuctionInfo;
import com.ssafy.auction_service.domain.auctionschedule.AuctionSchedule;
import com.ssafy.auction_service.domain.auctionschedule.AuctionStatus;
import com.ssafy.auction_service.domain.auctionschedule.JointMarket;
import com.ssafy.auction_service.domain.auctionschedule.repository.dto.AuctionScheduleSearchCond;
import com.ssafy.auction_service.domain.auctionschedule.repository.response.AuctionScheduleResponse;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class AuctionScheduleQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private AuctionScheduleQueryRepository auctionScheduleQueryRepository;

    @Autowired
    private AuctionScheduleRepository auctionScheduleRepository;

    @DisplayName("경매 일정 목록 조회시 화훼부류가 없으면 검색 조건에서 제외한다.")
    @Test
    void findByCondWithoutPlantCategory() {
        //given
        AuctionSchedule auctionSchedule1 = createAuctionSchedule(false, PlantCategory.CUT_FLOWERS, JointMarket.YANGJAE, AuctionStatus.INIT, LocalDateTime.of(2024, 8, 10, 5, 0));
        AuctionSchedule auctionSchedule2 = createAuctionSchedule(false, PlantCategory.ORCHID, JointMarket.YANGJAE, AuctionStatus.INIT, LocalDateTime.of(2024, 8, 11, 5, 0));
        AuctionSchedule auctionSchedule3 = createAuctionSchedule(false, PlantCategory.FOLIAGE, JointMarket.YANGJAE, AuctionStatus.INIT, LocalDateTime.of(2024, 8, 12, 5, 0));
        AuctionSchedule auctionSchedule4 = createAuctionSchedule(false, PlantCategory.VERNALIZATION, JointMarket.YANGJAE, AuctionStatus.INIT, LocalDateTime.of(2024, 8, 13, 5, 0));
        AuctionSchedule auctionSchedule5 = createAuctionSchedule(false, PlantCategory.CUT_FLOWERS, JointMarket.YANGJAE, AuctionStatus.READY, LocalDateTime.of(2024, 8, 14, 5, 0));
        AuctionSchedule auctionSchedule6 = createAuctionSchedule(false, PlantCategory.CUT_FLOWERS, JointMarket.YANGJAE, AuctionStatus.PROGRESS, LocalDateTime.of(2024, 8, 15, 5, 0));
        createAuctionSchedule(false, PlantCategory.CUT_FLOWERS, JointMarket.YANGJAE, AuctionStatus.COMPLETE, LocalDateTime.of(2024, 8, 16, 5, 0));
        createAuctionSchedule(false, PlantCategory.CUT_FLOWERS, JointMarket.GWANGJU, AuctionStatus.INIT, LocalDateTime.of(2024, 8, 17, 5, 0));
        createAuctionSchedule(true, PlantCategory.CUT_FLOWERS, JointMarket.YANGJAE, AuctionStatus.INIT, LocalDateTime.of(2024, 8, 18, 5, 0));

        AuctionScheduleSearchCond cond = AuctionScheduleSearchCond.builder()
            .plantCategory(null)
            .jointMarket(JointMarket.YANGJAE)
            .build();

        //when
        List<AuctionScheduleResponse> content = auctionScheduleQueryRepository.findByCond(cond);

        //then
        assertThat(content).hasSize(6)
            .extracting("id", "plantCategory", "jointMarket", "auctionStartDateTime", "auctionStatus")
            .containsExactly(
                tuple(auctionSchedule6.getId(), PlantCategory.CUT_FLOWERS, JointMarket.YANGJAE, LocalDateTime.of(2024, 8, 15, 5, 0), AuctionStatus.PROGRESS),
                tuple(auctionSchedule5.getId(), PlantCategory.CUT_FLOWERS, JointMarket.YANGJAE, LocalDateTime.of(2024, 8, 14, 5, 0), AuctionStatus.READY),
                tuple(auctionSchedule4.getId(), PlantCategory.VERNALIZATION, JointMarket.YANGJAE, LocalDateTime.of(2024, 8, 13, 5, 0), AuctionStatus.INIT),
                tuple(auctionSchedule3.getId(), PlantCategory.FOLIAGE, JointMarket.YANGJAE, LocalDateTime.of(2024, 8, 12, 5, 0), AuctionStatus.INIT),
                tuple(auctionSchedule2.getId(), PlantCategory.ORCHID, JointMarket.YANGJAE, LocalDateTime.of(2024, 8, 11, 5, 0), AuctionStatus.INIT),
                tuple(auctionSchedule1.getId(), PlantCategory.CUT_FLOWERS, JointMarket.YANGJAE, LocalDateTime.of(2024, 8, 10, 5, 0), AuctionStatus.INIT)
            );
    }

    @DisplayName("경매 일정 목록 조회시 공판장이 없으면 검색 조건에서 제외한다.")
    @Test
    void findByCondWithoutJointMarket() {
        //given
        AuctionSchedule auctionSchedule1 = createAuctionSchedule(false, PlantCategory.CUT_FLOWERS, JointMarket.YANGJAE, AuctionStatus.INIT, LocalDateTime.of(2024, 8, 10, 5, 0));
        createAuctionSchedule(false, PlantCategory.ORCHID, JointMarket.YANGJAE, AuctionStatus.INIT, LocalDateTime.of(2024, 8, 11, 5, 0));
        createAuctionSchedule(false, PlantCategory.FOLIAGE, JointMarket.YANGJAE, AuctionStatus.INIT, LocalDateTime.of(2024, 8, 12, 5, 0));
        createAuctionSchedule(false, PlantCategory.VERNALIZATION, JointMarket.YANGJAE, AuctionStatus.INIT, LocalDateTime.of(2024, 8, 13, 5, 0));
        AuctionSchedule auctionSchedule2 = createAuctionSchedule(false, PlantCategory.CUT_FLOWERS, JointMarket.YANGJAE, AuctionStatus.READY, LocalDateTime.of(2024, 8, 14, 5, 0));
        AuctionSchedule auctionSchedule3 = createAuctionSchedule(false, PlantCategory.CUT_FLOWERS, JointMarket.YANGJAE, AuctionStatus.PROGRESS, LocalDateTime.of(2024, 8, 15, 5, 0));
        createAuctionSchedule(false, PlantCategory.CUT_FLOWERS, JointMarket.YANGJAE, AuctionStatus.COMPLETE, LocalDateTime.of(2024, 8, 16, 5, 0));
        AuctionSchedule auctionSchedule4 = createAuctionSchedule(false, PlantCategory.CUT_FLOWERS, JointMarket.GWANGJU, AuctionStatus.INIT, LocalDateTime.of(2024, 8, 17, 5, 0));
        createAuctionSchedule(true, PlantCategory.CUT_FLOWERS, JointMarket.YANGJAE, AuctionStatus.INIT, LocalDateTime.of(2024, 8, 18, 5, 0));

        AuctionScheduleSearchCond cond = AuctionScheduleSearchCond.builder()
            .plantCategory(PlantCategory.CUT_FLOWERS)
            .jointMarket(null)
            .build();

        //when
        List<AuctionScheduleResponse> content = auctionScheduleQueryRepository.findByCond(cond);

        //then
        assertThat(content).hasSize(4)
            .extracting("id", "plantCategory", "jointMarket", "auctionStartDateTime", "auctionStatus")
            .containsExactly(
                tuple(auctionSchedule4.getId(), PlantCategory.CUT_FLOWERS, JointMarket.GWANGJU, LocalDateTime.of(2024, 8, 17, 5, 0), AuctionStatus.INIT),
                tuple(auctionSchedule3.getId(), PlantCategory.CUT_FLOWERS, JointMarket.YANGJAE, LocalDateTime.of(2024, 8, 15, 5, 0), AuctionStatus.PROGRESS),
                tuple(auctionSchedule2.getId(), PlantCategory.CUT_FLOWERS, JointMarket.YANGJAE, LocalDateTime.of(2024, 8, 14, 5, 0), AuctionStatus.READY),
                tuple(auctionSchedule1.getId(), PlantCategory.CUT_FLOWERS, JointMarket.YANGJAE, LocalDateTime.of(2024, 8, 10, 5, 0), AuctionStatus.INIT)
            );
    }

    @DisplayName("검색 조건으로 경매 일정 목록을 조회한다.")
    @Test
    void findByCond() {
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
        List<AuctionScheduleResponse> content = auctionScheduleQueryRepository.findByCond(cond);

        //then
        assertThat(content).hasSize(3)
            .extracting("id", "plantCategory", "jointMarket", "auctionStartDateTime", "auctionStatus")
            .containsExactly(
                tuple(auctionSchedule3.getId(), PlantCategory.CUT_FLOWERS, JointMarket.YANGJAE, LocalDateTime.of(2024, 8, 15, 5, 0), AuctionStatus.PROGRESS),
                tuple(auctionSchedule2.getId(), PlantCategory.CUT_FLOWERS, JointMarket.YANGJAE, LocalDateTime.of(2024, 8, 14, 5, 0), AuctionStatus.READY),
                tuple(auctionSchedule1.getId(), PlantCategory.CUT_FLOWERS, JointMarket.YANGJAE, LocalDateTime.of(2024, 8, 10, 5, 0), AuctionStatus.INIT)
            );
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
}