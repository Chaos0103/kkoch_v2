package com.ssafy.auction_service.domain.auctionschedule.repository;

import com.ssafy.auction_service.IntegrationTestSupport;
import com.ssafy.auction_service.domain.auctionschedule.AuctionInfo;
import com.ssafy.auction_service.domain.auctionschedule.AuctionSchedule;
import com.ssafy.auction_service.domain.auctionschedule.AuctionStatus;
import com.ssafy.auction_service.domain.auctionschedule.JointMarket;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AuctionScheduleRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private AuctionScheduleRepository auctionScheduleRepository;

    @DisplayName("화훼부류, 공판장, 경매시작시간으로 경매 일정 ID를 조회한다.")
    @Test
    void findIdByAuction() {
        //given
        createAuctionSchedule();

        //when
        Optional<Integer> auctionScheduleId = auctionScheduleRepository.findIdByAuction(PlantCategory.CUT_FLOWERS, JointMarket.YANGJAE, LocalDateTime.of(2024, 7, 12, 5, 0));

        //then
        assertThat(auctionScheduleId).isPresent();
    }

    private AuctionSchedule createAuctionSchedule() {
        AuctionSchedule auctionSchedule = AuctionSchedule.builder()
            .isDeleted(false)
            .createdBy(1L)
            .lastModifiedBy(1L)
            .auctionInfo(createAuctionInfo())
            .auctionStartDateTime(LocalDateTime.of(2024, 7, 12, 5, 0))
            .auctionStatus(AuctionStatus.INIT)
            .build();
        return auctionScheduleRepository.save(auctionSchedule);
    }

    private AuctionInfo createAuctionInfo() {
        return AuctionInfo.builder()
            .plantCategory(PlantCategory.CUT_FLOWERS)
            .jointMarket(JointMarket.YANGJAE)
            .auctionDescription("auctionDescription")
            .build();
    }
}