package com.ssafy.auction_service.api.controller.auctionvariety;

import com.ssafy.auction_service.ControllerTestSupport;
import com.ssafy.auction_service.api.controller.auctionvariety.param.AuctionVarietySearchParam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuctionVarietyApiQueryControllerTest extends ControllerTestSupport {

    @DisplayName("경매 품종 목록 조회시 페이지 번호가 양수가 아니라면 기본값으로 조회한다.")
    @ValueSource(strings = {"0", "-1", "a"})
    @NullAndEmptySource
    @ParameterizedTest
    void searchAuctionVarietiesIsNotPositivePageNumber(String page) throws Exception {
        AuctionVarietySearchParam param = AuctionVarietySearchParam.builder()
            .page(page)
            .build();

        mockMvc.perform(
                get("/auction-service/auction-schedules/{auctionScheduleId}/auction-varieties", 1)
                    .queryParam("page", param.getPage())
            )
            .andExpect(status().isOk());
    }

    @DisplayName("경매 품종 목록을 조회한다.")
    @Test
    void searchAuctionVarieties() throws Exception {
        AuctionVarietySearchParam param = AuctionVarietySearchParam.builder()
            .page("1")
            .build();

        mockMvc.perform(
                get("/auction-service/auction-schedules/{auctionScheduleId}/auction-varieties", 1)
                    .queryParam("page", param.getPage())
            )
            .andExpect(status().isOk());
    }
}