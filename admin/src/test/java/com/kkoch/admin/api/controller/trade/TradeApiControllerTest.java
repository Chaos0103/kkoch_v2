package com.kkoch.admin.api.controller.trade;

import com.kkoch.admin.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TradeApiControllerTest extends ControllerTestSupport {

    @DisplayName("거래 내역 목록 조회시 페이지 번호는 양수이다.")
    @Test
    void searchTradesIsZeroPage() throws Exception {
        mockMvc.perform(
                get("/admin-service/trades/{memberKey}", UUID.randomUUID().toString())
                    .queryParam("page", "0")
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("페이지 번호를 올바르게 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("거래 내역 목록을 조회한다.")
    @Test
    void searchTrades() throws Exception {
        mockMvc.perform(
                get("/admin-service/trades/{memberKey}", UUID.randomUUID().toString())
                    .queryParam("page", "1")
            )
            .andExpect(status().isOk());
    }

    @DisplayName("거래 내역을 상세 조회한다.")
    @Test
    void searchTrade() throws Exception {
        mockMvc.perform(
                get("/admin-service/trades/{tradeId}/results", 1L)
            )
            .andExpect(status().isOk());
    }
}