package com.kkoch.user.api.controller.pointlog;

import com.kkoch.user.ControllerTestSupport;
import com.kkoch.user.api.controller.pointlog.param.PointLogSearchParam;
import com.kkoch.user.api.controller.pointlog.request.PointLogCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PointLogControllerTest extends ControllerTestSupport {

    @DisplayName("신규 포인트 내역 등록시 유효성 검사를 한다.")
    @CsvSource({
        ",10000,CHARGE,은행을 입력해주세요.",
        "SHINHAN,0,CHARGE,금액을 올바르게 입력해주세요.",
        "SHINHAN,10000,,상태를 입력해주세요."
    })
    @ParameterizedTest
    void createPointLogWithoutValue(String bank, int amount, String status, String message) throws Exception {
        PointLogCreateRequest request = PointLogCreateRequest.builder()
            .bank(bank)
            .amount(amount)
            .status(status)
            .build();

        mockMvc.perform(
                post("/{memberKey}/points", generateMemberKey())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value(message))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 포인트 내역을 등록한다.")
    @Test
    void createPointLog() throws Exception {
        PointLogCreateRequest request = PointLogCreateRequest.builder()
            .bank("SHINHAN")
            .amount(100000)
            .status("CHARGE")
            .build();

        mockMvc.perform(
                post("/{memberKey}/points", generateMemberKey())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isOk());
    }

    @DisplayName("포인트 내역 목록 조회시 페이지 번호는 양수이다.")
    @Test
    void searchPointLogsIsZero() throws Exception {
        mockMvc.perform(
                get("/{memberKey}/points", generateMemberKey())
                    .queryParam("page", "0")
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("페이지 번호를 올바르게 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("포인트 내역 목록을 조회한다.")
    @Test
    void searchPointLogs() throws Exception {
        mockMvc.perform(
                get("/{memberKey}/points", generateMemberKey())
                    .queryParam("page", "1")
                    .with(csrf())
            )
            .andExpect(status().isOk());
    }
}