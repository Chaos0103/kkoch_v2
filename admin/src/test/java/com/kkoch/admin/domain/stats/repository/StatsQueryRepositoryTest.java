package com.kkoch.admin.domain.stats.repository;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.domain.Grade;
import com.kkoch.admin.domain.stats.Price;
import com.kkoch.admin.domain.stats.Stats;
import com.kkoch.admin.domain.stats.repository.dto.StatsSearchCond;
import com.kkoch.admin.domain.stats.repository.response.StatsResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class StatsQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private StatsQueryRepository statsQueryRepository;

    @Autowired
    private StatsRepository statsRepository;

    @DisplayName("검색 조건을 입력받아 통계 목록을 조회한다.")
    @Test
    void findAllByCond() {
        //given
        LocalDate currentDate = LocalDate.of(2024, 7, 12);

        Stats stats1 = createStats("10000001", LocalDate.of(2024, 7, 5), Grade.SUPER, false);
        Stats stats2 = createStats("10000001", LocalDate.of(2024, 7, 6), Grade.SUPER, false);
        Stats stats3 = createStats("10000001", LocalDate.of(2024, 7, 7), Grade.ADVANCED, false);
        Stats stats4 = createStats("10000001", LocalDate.of(2024, 7, 12), Grade.NORMAL, false);
        Stats stats5 = createStats("10000001", LocalDate.of(2024, 7, 12), Grade.SUPER, true);
        Stats stats6 = createStats("10000002", LocalDate.of(2024, 7, 12), Grade.SUPER, false);

        StatsSearchCond cond = StatsSearchCond.builder()
            .varietyCode("10000001")
            .period(7)
            .build();
        //when
        List<StatsResponse> content = statsQueryRepository.findAllByCond(cond, currentDate, "장미", "하트앤소울");

        //then
        assertThat(content).hasSize(3)
            .extracting("grade")
            .containsExactly(
                Grade.NORMAL,
                Grade.ADVANCED,
                Grade.SUPER
            );
    }

    private Stats createStats(String varietyCode, LocalDate statsDate, Grade grade, boolean isDeleted) {
        Stats stats = Stats.builder()
            .isDeleted(isDeleted)
            .grade(grade)
            .plantCount(10)
            .price(Price.builder()
                .avg(1)
                .max(1)
                .min(1)
                .build())
            .statsDate(statsDate)
            .varietyCode(varietyCode)
            .build();
        return statsRepository.save(stats);
    }
}