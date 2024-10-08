package com.ssafy.auction_service.api.service.variety;

import com.ssafy.auction_service.IntegrationTestSupport;
import com.ssafy.auction_service.api.service.variety.request.VarietyCreateServiceRequest;
import com.ssafy.auction_service.api.service.variety.response.VarietyCreateResponse;
import com.ssafy.auction_service.api.service.variety.response.VarietyModifyResponse;
import com.ssafy.auction_service.api.service.variety.response.VarietyRemoveResponse;
import com.ssafy.auction_service.common.exception.AppException;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import com.ssafy.auction_service.domain.variety.Variety;
import com.ssafy.auction_service.domain.variety.VarietyInfo;
import com.ssafy.auction_service.domain.variety.repository.VarietyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VarietyServiceTest extends IntegrationTestSupport {

    @Autowired
    private VarietyService varietyService;

    @Autowired
    private VarietyRepository varietyRepository;

    @DisplayName("이미 등록된 품종이라면 예외가 발생한다.")
    @Test
    void existVariety() {
        //given
        createVariety("10000001", "하트앤소울");

        VarietyCreateServiceRequest request = VarietyCreateServiceRequest.builder()
            .plantCategory(PlantCategory.CUT_FLOWERS)
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
            .plantCategory(PlantCategory.CUT_FLOWERS)
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
        Variety variety = createVariety("10000001", "하트앤소울");
        createVariety("10000002", "심화");

        //when
        assertThatThrownBy(() -> varietyService.modifyVariety(variety.getCode(), "심화"))
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
        Variety variety = createVariety("10000001", "하트앤소울");

        //when
        VarietyModifyResponse response = varietyService.modifyVariety(variety.getCode(), "심화");

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("code", "10000001")
            .hasFieldOrPropertyWithValue("plantCategory", "절화")
            .hasFieldOrPropertyWithValue("itemName", "장미")
            .hasFieldOrPropertyWithValue("varietyName", "심화");

        Optional<Variety> findVariety = varietyRepository.findById("10000001");
        assertThat(findVariety).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("varietyName", "심화");
    }

    @DisplayName("품종을 삭제한다.")
    @Test
    void removeVariety() {
        //given
        Variety variety = createVariety("10000001", "하트앤소울");

        //when
        VarietyRemoveResponse response = varietyService.removeVariety(variety.getCode());

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("code", "10000001")
            .hasFieldOrPropertyWithValue("plantCategory", "절화")
            .hasFieldOrPropertyWithValue("itemName", "장미")
            .hasFieldOrPropertyWithValue("varietyName", "하트앤소울");

        Optional<Variety> findVariety = varietyRepository.findById("10000001");
        assertThat(findVariety).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("isDeleted", true);
    }

    private Variety createVariety(String code, String varietyName) {
        Variety variety = Variety.builder()
            .isDeleted(false)
            .code(code)
            .info(
                VarietyInfo.builder()
                    .plantCategory(PlantCategory.CUT_FLOWERS)
                    .itemName("장미")
                    .varietyName(varietyName)
                    .build()
            )
            .build();
        return varietyRepository.save(variety);
    }
}