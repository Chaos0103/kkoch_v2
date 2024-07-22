package com.kkoch.admin.domain.auctionvariety.repository;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.domain.Grade;
import com.kkoch.admin.domain.auctionschedule.AuctionRoomStatus;
import com.kkoch.admin.domain.auctionschedule.AuctionSchedule;
import com.kkoch.admin.domain.auctionschedule.repository.AuctionScheduleRepository;
import com.kkoch.admin.domain.auctionvariety.AuctionVariety;
import com.kkoch.admin.domain.auctionvariety.AuctionVarietyInfo;
import com.kkoch.admin.domain.auctionvariety.ShippingInfo;
import com.kkoch.admin.domain.auctionvariety.repository.response.AuctionVarietyResponse;
import com.kkoch.admin.domain.variety.PlantCategory;
import com.kkoch.admin.domain.variety.Variety;
import com.kkoch.admin.domain.variety.repository.VarietyRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

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
    void findByAuctionScheduleId() {
        //given
        Variety variety = createVariety();
        AuctionSchedule auctionSchedule = createAuctionSchedule();
        AuctionVariety auctionVariety1 = createAuctionVariety(variety, auctionSchedule, "00001", false);
        AuctionVariety auctionVariety2 = createAuctionVariety(variety, auctionSchedule, "00002", true);
        AuctionVariety auctionVariety3 = createAuctionVariety(variety, auctionSchedule, "00003", false);

        //when
        List<AuctionVarietyResponse> content = auctionVarietyQueryRepository.findByAuctionScheduleId(auctionSchedule.getId());

        //then
        assertThat(content).hasSize(2)
            .extracting("auctionVarietyId", "auctionNumber")
            .containsExactly(
                tuple(auctionVariety1.getId(), "00001"),
                tuple(auctionVariety3.getId(), "00003")
            );
    }

    private Variety createVariety() {
        Variety variety = Variety.builder()
            .isDeleted(false)
            .createdBy(1)
            .lastModifiedBy(1)
            .code("10000001")
            .plantCategory(PlantCategory.CUT_FLOWERS)
            .itemName("장미")
            .varietyName("하트앤소울")
            .build();
        return varietyRepository.save(variety);
    }

    private AuctionSchedule createAuctionSchedule() {
        AuctionSchedule auctionSchedule = AuctionSchedule.builder()
            .isDeleted(false)
            .createdBy(1)
            .lastModifiedBy(1)
            .plantCategory(PlantCategory.CUT_FLOWERS)
            .roomStatus(AuctionRoomStatus.OPEN)
            .auctionDateTime(LocalDateTime.of(2024, 7, 12, 5, 0))
            .build();
        return auctionScheduleRepository.save(auctionSchedule);
    }

    private AuctionVariety createAuctionVariety(Variety variety, AuctionSchedule auctionSchedule, String auctionNumber, boolean isDeleted) {
        AuctionVariety auctionVariety = AuctionVariety.builder()
            .isDeleted(isDeleted)
            .createdBy(1)
            .lastModifiedBy(1)
            .auctionNumber(auctionNumber)
            .auctionVarietyInfo(AuctionVarietyInfo.builder()
                .grade(Grade.SUPER)
                .plantCount(10)
                .startPrice(4500)
                .build())
            .shippingInfo(ShippingInfo.builder()
                .region("광주")
                .shipper("김판매")
                .build())
            .variety(variety)
            .auctionSchedule(auctionSchedule)
            .build();
        return auctionVarietyRepository.save(auctionVariety);
    }
}