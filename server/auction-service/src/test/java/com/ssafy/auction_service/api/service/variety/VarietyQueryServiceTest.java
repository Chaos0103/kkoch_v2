package com.ssafy.auction_service.api.service.variety;

import com.ssafy.auction_service.IntegrationTestSupport;
import com.ssafy.auction_service.api.ListResponse;
import com.ssafy.auction_service.api.PageResponse;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import com.ssafy.auction_service.domain.variety.Variety;
import com.ssafy.auction_service.domain.variety.VarietyInfo;
import com.ssafy.auction_service.domain.variety.repository.VarietyRepository;
import com.ssafy.auction_service.domain.variety.repository.cond.VarietySearchCond;
import com.ssafy.auction_service.domain.variety.repository.response.ItemNameResponse;
import com.ssafy.auction_service.domain.variety.repository.response.VarietyResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class VarietyQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private VarietyQueryService varietyQueryService;

    @Autowired
    private VarietyRepository varietyRepository;

    @DisplayName("검색 조건과 페이지 번호를 입력 받아 품종 목록을 조회한다.")
    @Test
    void searchVarieties() {
        //given
        Variety variety1 = createVariety(false, "10031285", PlantCategory.CUT_FLOWERS, "장미", "하젤");
        Variety variety2 = createVariety(false, "10031204", PlantCategory.CUT_FLOWERS, "장미", "하트앤소울");
        createVariety(true, "10030934", PlantCategory.CUT_FLOWERS, "장미", "햇살(sp)");
        createVariety(false, "10011740", PlantCategory.CUT_FLOWERS, "국화", "개구리");
        Variety variety3 = createVariety(false, "10030547", PlantCategory.CUT_FLOWERS, "장미", "헤라");
        createVariety(false, "60031066", PlantCategory.ORCHID, "덴파레", "레드");
        createVariety(false, "85390027", PlantCategory.FOLIAGE, "장미", "미니장미 3.5\"");

        VarietySearchCond cond = VarietySearchCond.builder()
            .plantCategory(PlantCategory.CUT_FLOWERS)
            .itemName("장미")
            .build();

        //when
        PageResponse<VarietyResponse> response = varietyQueryService.searchVarieties(cond, 1);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("currentPage", 1)
            .hasFieldOrPropertyWithValue("size", 10)
            .hasFieldOrPropertyWithValue("isFirst", true)
            .hasFieldOrPropertyWithValue("isLast", true);
        assertThat(response.getContent()).hasSize(3)
            .extracting("code", "plantCategory", "itemName", "varietyName")
            .containsExactly(
                tuple(variety1.getCode(), PlantCategory.CUT_FLOWERS, "장미", "하젤"),
                tuple(variety2.getCode(), PlantCategory.CUT_FLOWERS, "장미", "하트앤소울"),
                tuple(variety3.getCode(), PlantCategory.CUT_FLOWERS, "장미", "헤라")
            );
    }

    @DisplayName("화훼부류를 입력 받아 품목명 목록을 조회한다.")
    @Test
    void searchItemNames() {
        //given
        createVariety(false, "10031285", PlantCategory.CUT_FLOWERS, "장미", "하젤");
        createVariety(false, "10031204", PlantCategory.CUT_FLOWERS, "장미", "하트앤소울");
        createVariety(false, "10011740", PlantCategory.CUT_FLOWERS, "국화", "개구리");
        createVariety(true, "10270008", PlantCategory.CUT_FLOWERS, "허브", "로즈마리");
        createVariety(false, "60031066", PlantCategory.ORCHID, "덴파레", "레드");
        createVariety(false, "85390027", PlantCategory.FOLIAGE, "장미", "미니장미 3.5\"");

        //when
        ListResponse<ItemNameResponse> response = varietyQueryService.searchItemNames(PlantCategory.CUT_FLOWERS);

        //then
        assertThat(response.getContent()).hasSize(2)
            .extracting("itemName")
            .containsExactly(
                "국화", "장미"
            );
    }

    private Variety createVariety(boolean isDeleted, String code, PlantCategory plantCategory, String itemName, String varietyName) {
        Variety variety = Variety.builder()
            .isDeleted(isDeleted)
            .code(code)
            .info(VarietyInfo.builder()
                .plantCategory(plantCategory)
                .itemName(itemName)
                .varietyName(varietyName)
                .build())
            .build();
        return varietyRepository.save(variety);
    }
}