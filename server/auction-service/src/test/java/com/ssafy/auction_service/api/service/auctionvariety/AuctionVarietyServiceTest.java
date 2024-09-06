package com.ssafy.auction_service.api.service.auctionvariety;

import com.ssafy.auction_service.IntegrationTestSupport;
import com.ssafy.auction_service.api.ApiResponse;
import com.ssafy.auction_service.api.client.MemberServiceClient;
import com.ssafy.auction_service.api.client.response.MemberIdResponse;
import com.ssafy.auction_service.api.service.auctionvariety.request.AuctionVarietyCreateServiceRequest;
import com.ssafy.auction_service.api.service.auctionvariety.response.AuctionVarietyCreateResponse;
import com.ssafy.auction_service.common.exception.AppException;
import com.ssafy.auction_service.domain.auctionschedule.AuctionInfo;
import com.ssafy.auction_service.domain.auctionschedule.AuctionSchedule;
import com.ssafy.auction_service.domain.auctionschedule.AuctionStatus;
import com.ssafy.auction_service.domain.auctionschedule.JointMarket;
import com.ssafy.auction_service.domain.auctionschedule.repository.AuctionScheduleRepository;
import com.ssafy.auction_service.domain.auctionvariety.AuctionVariety;
import com.ssafy.auction_service.domain.auctionvariety.PlantGrade;
import com.ssafy.auction_service.domain.auctionvariety.Price;
import com.ssafy.auction_service.domain.auctionvariety.repository.AuctionVarietyRepository;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import com.ssafy.auction_service.domain.variety.Variety;
import com.ssafy.auction_service.domain.variety.VarietyInfo;
import com.ssafy.auction_service.domain.variety.repository.VarietyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

class AuctionVarietyServiceTest extends IntegrationTestSupport {

    @Autowired
    private AuctionVarietyService auctionVarietyService;

    @Autowired
    private AuctionVarietyRepository auctionVarietyRepository;

    @Autowired
    private VarietyRepository varietyRepository;

    @Autowired
    private AuctionScheduleRepository auctionScheduleRepository;

    @MockBean
    private MemberServiceClient memberServiceClient;

    @DisplayName("경매 품종 등록시 경매 상태가 INIT 상태일 때 경매 품종을 등록할 수 있다.")
    @CsvSource({"READY", "PROGRESS", "COMPLETE"})
    @ParameterizedTest
    void createAuctionVarietyAuctionStateNotInit(AuctionStatus statue) {
        //given
        Variety variety = createVariety();
        AuctionSchedule auctionSchedule = createAuctionSchedule(PlantCategory.CUT_FLOWERS, statue);

        AuctionVarietyCreateServiceRequest request = AuctionVarietyCreateServiceRequest.builder()
            .plantGrade(PlantGrade.SUPER)
            .plantCount(10)
            .auctionStartPrice(Price.of(4500))
            .region("광주")
            .shipper("김출하")
            .build();

        mockingMemberId();

        //when
        assertThatThrownBy(() -> auctionVarietyService.createAuctionVariety(variety.getCode(), auctionSchedule.getId(), request))
            .isInstanceOf(AppException.class)
            .hasMessage("경매 품종을 등록할 수 없습니다.");

        //then
        List<AuctionVariety> auctionVarieties = auctionVarietyRepository.findAll();
        assertThat(auctionVarieties).isEmpty();
    }

    @DisplayName("경매 일정의 화훼부류와 등록할 품종의 화훼부류가 다르다면 예외가 발생한다.")
    @Test
    void createAuctionVarietyNotEqualsPlantCategory() {
        //given
        Variety variety = createVariety();
        AuctionSchedule auctionSchedule = createAuctionSchedule(PlantCategory.ORCHID, AuctionStatus.INIT);

        AuctionVarietyCreateServiceRequest request = AuctionVarietyCreateServiceRequest.builder()
            .plantGrade(PlantGrade.SUPER)
            .plantCount(10)
            .auctionStartPrice(Price.of(4500))
            .region("광주")
            .shipper("김출하")
            .build();

        mockingMemberId();

        //when
        assertThatThrownBy(() -> auctionVarietyService.createAuctionVariety(variety.getCode(), auctionSchedule.getId(), request))
            .isInstanceOf(AppException.class)
            .hasMessage("해당 경매에 등록할 수 없는 품종입니다.");

        //then
        List<AuctionVariety> auctionVarieties = auctionVarietyRepository.findAll();
        assertThat(auctionVarieties).isEmpty();
    }

    @DisplayName("경매 품종 정보를 입력 받아 경매 품종을 등록한다.")
    @Test
    void createAuctionVariety() {
        //given
        Variety variety = createVariety();
        AuctionSchedule auctionSchedule = createAuctionSchedule(PlantCategory.CUT_FLOWERS, AuctionStatus.INIT);

        AuctionVarietyCreateServiceRequest request = AuctionVarietyCreateServiceRequest.builder()
            .plantGrade(PlantGrade.SUPER)
            .plantCount(10)
            .auctionStartPrice(Price.of(4500))
            .region("광주")
            .shipper("김출하")
            .build();

        mockingMemberId();

        //when
        AuctionVarietyCreateResponse response = auctionVarietyService.createAuctionVariety(variety.getCode(), auctionSchedule.getId(), request);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("listingNumber", "00001")
            .hasFieldOrPropertyWithValue("plantGrade", PlantGrade.SUPER)
            .hasFieldOrPropertyWithValue("plantCount", 10)
            .hasFieldOrPropertyWithValue("auctionStartPrice", 4500)
            .hasFieldOrPropertyWithValue("region", "광주")
            .hasFieldOrPropertyWithValue("shipper", "김출하");

        List<AuctionVariety> auctionVarieties = auctionVarietyRepository.findAll();
        assertThat(auctionVarieties).hasSize(1);
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

    private AuctionSchedule createAuctionSchedule(PlantCategory plantCategory, AuctionStatus auctionStatus) {
        AuctionSchedule auctionSchedule = AuctionSchedule.builder()
            .isDeleted(false)
            .createdBy(1L)
            .lastModifiedBy(1L)
            .auctionInfo(AuctionInfo.builder()
                .plantCategory(plantCategory)
                .jointMarket(JointMarket.YANGJAE)
                .auctionDescription("경매 설명")
                .build())
            .auctionStartDateTime(LocalDateTime.of(2024, 7, 12, 5, 0))
            .auctionStatus(auctionStatus)
            .build();
        return auctionScheduleRepository.save(auctionSchedule);
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