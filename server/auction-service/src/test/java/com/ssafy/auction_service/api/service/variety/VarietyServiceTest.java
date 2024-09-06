package com.ssafy.auction_service.api.service.variety;

import com.ssafy.auction_service.IntegrationTestSupport;
import com.ssafy.auction_service.api.ApiResponse;
import com.ssafy.auction_service.api.client.MemberServiceClient;
import com.ssafy.auction_service.api.client.response.MemberIdResponse;
import com.ssafy.auction_service.api.service.variety.request.VarietyCreateServiceRequest;
import com.ssafy.auction_service.api.service.variety.response.VarietyCreateResponse;
import com.ssafy.auction_service.api.service.variety.response.VarietyModifyResponse;
import com.ssafy.auction_service.common.exception.AppException;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import com.ssafy.auction_service.domain.variety.Variety;
import com.ssafy.auction_service.domain.variety.repository.VarietyRepository;
import org.junit.jupiter.api.BeforeEach;
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

class VarietyServiceTest extends IntegrationTestSupport {

    @Autowired
    private VarietyService varietyService;

    @Autowired
    private VarietyRepository varietyRepository;

    @MockBean
    private MemberServiceClient memberServiceClient;

    @BeforeEach
    void setUp() {
        mockingMemberId();
    }

    @DisplayName("이미 등록된 품종이라면 예외가 발생한다.")
    @Test
    void existVariety() {
        //given
        createVariety("10000001", "하트앤소울");

        VarietyCreateServiceRequest request = VarietyCreateServiceRequest.builder()
            .plantCategory("CUT_FLOWERS")
            .itemName("장미")
            .varietyName("하트앤소울")
            .build();

        //when
        assertThatThrownBy(() -> varietyService.createVariety(request))
            .isInstanceOf(AppException.class)
            .hasMessage("이미 등록된 품종입니다.");

        //then
        List<Variety> varieties = varietyRepository.findAll();
        assertThat(varieties).hasSize(1);
    }

    @DisplayName("품종 정보를 입력 받아 품종을 등록한다.")
    @Test
    void createVariety() {
        //given
        VarietyCreateServiceRequest request = VarietyCreateServiceRequest.builder()
            .plantCategory("CUT_FLOWERS")
            .itemName("장미")
            .varietyName("하트앤소울")
            .build();

        //when
        VarietyCreateResponse response = varietyService.createVariety(request);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("code", "10000001")
            .hasFieldOrPropertyWithValue("plantCategory", "절화")
            .hasFieldOrPropertyWithValue("itemName", "장미")
            .hasFieldOrPropertyWithValue("varietyName", "하트앤소울");

        List<Variety> varieties = varietyRepository.findAll();
        assertThat(varieties).hasSize(1);
    }

    @DisplayName("품종명 수정시 이미 등록된 품종이라면 예외가 발생한다.")
    @Test
    void existVariety2() {
        //given
        LocalDateTime current = LocalDateTime.of(2024, 9, 6, 7, 0);
        Variety variety = createVariety("10000001", "하트앤소울");
        createVariety("10000001", "심화");

        //when
        assertThatThrownBy(() -> varietyService.modifyVariety(variety.getCode(), "심화", current))
            .isInstanceOf(AppException.class)
            .hasMessage("이미 등록된 품종입니다.");

        //then
        Optional<Variety> findVariety = varietyRepository.findById("10000001");
        assertThat(findVariety).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("varietyName", "하트앤소울");
    }

    @DisplayName("품종명을 수정한다.")
    @Test
    void modifyVariety() {
        //given
        LocalDateTime current = LocalDateTime.of(2024, 9, 6, 7, 0);
        Variety variety = createVariety("10000001", "하트앤소울");

        //when
        VarietyModifyResponse response = varietyService.modifyVariety(variety.getCode(), "심화", current);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("code", "10000001")
            .hasFieldOrPropertyWithValue("plantCategory", "절화")
            .hasFieldOrPropertyWithValue("itemName", "장미")
            .hasFieldOrPropertyWithValue("varietyName", "심화")
            .hasFieldOrPropertyWithValue("modifiedDateTime", current);

        Optional<Variety> findVariety = varietyRepository.findById("10000001");
        assertThat(findVariety).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("varietyName", "심화");
    }

    private Variety createVariety(String code, String varietyName) {
        Variety variety = Variety.builder()
            .isDeleted(false)
            .createdBy(1L)
            .lastModifiedBy(1L)
            .code(code)
            .plantCategory(PlantCategory.CUT_FLOWERS)
            .itemName("장미")
            .varietyName(varietyName)
            .build();
        return varietyRepository.save(variety);
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