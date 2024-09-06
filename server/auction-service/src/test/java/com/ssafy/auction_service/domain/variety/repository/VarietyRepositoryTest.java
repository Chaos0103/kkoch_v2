package com.ssafy.auction_service.domain.variety.repository;

import com.ssafy.auction_service.IntegrationTestSupport;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import com.ssafy.auction_service.domain.variety.Variety;
import com.ssafy.auction_service.domain.variety.VarietyInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class VarietyRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private VarietyRepository varietyRepository;

    @DisplayName("품종 코드를 조회한다.")
    @Test
    void findCodeByVariety() {
        //given
        Variety variety = createVariety();

        //when
        Optional<String> varietyCode = varietyRepository.findCodeByVariety(variety.getInfo());

        //then
        assertThat(varietyCode).isPresent();
    }

    @DisplayName("같은 화훼부류로 등록된 품종의 갯수를 조회한다.")
    @Test
    void countByPlantCategory() {
        //given
        createVariety();

        //when
        int count = varietyRepository.countByInfoPlantCategory(PlantCategory.CUT_FLOWERS);

        //then
        assertThat(count).isOne();
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

}