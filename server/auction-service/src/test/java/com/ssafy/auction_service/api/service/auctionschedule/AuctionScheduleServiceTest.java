package com.ssafy.auction_service.api.service.auctionschedule;

import com.ssafy.auction_service.IntegrationTestSupport;
import com.ssafy.auction_service.api.ApiResponse;
import com.ssafy.auction_service.api.client.MemberServiceClient;
import com.ssafy.auction_service.api.client.response.MemberIdResponse;
import com.ssafy.auction_service.api.service.auctionschedule.request.AuctionScheduleCreateServiceRequest;
import com.ssafy.auction_service.api.service.auctionschedule.response.AuctionScheduleCreateResponse;
import com.ssafy.auction_service.api.service.auctionschedule.response.AuctionStatusModifyResponse;
import com.ssafy.auction_service.common.exception.AppException;
import com.ssafy.auction_service.domain.auctionschedule.AuctionInfo;
import com.ssafy.auction_service.domain.auctionschedule.AuctionSchedule;
import com.ssafy.auction_service.domain.auctionschedule.AuctionStatus;
import com.ssafy.auction_service.domain.auctionschedule.JointMarket;
import com.ssafy.auction_service.domain.auctionschedule.repository.AuctionScheduleRepository;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

class AuctionScheduleServiceTest extends IntegrationTestSupport {

    @Autowired
    private AuctionScheduleService auctionScheduleService;

    @Autowired
    private AuctionScheduleRepository auctionScheduleRepository;

    @MockBean
    private MemberServiceClient memberServiceClient;

    @DisplayName("같은 경매 일정이 존재하면 예외가 발생한다.")
    @Test
    void duplicatedAuctionSchedule() {
        //given
        LocalDateTime current = LocalDateTime.of(2024, 8, 2, 0, 0, 0);

        createAuctionSchedule(AuctionStatus.INIT);

        AuctionScheduleCreateServiceRequest request = AuctionScheduleCreateServiceRequest.builder()
            .plantCategory("CUT_FLOWERS")
            .jointMarket("YANGJAE")
            .auctionDescription("경매 예정입니다.")
            .auctionStartDateTime("2024-08-12T05:00")
            .build();

        mockingMemberId();

        //when
        assertThatThrownBy(() -> auctionScheduleService.createAuctionSchedule(request, current))
            .isInstanceOf(AppException.class)
            .hasMessage("이미 등록된 경매 일정이 있습니다.");

        //then
        List<AuctionSchedule> auctionSchedules = auctionScheduleRepository.findAll();
        assertThat(auctionSchedules).hasSize(1);
    }

    @DisplayName("경매 일정 정보를 입력받아 경매 일정을 등록한다.")
    @Test
    void createAuctionSchedule() {
        //given
        LocalDateTime current = LocalDateTime.of(2024, 8, 2, 0, 0, 0);

        AuctionScheduleCreateServiceRequest request = AuctionScheduleCreateServiceRequest.builder()
            .plantCategory("CUT_FLOWERS")
            .jointMarket("YANGJAE")
            .auctionDescription("경매 예정입니다.")
            .auctionStartDateTime("2024-08-12T05:00")
            .build();

        mockingMemberId();

        //when
        AuctionScheduleCreateResponse response = auctionScheduleService.createAuctionSchedule(request, current);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("plantCategory", "절화")
            .hasFieldOrPropertyWithValue("jointMarket", "양재")
            .hasFieldOrPropertyWithValue("auctionStartDateTime", LocalDateTime.of(2024, 8, 12, 5, 0))
            .hasFieldOrPropertyWithValue("auctionStatus", AuctionStatus.INIT);

        List<AuctionSchedule> auctionSchedules = auctionScheduleRepository.findAll();
        assertThat(auctionSchedules).hasSize(1);
    }

    @DisplayName("경매 상태를 READY로 변경시 경매 상태가 PROGRESS 라면 예외가 발생한다.")
    @Test
    void progressStatusToReady() {
        //given
        LocalDateTime current = LocalDateTime.of(2024, 8, 6, 7, 0);
        AuctionSchedule auctionSchedule = createAuctionSchedule(AuctionStatus.PROGRESS);

        //when
        assertThatThrownBy(() -> auctionScheduleService.modifyAuctionStatusToReady(auctionSchedule.getId(), current))
            .isInstanceOf(AppException.class)
            .hasMessage("진행중인 경매입니다.");

        //then
        Optional<AuctionSchedule> findAuctionSchedule = auctionScheduleRepository.findById(auctionSchedule.getId());
        assertThat(findAuctionSchedule).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("auctionStatus", AuctionStatus.PROGRESS);
    }

