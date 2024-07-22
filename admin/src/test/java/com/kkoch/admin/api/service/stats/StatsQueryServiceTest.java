package com.kkoch.admin.api.service.stats;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.api.ListResponse;
import com.kkoch.admin.domain.Grade;
import com.kkoch.admin.domain.stats.Price;
import com.kkoch.admin.domain.stats.Stats;
import com.kkoch.admin.domain.stats.repository.StatsRepository;
import com.kkoch.admin.domain.stats.repository.dto.StatsSearchCond;
import com.kkoch.admin.domain.stats.repository.response.StatsResponse;
import com.kkoch.admin.domain.variety.PlantCategory;
import com.kkoch.admin.domain.variety.Variety;
import com.kkoch.admin.domain.variety.repository.VarietyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class StatsQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private StatsQueryService statsQueryService;

    @Autowired
    private StatsRepository statsRepository;

    @Autowired
    private VarietyRepository varietyRepository;

    @DisplayName("검색 조건과 현재 날짜를 입력 받아 통계 목록을 조회한다.")
    @Test
    void searchStats() {
        //given
        LocalDate currentDate = LocalDate.of(2024, 7, 12);

        Variety variety = createVariety();
        Stats stats1 = createStats(LocalDate.of(2024, 7, 6), Grade.SUPER);
        Stats stats2 = createStats(LocalDate.of(2024, 7, 7), Grade.ADVANCED);
        Stats stats3 = createStats(LocalDate.of(2024, 7, 12), Grade.NORMAL);

        StatsSearchCond cond = StatsSearchCond.builder()
            .varietyCode("10000001")
            .period(7)
            .build();

        //when
        ListResponse<StatsResponse> response = statsQueryService.searchStats(cond, currentDate);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("size", 3);
        assertThat(response.getContent()).hasSize(3)
            .extracting("grade")
            .containsExactly(
                Grade.NORMAL,
                Grade.ADVANCED,
                Grade.SUPER
            );
    }

    private Stats createStats(LocalDate statsDate, Grade grade) {
        Stats stats = Stats.builder()
            .isDeleted(false)
            .grade(grade)
            .plantCount(10)
            .price(Price.builder()
                .avg(1)
                .max(1)
                .min(1)
                .build())
            .statsDate(statsDate)
            .varietyCode("10000001")
            .build();
        return statsRepository.save(stats);
    }

    private Variety createVariety() {
        Variety variety = Variety.builder()
            .code("10000001")
            .plantCategory(PlantCategory.CUT_FLOWERS)
            .itemName("장미")
            .varietyName("하트앤소울")
            .build();
        return varietyRepository.save(variety);
    }
}