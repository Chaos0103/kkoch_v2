package com.kkoch.admin.api.controller.bidresult;

import com.kkoch.admin.ControllerTestSupport;
import com.kkoch.admin.api.controller.bidresult.request.BidResultCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BidResultApiControllerTest extends ControllerTestSupport {

    @DisplayName("낙찰 결과 신규 등록시 경매품종 ID는 양수이다.")
    @Test
    void createBidResultIsZeroAuctionVarietyId() throws Exception {
        BidResultCreateRequest request = BidResultCreateRequest.builder()
            .auctionVarietyId(0)
            .bidPrice(3500)
            .bidDateTime(LocalDateTime.of(2024, 7, 12, 5, 10))
            .build();

        mockMvc.perform(
                post("/admin-service/bid-results/{memberKey}", UUID.randomUUID().toString())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("경매품종 ID를 올바르게 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("낙찰 결과 신규 등록시 낙찰가는 양수이다.")
    @Test
    void createBidResultIsZeroBidPride() throws Exception {
        BidResultCreateRequest request = BidResultCreateRequest.builder()
            .auctionVarietyId(1)
            .bidPrice(0)
            .bidDateTime(LocalDateTime.of(2024, 7, 12, 5, 10))
            .build();

        mockMvc.perform(
                post("/admin-service/bid-results/{memberKey}", UUID.randomUUID().toString())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("낙찰가를 올바르게 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("낙찰 결과 신규 등록시 낙찰일시는 필수값이다.")
    @Test
    void createBidResultWithoutBidDateTime() throws Exception {
        BidResultCreateRequest request = BidResultCreateRequest.builder()
            .auctionVarietyId(1)
            .bidPrice(3500)
            .build();

        mockMvc.perform(
                post("/admin-service/bid-results/{memberKey}", UUID.randomUUID().toString())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("낙찰일시를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("낙찰 결과 신규 등록한다.")
    @Test
    void createBidResult() throws Exception {
        BidResultCreateRequest request = BidResultCreateRequest.builder()
            .auctionVarietyId(1)
            .bidPrice(3500)
            .bidDateTime(LocalDateTime.of(2024, 7, 12, 5, 10))
            .build();

        mockMvc.perform(
                post("/admin-service/bid-results/{memberKey}", UUID.randomUUID().toString())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated());
    }

    @DisplayName("낙찰 결과 목록 조회시 페이지 번호는 양수이다.")
    @Test
    void searchBidResultsIsZero() throws Exception {
        mockMvc.perform(
                get("/admin-service/bid-results")
                    .queryParam("page", "0")
                    .queryParam("startDate", "2024-07-12")
                    .queryParam("endDate", "2024-07-13")
                    .queryParam("plantCategory", "CUT_FLOWERS")
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("페이지 번호을 올바르게 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("낙찰 결과 목록 조회시 화훼부류는 필수값이다.")
    @Test
    void searchBidResultsWithoutPlantCategory() throws Exception {
        mockMvc.perform(
                get("/admin-service/bid-results")
                    .queryParam("page", "1")
                    .queryParam("startDate", "2024-07-12")
                    .queryParam("endDate", "2024-07-13")
                    .queryParam("plantCategory", " ")
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("화훼부류를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("낙찰 결과 목록을 조회한다.")
    @Test
    void searchBidResults() throws Exception {
        mockMvc.perform(
                get("/admin-service/bid-results")
                    .queryParam("page", "1")
                    .queryParam("startDate", "2024-07-12")
                    .queryParam("endDate", "2024-07-13")
                    .queryParam("plantCategory", "CUT_FLOWERS")
            )
            .andExpect(status().isOk());
    }
}