    @DisplayName("경매 상태를 READY로 변경시 경매 상태가 COMPLETE 라면 예외가 발생한다.")
    @Test
    void completeStatusToReady() {
        //given
        LocalDateTime current = LocalDateTime.of(2024, 8, 6, 7, 0);
        AuctionSchedule auctionSchedule = createAuctionSchedule(AuctionStatus.COMPLETE);

        //when
        assertThatThrownBy(() -> auctionScheduleService.modifyAuctionStatusToReady(auctionSchedule.getId(), current))
            .isInstanceOf(AppException.class)
            .hasMessage("완료된 경매입니다.");

        //then
        Optional<AuctionSchedule> findAuctionSchedule = auctionScheduleRepository.findById(auctionSchedule.getId());
        assertThat(findAuctionSchedule).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("auctionStatus", AuctionStatus.COMPLETE);
    }

    @DisplayName("경매 일정 상태를 READY로 변경한다.")
    @Test
    void modifyAuctionStatusToReady() {
        //given
        LocalDateTime current = LocalDateTime.of(2024, 8, 6, 7, 0);
        AuctionSchedule auctionSchedule = createAuctionSchedule(AuctionStatus.INIT);

        //when
        AuctionStatusModifyResponse response = auctionScheduleService.modifyAuctionStatusToReady(auctionSchedule.getId(), current);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("id", auctionSchedule.getId())
            .hasFieldOrPropertyWithValue("auctionStatus", AuctionStatus.READY)
            .hasFieldOrPropertyWithValue("modifyDateTime", current);

        Optional<AuctionSchedule> findAuctionSchedule = auctionScheduleRepository.findById(auctionSchedule.getId());
        assertThat(findAuctionSchedule).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("auctionStatus", AuctionStatus.READY);
    }

    @DisplayName("경매 상태를 PROGRESS로 변경시 경매 상태가 INIT 라면 예외가 발생한다.")
    @Test
    void initStatusToProgress() {
        //given
        LocalDateTime current = LocalDateTime.of(2024, 8, 6, 7, 0);
        AuctionSchedule auctionSchedule = createAuctionSchedule(AuctionStatus.INIT);

        //when
        assertThatThrownBy(() -> auctionScheduleService.modifyAuctionStatusToProgress(auctionSchedule.getId(), current))
            .isInstanceOf(AppException.class)
            .hasMessage("준비된 경매가 아닙니다.");

        //then
        Optional<AuctionSchedule> findAuctionSchedule = auctionScheduleRepository.findById(auctionSchedule.getId());
        assertThat(findAuctionSchedule).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("auctionStatus", AuctionStatus.INIT);
    }

    @DisplayName("경매 상태를 PROGRESS로 변경시 경매 상태가 COMPLETE 라면 예외가 발생한다.")
    @Test
    void completeStatusToProgress() {
        //given
        LocalDateTime current = LocalDateTime.of(2024, 8, 6, 7, 0);
        AuctionSchedule auctionSchedule = createAuctionSchedule(AuctionStatus.COMPLETE);

        //when
        assertThatThrownBy(() -> auctionScheduleService.modifyAuctionStatusToProgress(auctionSchedule.getId(), current))
            .isInstanceOf(AppException.class)
            .hasMessage("완료된 경매입니다.");

        //then
        Optional<AuctionSchedule> findAuctionSchedule = auctionScheduleRepository.findById(auctionSchedule.getId());
        assertThat(findAuctionSchedule).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("auctionStatus", AuctionStatus.COMPLETE);
    }

