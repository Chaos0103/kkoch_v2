package com.ssafy.auction_service.api.service.auctionvariety;

import com.ssafy.auction_service.IntegrationTestSupport;
import com.ssafy.auction_service.api.PageResponse;
import com.ssafy.auction_service.domain.auctionschedule.AuctionInfo;
import com.ssafy.auction_service.domain.auctionschedule.AuctionSchedule;
import com.ssafy.auction_service.domain.auctionschedule.AuctionStatus;
import com.ssafy.auction_service.domain.auctionschedule.JointMarket;
import com.ssafy.auction_service.domain.auctionschedule.repository.AuctionScheduleRepository;
import com.ssafy.auction_service.domain.auctionvariety.*;
import com.ssafy.auction_service.domain.auctionvariety.repository.AuctionVarietyRepository;
import com.ssafy.auction_service.domain.auctionvariety.repository.response.AuctionVarietyResponse;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import com.ssafy.auction_service.domain.variety.Variety;
import com.ssafy.auction_service.domain.variety.VarietyInfo;
import com.ssafy.auction_service.domain.variety.repository.VarietyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class AuctionVarietyQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private AuctionVarietyQueryService auctionVarietyQueryService;

    @Autowired
    private AuctionVarietyRepository auctionVarietyRepository;

    @Autowired
    private VarietyRepository varietyRepository;

    @Autowired
    private AuctionScheduleRepository auctionScheduleRepository;

    @DisplayName("경매 일정에 등록된 경매 품종 목록을 조회한다.")
    @Test
    void searchAuctionVarieties() {
        //given
        Variety variety = createVariety();
        AuctionSchedule auctionSchedule = createAuctionSchedule();
        AuctionVariety auctionVariety1 = createAuctionVariety(auctionSchedule, variety, "00001");
        AuctionVariety auctionVariety2 = createAuctionVariety(auctionSchedule, variety, "00002");
        AuctionVariety auctionVariety3 = createAuctionVariety(auctionSchedule, variety, "00003");

        //when
        PageResponse<AuctionVarietyResponse> response = auctionVarietyQueryService.searchAuctionVarieties(auctionSchedule.getId(), 1);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("currentPage", 1)
            .hasFieldOrPropertyWithValue("size", 10)
            .hasFieldOrPropertyWithValue("isFirst", true)
            .hasFieldOrPropertyWithValue("isLast", true);
        assertThat(response.getContent()).hasSize(3)
            .extracting("id", "listingNumber")
            .containsExactly(
                tuple(auctionVariety1.getId(), auctionVariety1.getListingNumber()),
                tuple(auctionVariety2.getId(), auctionVariety2.getListingNumber()),
                tuple(auctionVariety3.getId(), auctionVariety3.getListingNumber())
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

    private AuctionSchedule createAuctionSchedule() {
        AuctionSchedule auctionSchedule = AuctionSchedule.builder()
            .isDeleted(false)
            .createdBy(1L)
            .lastModifiedBy(1L)
            .auctionInfo(AuctionInfo.builder()
                .plantCategory(PlantCategory.CUT_FLOWERS)
                .jointMarket(JointMarket.YANGJAE)
                .auctionStartDateTime(LocalDateTime.of(2024, 7, 12, 5, 0))
                .build())
            .auctionStatus(AuctionStatus.READY)
            .auctionDescription("경매 설명")
            .build();
        return auctionScheduleRepository.save(auctionSchedule);
    }

    private AuctionVariety createAuctionVariety(AuctionSchedule auctionSchedule, Variety variety, String listingNumber) {
        AuctionVariety auctionVariety = AuctionVariety.builder()
            .isDeleted(false)
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