package com.kkoch.admin.api.controller;

import com.kkoch.admin.ControllerTestSupport;
import com.kkoch.admin.api.controller.request.VarietyCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VarietyApiControllerTest extends ControllerTestSupport {

    @DisplayName("품종을 신규 등록시 모든 데이터는 필수값이다.")
    @CsvSource({
        ",장미,하트앤소울,화훼부류를 입력해주세요.",
        "CUT_FLOWERS,,하트앤소울,품목명을 입력해주세요.",
        "CUT_FLOWERS,장미,,품종명을 입력해주세요."
    })
    @ParameterizedTest
    void createVarietyWithoutValue(String plantCategory, String itemName, String varietyName, String message) throws Exception {
        VarietyCreateRequest request = VarietyCreateRequest.builder()
            .plantCategory(plantCategory)
            .itemName(itemName)
            .varietyName(varietyName)
            .build();

        mockMvc.perform(
                post("/admin-service/varieties")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value(message))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("품종을 신규 등록한다.")
    @Test
    void createVariety() throws Exception {
        VarietyCreateRequest request = VarietyCreateRequest.builder()
            .plantCategory("CUT_FLOWERS")
            .itemName("장미")
            .varietyName("하트앤소울")
            .build();

        mockMvc.perform(
                post("/admin-service/varieties")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated());
    }

    @DisplayName("품목명 목록을 조회한다.")
    @Test
    void searchItemNames() throws Exception {
        mockMvc.perform(
                get("/admin-service/varieties/items")
                    .queryParam("category", "CUT_FLOWERS")
            )
            .andExpect(status().isOk());
    }

    @DisplayName("품종명 목록을 조회한다.")
    @Test
    void searchVarietyNames() throws Exception {
        mockMvc.perform(
                get("/admin-service/varieties/names")
                    .queryParam("item", "장미")
            )
            .andExpect(status().isOk());
    }
}