package com.kkoch.admin.api.controller.auctionvariety;

import com.kkoch.admin.ControllerTestSupport;
import com.kkoch.admin.api.controller.auctionvariety.request.AuctionVarietyCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuctionVarietyApiControllerTest extends ControllerTestSupport {

    @DisplayName("경매 품종을 신규 등록시 모든 데이터는 필수값이다.")
    @CsvSource({
        ",1,SUPER,10,4500,광주,김판매,품종코드를 입력해주세요.",
        "10000001,0,SUPER,10,4500,광주,김판매,경매 일정을 올바르게 입력해주세요.",
        "10000001,1,,10,4500,광주,김판매,품종 등급을 입력해주세요.",
        "10000001,1,SUPER,0,4500,광주,김판매,단수를 올바르게 입력해주세요.",
        "10000001,1,SUPER,10,0,광주,김판매,경매 시작가를 올바르게 입력해주세요.",
        "10000001,1,SUPER,10,4500,,김판매,지역을 입력해주세요.",
        "10000001,1,SUPER,10,4500,광주,,출하자를 입력해주세요.",
    })
    @ParameterizedTest
    void createAuctionVarietyWithoutValue(String varietyCode, int auctionScheduleId, String grade, int plantCount, int startPrice, String region, String shipper, String message) throws Exception {
        AuctionVarietyCreateRequest request = AuctionVarietyCreateRequest.builder()
            .varietyCode(varietyCode)
            .auctionScheduleId(auctionScheduleId)
            .grade(grade)
            .plantCount(plantCount)
            .startPrice(startPrice)
            .region(region)
            .shipper(shipper)
            .build();
        mockMvc.perform(
                post("/admin-service/auction-varieties")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value(message))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("경매 품종을 신규 등록한다.")
    @Test
    void createAuctionVariety() throws Exception {
        AuctionVarietyCreateRequest request = AuctionVarietyCreateRequest.builder()
            .varietyCode("10000001")
            .auctionScheduleId(1)
            .grade("SUPER")
            .plantCount(10)
            .startPrice(4500)
            .region("광주")
            .shipper("김판매")
            .build();

        mockMvc.perform(
                post("/admin-service/auction-varieties")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated());
    }

    @DisplayName("경매 품종 목록을 조회한다.")
    @Test
    void searchAuctionVarieties() throws Exception {
        mockMvc.perform(
                get("/admin-service/auction-varieties")
                    .queryParam("auctionScheduleId", "1")
            )
            .andExpect(status().isOk());
    }
}