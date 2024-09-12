package com.ssafy.auction_service.api.controller.auctionschedule;

import com.ssafy.auction_service.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuctionScheduleApiQueryControllerTest extends ControllerTestSupport {

    @DisplayName("경매 일정 목록 조회시 화훼부류는 필수값이 아니다.")
    @NullAndEmptySource
    @ParameterizedTest
    void searchAuctionSchedulesWithoutPlantCategory(String plantCategory) throws Exception {
        mockMvc.perform(
                get("/auction-service/auction-schedules")
                    .queryParam("plantCategory", plantCategory)
                    .queryParam("jointMarket", "YANGJAE")
            )
            .andExpect(status().isOk());
    }

    @DisplayName("경매 일정 목록 조회시 공판장은 필수값이 아니다.")
    @NullAndEmptySource
    @ParameterizedTest
    void searchAuctionSchedulesWithoutJointMarket(String jointMarket) throws Exception {
        mockMvc.perform(
                get("/auction-service/auction-schedules")
                    .queryParam("plantCategory", "CUT_FLOWERS")
                    .queryParam("jointMarket", jointMarket)
            )
            .andExpect(status().isOk());
    }

    @DisplayName("경매 일정 목록을 조회한다.")
    @Test
    void searchAuctionSchedules() throws Exception {
        mockMvc.perform(
                get("/auction-service/auction-schedules")
                    .queryParam("plantCategory", "CUT_FLOWERS")
                    .queryParam("jointMarket", "YANGJAE")
            )
            .andExpect(status().isOk());
    }
}