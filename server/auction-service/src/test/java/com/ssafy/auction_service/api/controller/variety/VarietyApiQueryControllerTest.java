package com.ssafy.auction_service.api.controller.variety;

import com.ssafy.auction_service.ControllerTestSupport;
import com.ssafy.auction_service.api.controller.variety.param.VarietySearchParam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VarietyApiQueryControllerTest extends ControllerTestSupport {

    @DisplayName("품종 목록 조회시 페이지 번호가 양수가 아니라면 기본값으로 조회한다.")
    @ValueSource(strings = {"0", "-1", "a"})
    @NullAndEmptySource
    @ParameterizedTest
    void searchVarietiesIsNotPositivePage(String page) throws Exception {
        VarietySearchParam param = VarietySearchParam.builder()
            .page(page)
            .plantCategory("CUT_FLOWERS")
            .itemName("장미")
            .build();

        mockMvc.perform(
                get("/auction-service/varieties")
                    .queryParam("page", param.getPage())
                    .queryParam("plantCategory", param.getPlantCategory())
                    .queryParam("itemName", param.getItemName())
            )
            .andExpect(status().isOk());
    }

    @DisplayName("품종 목록 조회시 화훼부류는 필수값이다.")
    @NullAndEmptySource
    @ParameterizedTest
    void searchVarietiesWithoutPlantCategory(String plantCategory) throws Exception {
        VarietySearchParam param = VarietySearchParam.builder()
            .page("1")
            .plantCategory(plantCategory)
            .itemName("장미")
            .build();

        mockMvc.perform(
                get("/auction-service/varieties")
                    .queryParam("page", param.getPage())
                    .queryParam("plantCategory", param.getPlantCategory())
                    .queryParam("itemName", param.getItemName())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("화훼부류를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("품종 목록 조회시 품목명은 필수값이 아니다.")
    @NullAndEmptySource
    @ParameterizedTest
    void searchVarietiesWithoutItemName(String itemName) throws Exception {
        VarietySearchParam param = VarietySearchParam.builder()
            .page("1")
            .plantCategory("CUT_FLOWERS")
            .itemName(itemName)
            .build();

        mockMvc.perform(
                get("/auction-service/varieties")
                    .queryParam("page", param.getPage())
                    .queryParam("plantCategory", param.getPlantCategory())
                    .queryParam("itemName", param.getItemName())
            )
            .andExpect(status().isOk());
    }

    @DisplayName("품종 목록을 조회한다.")
    @Test
    void searchVarieties() throws Exception {
        VarietySearchParam param = VarietySearchParam.builder()
            .page("1")
            .plantCategory("CUT_FLOWERS")
            .itemName("장미")
            .build();

        mockMvc.perform(
                get("/auction-service/varieties")
                    .queryParam("page", param.getPage())
                    .queryParam("plantCategory", param.getPlantCategory())
                    .queryParam("itemName", param.getItemName())
            )
            .andExpect(status().isOk());
    }
}