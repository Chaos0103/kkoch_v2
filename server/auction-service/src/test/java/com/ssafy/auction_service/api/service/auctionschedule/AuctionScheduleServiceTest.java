package com.ssafy.auction_service.api.service.auctionschedule;

import com.ssafy.auction_service.IntegrationTestSupport;
import com.ssafy.auction_service.api.ApiResponse;
import com.ssafy.auction_service.api.client.MemberServiceClient;
import com.ssafy.auction_service.api.client.response.MemberIdResponse;
import com.ssafy.auction_service.api.service.auctionschedule.request.AuctionScheduleCreateServiceRequest;
import com.ssafy.auction_service.api.service.auctionschedule.response.AuctionScheduleCreateResponse;
import com.ssafy.auction_service.common.exception.AppException;
import com.ssafy.auction_service.domain.auctionschedule.AuctionInfo;
import com.ssafy.auction_service.domain.auctionschedule.AuctionSchedule;
import com.ssafy.auction_service.domain.auctionschedule.AuctionStatue;
import com.ssafy.auction_service.domain.auctionschedule.JointMarket;
import com.ssafy.auction_service.domain.auctionschedule.repository.AuctionScheduleRepository;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;

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

        createAuctionSchedule(AuctionStatue.INIT);

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
            .hasFieldOrPropertyWithValue("auctionStatus", AuctionStatue.INIT);

        List<AuctionSchedule> auctionSchedules = auctionScheduleRepository.findAll();
        assertThat(auctionSchedules).hasSize(1);
    }

    private AuctionSchedule createAuctionSchedule(AuctionStatue auctionStatue) {
        AuctionSchedule auctionSchedule = AuctionSchedule.builder()
            .isDeleted(false)
            .createdBy(1L)
            .lastModifiedBy(1L)
            .auctionInfo(createAuctionInfo())
            .auctionStartDateTime(LocalDateTime.of(2024, 8, 12, 5, 0))
            .auctionStatue(auctionStatue)
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