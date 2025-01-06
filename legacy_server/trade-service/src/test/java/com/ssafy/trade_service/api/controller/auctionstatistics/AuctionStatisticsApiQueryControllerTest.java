package com.ssafy.trade_service.api.controller.auctionstatistics;

import com.ssafy.trade_service.ControllerTestSupport;
import com.ssafy.trade_service.api.controller.auctionstatistics.param.AuctionStatisticsParam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.time.LocalDate;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuctionStatisticsApiQueryControllerTest extends ControllerTestSupport {

    @DisplayName("품종의 경매 통계 목록 조회시 조회 시작일은 필수값이다.")
    @Test
    void searchAuctionStatisticsWithoutFrom() throws Exception {
        AuctionStatisticsParam param = AuctionStatisticsParam.builder()
            .from(null)
            .to(LocalDate.of(2024, 8, 31))
            .plantGrade("SUPER")
            .build();

        mockMvc.perform(
                get("/trade-service/auction-statistics/{varietyCode}", "10031204")
                    .queryParam("to", param.getTo().toString())
                    .queryParam("plantGrade", param.getPlantGrade())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("조회 시작일을 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("품종의 경매 통계 목록 조회시 조회 종료일은 필수값이다.")
    @Test
    void searchAuctionStatisticsWithoutTo() throws Exception {
        AuctionStatisticsParam param = AuctionStatisticsParam.builder()
            .from(LocalDate.of(2024, 8, 1))
            .to(null)
            .plantGrade("SUPER")
            .build();

        mockMvc.perform(
                get("/trade-service/auction-statistics/{varietyCode}", "10031204")
                    .queryParam("from", param.getFrom().toString())
                    .queryParam("plantGrade", param.getPlantGrade())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("조회 종료일을 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("품종의 경매 통계 목록 조회시 화훼등급은 필수값이 아니다.")
    @NullAndEmptySource
    @ParameterizedTest
    void searchAuctionStatisticsWithoutPlantGrade(String plantGrade) throws Exception {
        AuctionStatisticsParam param = AuctionStatisticsParam.builder()
            .from(LocalDate.of(2024, 8, 1))
            .to(LocalDate.of(2024, 8, 31))
            .plantGrade(plantGrade)
            .build();

        mockMvc.perform(
                get("/trade-service/auction-statistics/{varietyCode}", "10031204")
                    .queryParam("from", param.getFrom().toString())
                    .queryParam("to", param.getTo().toString())
                    .queryParam("plantGrade", param.getPlantGrade())
            )
            .andExpect(status().isOk());
    }

    @DisplayName("원하는 품종의 경매 통계 목록을 조회한다.")
    @Test
    void searchAuctionStatistics() throws Exception {
        AuctionStatisticsParam param = AuctionStatisticsParam.builder()
            .from(LocalDate.of(2024, 8, 1))
            .to(LocalDate.of(2024, 8, 31))
            .plantGrade("SUPER")
            .build();

        mockMvc.perform(
                get("/trade-service/auction-statistics/{varietyCode}", "10031204")
                    .queryParam("from", param.getFrom().toString())
                    .queryParam("to", param.getTo().toString())
                    .queryParam("plantGrade", param.getPlantGrade())
            )
            .andExpect(status().isOk());
    }
}