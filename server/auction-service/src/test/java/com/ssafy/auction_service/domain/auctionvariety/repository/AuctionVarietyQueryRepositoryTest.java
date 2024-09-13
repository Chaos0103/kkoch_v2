package com.ssafy.auction_service.domain.auctionvariety.repository;

import com.ssafy.auction_service.IntegrationTestSupport;
import com.ssafy.auction_service.domain.auctionschedule.AuctionInfo;
import com.ssafy.auction_service.domain.auctionschedule.AuctionSchedule;
import com.ssafy.auction_service.domain.auctionschedule.AuctionStatus;
import com.ssafy.auction_service.domain.auctionschedule.JointMarket;
import com.ssafy.auction_service.domain.auctionschedule.repository.AuctionScheduleRepository;
import com.ssafy.auction_service.domain.auctionvariety.*;
import com.ssafy.auction_service.domain.auctionvariety.repository.response.AuctionVarietyResponse;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import com.ssafy.auction_service.domain.variety.Variety;
import com.ssafy.auction_service.domain.variety.VarietyInfo;
import com.ssafy.auction_service.domain.variety.repository.VarietyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class AuctionVarietyQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private AuctionVarietyQueryRepository auctionVarietyQueryRepository;

    @Autowired
    private AuctionVarietyRepository auctionVarietyRepository;

    @Autowired
    private VarietyRepository varietyRepository;

    @Autowired
    private AuctionScheduleRepository auctionScheduleRepository;

    @DisplayName("경매 일정 ID로 경매 품종 목록을 조회한다.")
    @Test
    void findAllByAuctionScheduleId() {
        //given
        Variety variety = createVariety();
        AuctionSchedule otherAuctionSchedule = createAuctionSchedule(LocalDateTime.of(2024, 7, 15, 5, 0));
        createAuctionVariety(false, otherAuctionSchedule, variety, "00001");

        AuctionSchedule targetAuctionSchedule = createAuctionSchedule(LocalDateTime.of(2024, 7, 12, 5, 0));
        AuctionVariety auctionVariety1 = createAuctionVariety(false, targetAuctionSchedule, variety, "00001");
        createAuctionVariety(true, targetAuctionSchedule, variety, "00002");
        AuctionVariety auctionVariety2 = createAuctionVariety(false, targetAuctionSchedule, variety, "00003");

        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        List<AuctionVarietyResponse> content = auctionVarietyQueryRepository.findAllByAuctionScheduleId(targetAuctionSchedule.getId(), pageRequest);

        //then
        assertThat(content).hasSize(2)
            .extracting("id", "listingNumber")
            .containsExactly(
                tuple(auctionVariety1.getId(), "00001"),
                tuple(auctionVariety2.getId(), "00003")
            );
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

    private AuctionSchedule createAuctionSchedule(LocalDateTime auctionStartDateTime) {
        AuctionSchedule auctionSchedule = AuctionSchedule.builder()
            .isDeleted(false)
            .createdBy(1L)
            .lastModifiedBy(1L)
            .auctionInfo(AuctionInfo.builder()
                .plantCategory(PlantCategory.CUT_FLOWERS)
                .jointMarket(JointMarket.YANGJAE)
                .auctionStartDateTime(auctionStartDateTime)
                .build())
            .auctionStatus(AuctionStatus.READY)
            .auctionDescription("경매 설명")
            .build();
        return auctionScheduleRepository.save(auctionSchedule);
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