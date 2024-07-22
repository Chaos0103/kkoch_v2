package com.kkoch.admin.domain.auctionschedule.repository;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.domain.auctionschedule.AuctionRoomStatus;
import com.kkoch.admin.domain.auctionschedule.AuctionSchedule;
import com.kkoch.admin.domain.auctionschedule.repository.response.AuctionScheduleResponse;
import com.kkoch.admin.domain.variety.PlantCategory;
import com.kkoch.admin.domain.auctionschedule.repository.response.OpenedAuctionResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class AuctionScheduleQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private AuctionScheduleQueryRepository auctionScheduleQueryRepository;

    @Autowired
    private AuctionScheduleRepository auctionScheduleRepository;

    @DisplayName("페이지 조건을 입력 받아 경매 일정 ID 목록을 조회한다.")
    @Test
    void findAllIdByCond() {
        //given
        AuctionSchedule auctionSchedule1 = createAuctionSchedule(true, AuctionRoomStatus.INIT);
        AuctionSchedule auctionSchedule2 = createAuctionSchedule(false, AuctionRoomStatus.READY);
        AuctionSchedule auctionSchedule3 = createAuctionSchedule(false, AuctionRoomStatus.OPEN);
        AuctionSchedule auctionSchedule4 = createAuctionSchedule(false, AuctionRoomStatus.CLOSE);

        PageRequest pageRequest = PageRequest.of(0, 2);

        //when
        List<Integer> auctionScheduleIds = auctionScheduleQueryRepository.findAllIdByCond(pageRequest);

        //then
        assertThat(auctionScheduleIds).hasSize(2)
            .containsExactly(auctionSchedule4.getId(), auctionSchedule3.getId());
    }

    @DisplayName("경매 일정 ID 목록을 입력 받아 경매 일정 목록을 조회한다.")
    @Test
    void findAllByIdIn() {
        //given
        AuctionSchedule auctionSchedule1 = createAuctionSchedule(false, AuctionRoomStatus.INIT);
        AuctionSchedule auctionSchedule2 = createAuctionSchedule(false, AuctionRoomStatus.READY);
        AuctionSchedule auctionSchedule3 = createAuctionSchedule(false, AuctionRoomStatus.OPEN);
        AuctionSchedule auctionSchedule4 = createAuctionSchedule(false, AuctionRoomStatus.CLOSE);

        List<Integer> auctionScheduleIds = List.of(auctionSchedule4.getId(), auctionSchedule3.getId(), auctionSchedule2.getId(), auctionSchedule1.getId());

        //when
        List<AuctionScheduleResponse> content = auctionScheduleQueryRepository.findAllByIdIn(auctionScheduleIds);

        //then
        assertThat(content).hasSize(4)
            .extracting("auctionScheduleId", "roomStatus")
            .containsExactly(
                tuple(auctionSchedule4.getId(), AuctionRoomStatus.CLOSE),
                tuple(auctionSchedule3.getId(), AuctionRoomStatus.OPEN),
                tuple(auctionSchedule2.getId(), AuctionRoomStatus.READY),
                tuple(auctionSchedule1.getId(), AuctionRoomStatus.INIT)
            );
    }

    @DisplayName("방 상태가 OPEN인 경매 일정을 조회한다.")
    @Test
    void findByRoomStatusIsOpen() {
        //given
        AuctionSchedule auctionSchedule1 = createAuctionSchedule(false, AuctionRoomStatus.INIT);
        AuctionSchedule auctionSchedule2 = createAuctionSchedule(false, AuctionRoomStatus.READY);
        AuctionSchedule auctionSchedule3 = createAuctionSchedule(false, AuctionRoomStatus.OPEN);
        AuctionSchedule auctionSchedule4 = createAuctionSchedule(false, AuctionRoomStatus.CLOSE);

        //when
        Optional<OpenedAuctionResponse> content = auctionScheduleQueryRepository.findByRoomStatusIsOpen();

        //then
        assertThat(content).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("auctionScheduleId", auctionSchedule3.getId())
            .hasFieldOrPropertyWithValue("roomStatus", AuctionRoomStatus.OPEN);
    }

    @DisplayName("삭제되지 않은 경매 일정 중 방 상태가 OPEN인 경매 일정을 조회한다.")
    @Test
    void findByRoomStatusIsOpenIsDeleted() {
        //given
        AuctionSchedule auctionSchedule = createAuctionSchedule(true, AuctionRoomStatus.OPEN);

        //when
        Optional<OpenedAuctionResponse> content = auctionScheduleQueryRepository.findByRoomStatusIsOpen();

        //then
        assertThat(content).isEmpty();
    }

    private AuctionSchedule createAuctionSchedule(boolean isDeleted, AuctionRoomStatus status) {
        AuctionSchedule auctionSchedule = AuctionSchedule.builder()
            .isDeleted(isDeleted)
            .createdBy(1)
            .lastModifiedBy(1)
            .plantCategory(PlantCategory.CUT_FLOWERS)
            .roomStatus(status)
            .auctionDateTime(LocalDateTime.of(2024, 7, 12, 5, 0))
            .build();
        return auctionScheduleRepository.save(auctionSchedule);
    }

}