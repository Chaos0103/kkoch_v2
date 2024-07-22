package com.kkoch.admin.api.controller.stats;

import com.kkoch.admin.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StatsApiControllerTest extends ControllerTestSupport {

    @DisplayName("통계 목록 조회시 품종코드는 필수값이다.")
    @Test
    void searchStatsWithoutVarietyCode() throws Exception {
        mockMvc.perform(
                get("/admin-service/stats")
                    .queryParam("varietyCode", "")
                    .queryParam("period", "1")
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("품좀코드를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("통계 목록 조회시 조회 기간은 양수이다.")
    @Test
    void searchStatsIsZero() throws Exception {
        mockMvc.perform(
                get("/admin-service/stats")
                    .queryParam("varietyCode", "10000001")
                    .queryParam("period", "0")
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("조회 기간을 올바르게 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("통계 목록을 조회한다.")
    @Test
    void searchStats() throws Exception {
        mockMvc.perform(
                get("/admin-service/stats")
                    .queryParam("varietyCode", "10000001")
                    .queryParam("period", "1")
            )
            .andExpect(status().isOk());
    }
}