package com.ssafy.trade_service.api.service.auctionstatistics;

import com.ssafy.trade_service.IntegrationTestSupport;
import com.ssafy.trade_service.api.ListResponse;
import com.ssafy.trade_service.domain.auctionstatistics.AuctionStatistics;
import com.ssafy.trade_service.domain.auctionstatistics.PriceCalculatedResult;
import com.ssafy.trade_service.domain.auctionstatistics.repository.AuctionStatisticsRepository;
import com.ssafy.trade_service.domain.auctionstatistics.repository.cond.AuctionStatisticsSearchCond;
import com.ssafy.trade_service.domain.auctionstatistics.repository.response.AuctionStatisticsResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class AuctionStatisticsQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private AuctionStatisticsQueryService auctionStatisticsQueryService;

    @Autowired
    private AuctionStatisticsRepository auctionStatisticsRepository;

    @DisplayName("품종코드와 검색 조건을 입력 받아 경매 통계 목록을 조회한다.")
    @Test
    void searchAuctionStatistics() {
        //given
        createAuctionStatistics("10031204", "SUPER", LocalDate.of(2024, 8, 1));
        createAuctionStatistics("10031204", "SUPER", LocalDate.of(2024, 8, 2));
        createAuctionStatistics("10031204", "ADVANCED", LocalDate.of(2024, 8, 2));
        createAuctionStatistics("10031204", "NORMAL", LocalDate.of(2024, 8, 2));
        createAuctionStatistics("10031205", "SUPER", LocalDate.of(2024, 8, 2));
        createAuctionStatistics("10031204", "SUPER", LocalDate.of(2024, 8, 3));

        AuctionStatisticsSearchCond cond = AuctionStatisticsSearchCond.builder()
            .from(LocalDate.of(2024, 8, 2))
            .to(LocalDate.of(2024, 8, 2))
            .plantGrade("SUPER")
            .build();

        //when
        ListResponse<AuctionStatisticsResponse> response = auctionStatisticsQueryService.searchAuctionStatistics("10031204", cond);

        //then
        assertThat(response.getContent()).hasSize(1)
            .extracting("varietyCode", "plantGrade", "calculatedDate")
            .containsExactly(
                tuple("10031204", "SUPER", LocalDate.of(2024, 8, 2))
            );
    }

    private AuctionStatistics createAuctionStatistics(String varietyCode, String plantGrade, LocalDate calculatedDate) {
        AuctionStatistics auctionStatistics = AuctionStatistics.builder()
            .isDeleted(false)
            .varietyCode(varietyCode)
            .plantGrade(plantGrade)
            .plantCount(100)
            .calculatedDate(calculatedDate)
            .calculatedResult(PriceCalculatedResult.builder()
                .avg(3000)
                .max(3000)
                .min(3000)
                .build())
            .build();
        return auctionStatisticsRepository.save(auctionStatistics);
    }
}