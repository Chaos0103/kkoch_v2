package com.ssafy.auction_service.api.controller.variety;

import com.ssafy.auction_service.ControllerTestSupport;
import com.ssafy.auction_service.api.controller.variety.request.VarietyCreateRequest;
import com.ssafy.auction_service.api.controller.variety.request.VarietyModifyRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VarietyApiControllerTest extends ControllerTestSupport {

    @DisplayName("품종 등록시 화훼부류는 필수값이다.")
    @NullAndEmptySource
    @ParameterizedTest
    void createVarietyWithoutCategory(String category) throws Exception {
        VarietyCreateRequest request = VarietyCreateRequest.builder()
            .category(category)
            .itemName("장미")
            .varietyName("하트앤소울")
            .build();

        mockMvc.perform(
                post("/auction-service/varieties")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("화훼부류를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("품종 등록시 품목명은 필수값이다.")
    @NullAndEmptySource
    @ParameterizedTest
    void createVarietyWithoutItemName(String itemName) throws Exception {
        VarietyCreateRequest request = VarietyCreateRequest.builder()
            .category("CUT_FLOWERS")
            .itemName(itemName)
            .varietyName("하트앤소울")
            .build();

        mockMvc.perform(
                post("/auction-service/varieties")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("품목명을 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("품종 등록시 품종명은 필수값이다.")
    @NullAndEmptySource
    @ParameterizedTest
    void createVarietyWithoutVarietyName(String varietyName) throws Exception {
        VarietyCreateRequest request = VarietyCreateRequest.builder()
            .category("CUT_FLOWERS")
            .itemName("장미")
            .varietyName(varietyName)
            .build();

        mockMvc.perform(
                post("/auction-service/varieties")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("품종명을 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("품종을 등록한다.")
    @Test
    void createVariety() throws Exception {
        VarietyCreateRequest request = VarietyCreateRequest.builder()
            .category("CUT_FLOWERS")
            .itemName("장미")
            .varietyName("하트앤소울")
            .build();

        mockMvc.perform(
                post("/auction-service/varieties")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated());
    }

    @DisplayName("품종 수정시 품종명은 필수값이다.")
    @NullAndEmptySource
    @ParameterizedTest
    void modifyVarietyWithoutVarietyName(String varietyName) throws Exception {
        VarietyModifyRequest request = VarietyModifyRequest.builder()
            .varietyName(varietyName)
            .build();

        mockMvc.perform(
                patch("/auction-service/varieties/{varietyCode}", "10031204")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("품종명을 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("품종을 수정한다.")
    @Test
    void modifyVariety() throws Exception {
        VarietyModifyRequest request = VarietyModifyRequest.builder()
            .varietyName("하젤")
            .build();

        mockMvc.perform(
                patch("/auction-service/varieties/{varietyCode}", "10031204")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }
}