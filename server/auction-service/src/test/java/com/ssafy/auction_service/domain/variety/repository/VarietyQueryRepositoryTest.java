package com.ssafy.auction_service.domain.variety.repository;

import com.ssafy.auction_service.IntegrationTestSupport;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import com.ssafy.auction_service.domain.variety.Variety;
import com.ssafy.auction_service.domain.variety.VarietyInfo;
import com.ssafy.auction_service.domain.variety.repository.cond.VarietySearchCond;
import com.ssafy.auction_service.domain.variety.repository.response.VarietyResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class VarietyQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private VarietyQueryRepository varietyQueryRepository;

    @Autowired
    private VarietyRepository varietyRepository;

    @DisplayName("품종 목록 조회시 품목명이 없으면 전체를 조회한다.")
    @Test
    void findAllByCondWithoutItemName() {
        //given
        Variety variety1 = createVariety(false, "10031285", PlantCategory.CUT_FLOWERS, "장미", "하젤");
        Variety variety2 = createVariety(false, "10031204", PlantCategory.CUT_FLOWERS, "장미", "하트앤소울");
        createVariety(true, "10030934", PlantCategory.CUT_FLOWERS, "장미", "햇살(sp)");
        Variety variety3 = createVariety(false, "10011740", PlantCategory.CUT_FLOWERS, "국화", "개구리");
        Variety variety4 = createVariety(false, "10030547", PlantCategory.CUT_FLOWERS, "장미", "헤라");
        createVariety(false, "60031066", PlantCategory.ORCHID, "덴파레", "레드");
        createVariety(false, "85390027", PlantCategory.FOLIAGE, "장미", "미니장미 3.5\"");

        VarietySearchCond cond = VarietySearchCond.builder()
            .plantCategory(PlantCategory.CUT_FLOWERS)
            .itemName(null)
            .build();
        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        List<VarietyResponse> content = varietyQueryRepository.findAllByCond(cond, pageRequest);

        //then
        assertThat(content).hasSize(4)
            .extracting("code", "plantCategory", "itemName", "varietyName")
            .containsExactly(
                tuple(variety3.getCode(), PlantCategory.CUT_FLOWERS, "국화", "개구리"),
                tuple(variety1.getCode(), PlantCategory.CUT_FLOWERS, "장미", "하젤"),
                tuple(variety2.getCode(), PlantCategory.CUT_FLOWERS, "장미", "하트앤소울"),
                tuple(variety4.getCode(), PlantCategory.CUT_FLOWERS, "장미", "헤라")
            );
    }

    @DisplayName("검색 조건으로 품종 목록을 조회한다.")
    @Test
    void findAllByCond() {
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
        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        List<VarietyResponse> content = varietyQueryRepository.findAllByCond(cond, pageRequest);

        //then
        assertThat(content).hasSize(3)
            .extracting("code", "plantCategory", "itemName", "varietyName")
            .containsExactly(
                tuple(variety1.getCode(), PlantCategory.CUT_FLOWERS, "장미", "하젤"),
                tuple(variety2.getCode(), PlantCategory.CUT_FLOWERS, "장미", "하트앤소울"),
                tuple(variety3.getCode(), PlantCategory.CUT_FLOWERS, "장미", "헤라")
            );
    }

    private Variety createVariety(boolean isDeleted, String code, PlantCategory plantCategory, String itemName, String varietyName) {
        Variety variety = Variety.builder()
            .isDeleted(isDeleted)
            .createdBy(1L)
            .lastModifiedBy(1L)
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