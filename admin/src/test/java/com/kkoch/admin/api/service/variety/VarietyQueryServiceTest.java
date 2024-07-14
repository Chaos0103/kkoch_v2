package com.kkoch.admin.api.service.variety;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.domain.variety.PlantCategory;
import com.kkoch.admin.domain.variety.Variety;
import com.kkoch.admin.domain.variety.repository.VarietyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class VarietyQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private VarietyQueryService varietyQueryService;

    @Autowired
    private VarietyRepository varietyRepository;

    @DisplayName("화훼부류를 입력 받아 품목명 목록을 조회한다.")
    @Test
    void searchItemNames() {
        //given
        Variety variety1 = createVariety("10000001", PlantCategory.CUT_FLOWERS, "장미", "하트앤소울", false);
        Variety variety2 = createVariety("10000002", PlantCategory.CUT_FLOWERS, "장미", "골든부부젤라(sp)", true);
        Variety variety3 = createVariety("10000003", PlantCategory.CUT_FLOWERS, "국화", "개구리", false);
        Variety variety4 = createVariety("10000004", PlantCategory.CUT_FLOWERS, "장미", "고르키파크", false);
        Variety variety5 = createVariety("20000001", PlantCategory.FOLIAGE, "동양란", "금화", false);
        Variety variety6 = createVariety("30000001", PlantCategory.ORCHID, "가자니아", "가자니아 3.5\"", false);

        //when
        List<String> content = varietyQueryService.searchItemNames("CUT_FLOWERS");

        //then
        assertThat(content).hasSize(2)
            .containsExactly("국화", "장미");
    }

    @DisplayName("품목명을 입력 받아 품종명 목록을 조회한다.")
    @Test
    void searchVarietyNames() {
        //given
        Variety variety1 = createVariety("10000001", PlantCategory.CUT_FLOWERS, "장미", "하트앤소울", false);
        Variety variety2 = createVariety("10000002", PlantCategory.CUT_FLOWERS, "장미", "골든부부젤라(sp)", true);
        Variety variety3 = createVariety("10000003", PlantCategory.CUT_FLOWERS, "국화", "개구리", false);
        Variety variety4 = createVariety("10000004", PlantCategory.CUT_FLOWERS, "장미", "고르키파크", false);
        Variety variety5 = createVariety("20000001", PlantCategory.FOLIAGE, "동양란", "금화", false);
        Variety variety6 = createVariety("30000001", PlantCategory.ORCHID, "가자니아", "가자니아 3.5\"", false);

        //when
        List<String> content = varietyQueryService.searchVarietyNames("장미");

        //then
        assertThat(content).hasSize(2)
            .containsExactly("고르키파크", "하트앤소울");
    }

    public Variety createVariety(String code, PlantCategory plantCategory, String itemName, String varietyName, boolean isDeleted) {
        Variety variety = Variety.builder()
            .isDeleted(isDeleted)
            .createdBy(1)
            .lastModifiedBy(1)
            .code(code)
            .plantCategory(plantCategory)
            .itemName(itemName)
            .varietyName(varietyName)
            .build();
        return varietyRepository.save(variety);
    }
}