    @DisplayName("경매 일정 상태를 PROGRESS로 변경한다.")
    @Test
    void modifyAuctionStatusToProgress() {
        //given
        LocalDateTime current = LocalDateTime.of(2024, 8, 6, 7, 0);
        AuctionSchedule auctionSchedule = createAuctionSchedule(AuctionStatus.READY);

        //when
        AuctionStatusModifyResponse response = auctionScheduleService.modifyAuctionStatusToProgress(auctionSchedule.getId(), current);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("id", auctionSchedule.getId())
            .hasFieldOrPropertyWithValue("auctionStatus", AuctionStatus.PROGRESS)
            .hasFieldOrPropertyWithValue("modifyDateTime", current);

        Optional<AuctionSchedule> findAuctionSchedule = auctionScheduleRepository.findById(auctionSchedule.getId());
        assertThat(findAuctionSchedule).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("auctionStatus", AuctionStatus.PROGRESS);
    }

    @DisplayName("경매 상태를 COMPLETE로 변경시 경매 상태가 INIT 라면 예외가 발생한다.")
    @Test
    void initStatusToComplete() {
        //given
        LocalDateTime current = LocalDateTime.of(2024, 8, 6, 7, 0);
        AuctionSchedule auctionSchedule = createAuctionSchedule(AuctionStatus.INIT);

        //when
        assertThatThrownBy(() -> auctionScheduleService.modifyAuctionStatusToComplete(auctionSchedule.getId(), current))
            .isInstanceOf(AppException.class)
            .hasMessage("진행중인 경매가 아닙니다.");

        //then
        Optional<AuctionSchedule> findAuctionSchedule = auctionScheduleRepository.findById(auctionSchedule.getId());
        assertThat(findAuctionSchedule).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("auctionStatus", AuctionStatus.INIT);
    }

    @DisplayName("경매 상태를 PROGRESS로 변경시 경매 상태가 COMPLETE 라면 예외가 발생한다.")
    @Test
    void readyStatusToComplete() {
        //given
        LocalDateTime current = LocalDateTime.of(2024, 8, 6, 7, 0);
        AuctionSchedule auctionSchedule = createAuctionSchedule(AuctionStatus.READY);

        //when
        assertThatThrownBy(() -> auctionScheduleService.modifyAuctionStatusToComplete(auctionSchedule.getId(), current))
            .isInstanceOf(AppException.class)
            .hasMessage("진행중인 경매가 아닙니다.");

        //then
        Optional<AuctionSchedule> findAuctionSchedule = auctionScheduleRepository.findById(auctionSchedule.getId());
        assertThat(findAuctionSchedule).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("auctionStatus", AuctionStatus.READY);
    }

    @DisplayName("경매 일정 상태를 COMPLETE로 변경한다.")
    @Test
    void modifyAuctionStatusToComplete() {
        //given
        LocalDateTime current = LocalDateTime.of(2024, 8, 6, 7, 0);
        AuctionSchedule auctionSchedule = createAuctionSchedule(AuctionStatus.PROGRESS);

        //when
        AuctionStatusModifyResponse response = auctionScheduleService.modifyAuctionStatusToComplete(auctionSchedule.getId(), current);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("id", auctionSchedule.getId())
            .hasFieldOrPropertyWithValue("auctionStatus", AuctionStatus.COMPLETE)
            .hasFieldOrPropertyWithValue("modifyDateTime", current);

        Optional<AuctionSchedule> findAuctionSchedule = auctionScheduleRepository.findById(auctionSchedule.getId());
        assertThat(findAuctionSchedule).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("auctionStatus", AuctionStatus.COMPLETE);
    }

    private AuctionSchedule createAuctionSchedule(AuctionStatus auctionStatus) {
        AuctionSchedule auctionSchedule = AuctionSchedule.builder()
            .isDeleted(false)
            .createdBy(1L)
            .lastModifiedBy(1L)
            .auctionInfo(createAuctionInfo())
            .auctionStartDateTime(LocalDateTime.of(2024, 8, 12, 5, 0))
            .auctionStatus(auctionStatus)
            .build();
        return auctionScheduleRepository.save(auctionSchedule);
    }

    private AuctionInfo createAuctionInfo() {
        return AuctionInfo.builder()
            .plantCategory(PlantCategory.CUT_FLOWERS)
            .jointMarket(JointMarket.YANGJAE)
            .auctionDescription("auction description")
            .build();
    }

    private void mockingMemberId() {
        MemberIdResponse memberId = MemberIdResponse.builder()
            .memberId(1L)
            .build();
        ApiResponse<MemberIdResponse> apiResponse = ApiResponse.ok(memberId);

        given(memberServiceClient.searchMemberId())
            .willReturn(apiResponse);
    }
}