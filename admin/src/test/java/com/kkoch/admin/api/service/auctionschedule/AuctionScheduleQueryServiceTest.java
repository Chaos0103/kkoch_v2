package com.kkoch.admin.api.service.auctionschedule;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.api.PageResponse;
import com.kkoch.admin.domain.auctionschedule.AuctionRoomStatus;
import com.kkoch.admin.domain.auctionschedule.AuctionSchedule;
import com.kkoch.admin.domain.auctionschedule.repository.AuctionScheduleRepository;
import com.kkoch.admin.domain.auctionschedule.repository.response.AuctionScheduleResponse;
import com.kkoch.admin.domain.auctionschedule.repository.response.OpenedAuctionResponse;
import com.kkoch.admin.domain.variety.PlantCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class AuctionScheduleQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private AuctionScheduleQueryService auctionScheduleQueryService;

    @Autowired
    private AuctionScheduleRepository auctionScheduleRepository;

    @DisplayName("페이지 정보를 입력 받아 경매 일정 목록을 조회한다.")
    @Test
    void searchAuctionSchedules() {
        //given
        AuctionSchedule auctionSchedule1 = createAuctionSchedule(AuctionRoomStatus.CLOSE);
        AuctionSchedule auctionSchedule2 = createAuctionSchedule(AuctionRoomStatus.OPEN);
        AuctionSchedule auctionSchedule3 = createAuctionSchedule(AuctionRoomStatus.READY);
        AuctionSchedule auctionSchedule4 = createAuctionSchedule(AuctionRoomStatus.INIT);

        PageRequest pageRequest = PageRequest.of(1, 2);

        //when
        PageResponse<AuctionScheduleResponse> response = auctionScheduleQueryService.searchAuctionSchedules(pageRequest);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("currentPage", 2)
            .hasFieldOrPropertyWithValue("size", 2)
            .hasFieldOrPropertyWithValue("isFirst", false)
            .hasFieldOrPropertyWithValue("isLast", true);

        assertThat(response.getContent()).hasSize(2)
            .extracting("auctionScheduleId", "roomStatus")
            .containsExactly(
                tuple(auctionSchedule2.getId(), AuctionRoomStatus.OPEN),
                tuple(auctionSchedule1.getId(), AuctionRoomStatus.CLOSE)
            );
    }

    @DisplayName("경매 방 상태가 OPEN인 경매 일정을 조회한다.")
    @Test
    void searchOpenedAuction() {
        //given
        AuctionSchedule auctionSchedule = createAuctionSchedule(AuctionRoomStatus.OPEN);

        //when
        OpenedAuctionResponse response = auctionScheduleQueryService.searchOpenedAuction();

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("auctionScheduleId", auctionSchedule.getId())
            .hasFieldOrPropertyWithValue("plantCategory", PlantCategory.CUT_FLOWERS)
            .hasFieldOrPropertyWithValue("roomStatus", AuctionRoomStatus.OPEN)
            .hasFieldOrPropertyWithValue("auctionDateTime", LocalDateTime.of(2024, 7, 12, 5, 0));
    }

    @DisplayName("경매 방 상태가 OPEN인 경매 일정을 조회시 데이터가 없다면 null을 반환한다.")
    @Test
    void searchOpenedAuctionWithoutDate() {
        //given //when
        OpenedAuctionResponse response = auctionScheduleQueryService.searchOpenedAuction();

        //then
        assertThat(response).isNull();
    }

    public AuctionSchedule createAuctionSchedule(AuctionRoomStatus roomStatus) {
        AuctionSchedule auctionSchedule = AuctionSchedule.builder()
            .isDeleted(false)
            .createdBy(1)
            .lastModifiedBy(1)
            .plantCategory(PlantCategory.CUT_FLOWERS)
            .roomStatus(roomStatus)
            .auctionDateTime(LocalDateTime.of(2024, 7, 12, 5, 0))
            .build();
        return auctionScheduleRepository.save(auctionSchedule);
    }

}