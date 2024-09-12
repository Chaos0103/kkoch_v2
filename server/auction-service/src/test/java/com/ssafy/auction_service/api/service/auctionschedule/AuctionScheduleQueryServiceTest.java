package com.ssafy.auction_service.api.service.auctionschedule;

import com.ssafy.auction_service.IntegrationTestSupport;
import com.ssafy.auction_service.api.ListResponse;
import com.ssafy.auction_service.domain.auctionschedule.AuctionInfo;
import com.ssafy.auction_service.domain.auctionschedule.AuctionSchedule;
import com.ssafy.auction_service.domain.auctionschedule.AuctionStatus;
import com.ssafy.auction_service.domain.auctionschedule.JointMarket;
import com.ssafy.auction_service.domain.auctionschedule.repository.AuctionScheduleRepository;
import com.ssafy.auction_service.domain.auctionschedule.repository.dto.AuctionScheduleSearchCond;
import com.ssafy.auction_service.domain.auctionschedule.repository.response.AuctionScheduleResponse;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class AuctionScheduleQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private AuctionScheduleQueryService auctionScheduleQueryService;

    @Autowired
    private AuctionScheduleRepository auctionScheduleRepository;

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