package com.ssafy.auction_service.api.service.auctionschedule;

import com.ssafy.auction_service.IntegrationTestSupport;
import com.ssafy.auction_service.api.ApiResponse;
import com.ssafy.auction_service.api.client.MemberServiceClient;
import com.ssafy.auction_service.api.client.response.MemberIdResponse;
import com.ssafy.auction_service.api.service.auctionschedule.request.AuctionScheduleCreateServiceRequest;
import com.ssafy.auction_service.api.service.auctionschedule.request.AuctionScheduleModifyServiceRequest;
import com.ssafy.auction_service.api.service.auctionschedule.response.AuctionScheduleCreateResponse;
import com.ssafy.auction_service.api.service.auctionschedule.response.AuctionScheduleModifyResponse;
import com.ssafy.auction_service.api.service.auctionschedule.response.AuctionScheduleRemoveResponse;
import com.ssafy.auction_service.api.service.auctionschedule.response.AuctionStatusModifyResponse;
import com.ssafy.auction_service.common.exception.AppException;
import com.ssafy.auction_service.domain.auctionschedule.AuctionInfo;
import com.ssafy.auction_service.domain.auctionschedule.AuctionSchedule;
import com.ssafy.auction_service.domain.auctionschedule.AuctionStatus;
import com.ssafy.auction_service.domain.auctionschedule.JointMarket;
import com.ssafy.auction_service.domain.auctionschedule.repository.AuctionScheduleRepository;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

    @BeforeEach
    void setUp() {
        mockingMemberId();
    }

    @DisplayName("같은 경매 일정이 존재하면 예외가 발생한다.")
    @Test
    void duplicatedAuctionSchedule() {
        //given
        LocalDateTime current = LocalDateTime.of(2024, 8, 2, 0, 0, 0);

        createAuctionSchedule(AuctionStatus.INIT, LocalDateTime.of(2024, 8, 12, 5, 0));

        AuctionScheduleCreateServiceRequest request = AuctionScheduleCreateServiceRequest.builder()
            .plantCategory(PlantCategory.CUT_FLOWERS)
            .jointMarket(JointMarket.YANGJAE)
            .auctionDescription("경매 예정입니다.")
            .auctionStartDateTime(LocalDateTime.of(2024, 8, 12, 5, 0))
            .build();

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
            .plantCategory(PlantCategory.CUT_FLOWERS)
            .jointMarket(JointMarket.YANGJAE)
            .auctionDescription("경매 예정입니다.")
            .auctionStartDateTime(LocalDateTime.of(2024, 8, 12, 5, 0))
            .build();

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

    @DisplayName("경매 일정 수정시 같은 경매 일정이 존재하면 예외가 발생한다.")
    @Test
    void duplicatedAuctionSchedule2() {
        //given
        LocalDateTime current = LocalDateTime.of(2024, 8, 15, 10, 0);
        createAuctionSchedule(AuctionStatus.INIT, LocalDateTime.of(2024, 8, 12, 5, 0));

        AuctionSchedule auctionSchedule = createAuctionSchedule(AuctionStatus.INIT, LocalDateTime.of(2024, 8, 11, 5, 0));

        AuctionScheduleModifyServiceRequest request = AuctionScheduleModifyServiceRequest.builder()
            .auctionStartDateTime(LocalDateTime.of(2024, 8, 12, 5, 0))
            .auctionDescription("수정된 설명입니다.")
            .build();

        //when
        assertThatThrownBy(() -> auctionScheduleService.modifyAuctionSchedule(auctionSchedule.getId(), request, current))
            .isInstanceOf(AppException.class)
            .hasMessage("이미 등록된 경매 일정이 있습니다.");

        //then
        Optional<AuctionSchedule> findAuctionSchedule = auctionScheduleRepository.findById(auctionSchedule.getId());
        assertThat(findAuctionSchedule).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("auctionInfo.auctionStartDateTime", LocalDateTime.of(2024, 8, 11, 5, 0))
            .hasFieldOrPropertyWithValue("auctionDescription", "auction description");
    }

    @DisplayName("경매 일정 수정시 상태가 INIT이 아니라면 예외가 발생한다.")
    @ValueSource(strings = {"READY", "PROGRESS", "COMPLETE"})
    @ParameterizedTest
    void impossibleModifyStatus(AuctionStatus auctionStatus) {
        //given
        LocalDateTime current = LocalDateTime.of(2024, 8, 15, 10, 0);
        AuctionSchedule auctionSchedule = createAuctionSchedule(auctionStatus, LocalDateTime.of(2024, 8, 12, 5, 0));

        AuctionScheduleModifyServiceRequest request = AuctionScheduleModifyServiceRequest.builder()
            .auctionStartDateTime(LocalDateTime.of(2024, 9, 6, 10, 0))
            .auctionDescription("수정된 설명입니다.")
            .build();

        //when
        assertThatThrownBy(() -> auctionScheduleService.modifyAuctionSchedule(auctionSchedule.getId(), request, current))
            .isInstanceOf(AppException.class)
            .hasMessage("더이상 경매 일정을 수정할 수 없습니다.");

        //then
        Optional<AuctionSchedule> findAuctionSchedule = auctionScheduleRepository.findById(auctionSchedule.getId());
        assertThat(findAuctionSchedule).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("auctionInfo.auctionStartDateTime", LocalDateTime.of(2024, 8, 12, 5, 0))
            .hasFieldOrPropertyWithValue("auctionDescription", "auction description");
    }

    @DisplayName("경매 시간과 경매 설명을 입력 받아 경매 일정을 수정한다.")
    @Test
    void modifyAuctionSchedule() {
        //given
        LocalDateTime current = LocalDateTime.of(2024, 8, 15, 10, 0);
        AuctionSchedule auctionSchedule = createAuctionSchedule(AuctionStatus.INIT, LocalDateTime.of(2024, 8, 12, 5, 0));

        AuctionScheduleModifyServiceRequest request = AuctionScheduleModifyServiceRequest.builder()
            .auctionStartDateTime(LocalDateTime.of(2024, 9, 6, 10, 0))
            .auctionDescription("수정된 설명입니다.")
            .build();

        //when
        AuctionScheduleModifyResponse response = auctionScheduleService.modifyAuctionSchedule(auctionSchedule.getId(), request, current);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("id", auctionSchedule.getId())
            .hasFieldOrPropertyWithValue("plantCategory", "절화")
            .hasFieldOrPropertyWithValue("jointMarket", "양재")
            .hasFieldOrPropertyWithValue("auctionStartDateTime", LocalDateTime.of(2024, 9, 6, 10, 0))
            .hasFieldOrPropertyWithValue("auctionStatus", AuctionStatus.INIT)
            .hasFieldOrPropertyWithValue("modifiedDateTime", current);

        Optional<AuctionSchedule> findAuctionSchedule = auctionScheduleRepository.findById(auctionSchedule.getId());
        assertThat(findAuctionSchedule).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("auctionInfo.auctionStartDateTime", LocalDateTime.of(2024, 9, 6, 10, 0))
            .hasFieldOrPropertyWithValue("auctionDescription", "수정된 설명입니다.");
    }

    @DisplayName("경매 상태를 READY로 변경시 경매 상태가 PROGRESS 라면 예외가 발생한다.")
    @Test
    void progressStatusToReady() {
        //given
        LocalDateTime current = LocalDateTime.of(2024, 8, 6, 7, 0);
        AuctionSchedule auctionSchedule = createAuctionSchedule(AuctionStatus.PROGRESS, LocalDateTime.of(2024, 8, 12, 5, 0));

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
        AuctionSchedule auctionSchedule = createAuctionSchedule(AuctionStatus.COMPLETE, LocalDateTime.of(2024, 8, 12, 5, 0));

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
        AuctionSchedule auctionSchedule = createAuctionSchedule(AuctionStatus.INIT, LocalDateTime.of(2024, 8, 12, 5, 0));

        //when
        AuctionStatusModifyResponse response = auctionScheduleService.modifyAuctionStatusToReady(auctionSchedule.getId(), current);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("id", auctionSchedule.getId())
            .hasFieldOrPropertyWithValue("auctionStatus", AuctionStatus.READY)
            .hasFieldOrPropertyWithValue("modifiedDateTime", current);

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
        AuctionSchedule auctionSchedule = createAuctionSchedule(AuctionStatus.INIT, LocalDateTime.of(2024, 8, 12, 5, 0));

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
        AuctionSchedule auctionSchedule = createAuctionSchedule(AuctionStatus.COMPLETE, LocalDateTime.of(2024, 8, 12, 5, 0));

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
        AuctionSchedule auctionSchedule = createAuctionSchedule(AuctionStatus.READY, LocalDateTime.of(2024, 8, 12, 5, 0));

        //when
        AuctionStatusModifyResponse response = auctionScheduleService.modifyAuctionStatusToProgress(auctionSchedule.getId(), current);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("id", auctionSchedule.getId())
            .hasFieldOrPropertyWithValue("auctionStatus", AuctionStatus.PROGRESS)
            .hasFieldOrPropertyWithValue("modifiedDateTime", current);

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
        AuctionSchedule auctionSchedule = createAuctionSchedule(AuctionStatus.INIT, LocalDateTime.of(2024, 8, 12, 5, 0));

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
        AuctionSchedule auctionSchedule = createAuctionSchedule(AuctionStatus.READY, LocalDateTime.of(2024, 8, 12, 5, 0));

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
        AuctionSchedule auctionSchedule = createAuctionSchedule(AuctionStatus.PROGRESS, LocalDateTime.of(2024, 8, 12, 5, 0));

        //when
        AuctionStatusModifyResponse response = auctionScheduleService.modifyAuctionStatusToComplete(auctionSchedule.getId(), current);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("id", auctionSchedule.getId())
            .hasFieldOrPropertyWithValue("auctionStatus", AuctionStatus.COMPLETE)
            .hasFieldOrPropertyWithValue("modifiedDateTime", current);

        Optional<AuctionSchedule> findAuctionSchedule = auctionScheduleRepository.findById(auctionSchedule.getId());
        assertThat(findAuctionSchedule).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("auctionStatus", AuctionStatus.COMPLETE);
    }

    @DisplayName("경매 일정 삭제시 상태가 INIT이 아니라면 예외가 발생한다.")
    @ValueSource(strings = {"READY", "PROGRESS", "COMPLETE"})
    @ParameterizedTest
    void impossibleRemoveStatus(AuctionStatus status) {
        //given
        LocalDateTime current = LocalDateTime.of(2024, 8, 6, 7, 0);
        AuctionSchedule auctionSchedule = createAuctionSchedule(status, LocalDateTime.of(2024, 8, 12, 5, 0));

        //when
        assertThatThrownBy(() -> auctionScheduleService.removeAuctionSchedule(auctionSchedule.getId(), current))
            .isInstanceOf(AppException.class)
            .hasMessage("경매 일정을 삭제할 수 없습니다.");

        //then
        Optional<AuctionSchedule> findAuctionSchedule = auctionScheduleRepository.findById(auctionSchedule.getId());
        assertThat(findAuctionSchedule).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("isDeleted", false);
    }

    @DisplayName("경매 일정을 삭제한다.")
    @Test
    void removeAuctionSchedule() {
        //given
        LocalDateTime current = LocalDateTime.of(2024, 8, 6, 7, 0);
        AuctionSchedule auctionSchedule = createAuctionSchedule(AuctionStatus.INIT, LocalDateTime.of(2024, 8, 12, 5, 0));

        //when
        AuctionScheduleRemoveResponse response = auctionScheduleService.removeAuctionSchedule(auctionSchedule.getId(), current);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("id", auctionSchedule.getId())
            .hasFieldOrPropertyWithValue("plantCategory", "절화")
            .hasFieldOrPropertyWithValue("jointMarket", "양재")
            .hasFieldOrPropertyWithValue("auctionStartDateTime", LocalDateTime.of(2024, 8, 12, 5, 0))
            .hasFieldOrPropertyWithValue("auctionStatus", AuctionStatus.INIT)
            .hasFieldOrPropertyWithValue("removedDateTime", current);

        Optional<AuctionSchedule> findAuctionSchedule = auctionScheduleRepository.findById(auctionSchedule.getId());
        assertThat(findAuctionSchedule).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("isDeleted", true);
    }

    private AuctionSchedule createAuctionSchedule(AuctionStatus auctionStatus, LocalDateTime auctionStartDateTime) {
        AuctionSchedule auctionSchedule = AuctionSchedule.builder()
            .isDeleted(false)
            .createdBy(1L)
            .lastModifiedBy(1L)
            .auctionInfo(createAuctionInfo(auctionStartDateTime))
            .auctionStatus(auctionStatus)
            .auctionDescription("auction description")
            .build();
        return auctionScheduleRepository.save(auctionSchedule);
    }

    private AuctionInfo createAuctionInfo(LocalDateTime auctionStartDateTime) {
        return AuctionInfo.builder()
            .plantCategory(PlantCategory.CUT_FLOWERS)
            .jointMarket(JointMarket.YANGJAE)
            .auctionStartDateTime(auctionStartDateTime)
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