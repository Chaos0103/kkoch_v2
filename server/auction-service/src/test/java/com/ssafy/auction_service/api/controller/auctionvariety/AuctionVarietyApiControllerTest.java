package com.ssafy.auction_service.api.controller.auctionvariety;

import com.ssafy.auction_service.ControllerTestSupport;
import com.ssafy.auction_service.api.controller.auctionvariety.request.AuctionVarietyCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuctionVarietyApiControllerTest extends ControllerTestSupport {

    @DisplayName("경매 품종 등록시 화훼등급은 필수값이다.")
    @NullAndEmptySource
    @ParameterizedTest
    void createAuctionVarietyWithoutPlantGrade(String plantGrade) throws Exception {
        AuctionVarietyCreateRequest request = AuctionVarietyCreateRequest.builder()
            .plantGrade(plantGrade)
            .plantCount("10")
            .auctionStartPrice("4500")
            .region("광주")
            .shipper("김출하")
            .build();

        mockMvc.perform(
                post("/auction-service/auction-variety")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("화훼등급을 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("경매 품종 등록시 화훼단수는 필수값이다.")
    @NullAndEmptySource
    @ParameterizedTest
    void createAuctionVarietyWithoutPlantCount(String plantCount) throws Exception {
        AuctionVarietyCreateRequest request = AuctionVarietyCreateRequest.builder()
            .plantGrade("SUPER")
            .plantCount(plantCount)
            .auctionStartPrice("4500")
            .region("광주")
            .shipper("김출하")
            .build();

        mockMvc.perform(
                post("/auction-service/auction-variety")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("화훼단수를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("경매 품종 등록시 경매 시작가는 필수값이다.")
    @NullAndEmptySource
    @ParameterizedTest
    void createAuctionVarietyWithoutAuctionStartPrice(String auctionStartPrice) throws Exception {
        AuctionVarietyCreateRequest request = AuctionVarietyCreateRequest.builder()
            .plantGrade("SUPER")
            .plantCount("10")
            .auctionStartPrice(auctionStartPrice)
            .region("광주")
            .shipper("김출하")
            .build();

        mockMvc.perform(
                post("/auction-service/auction-variety")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("경매 시작가를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("경매 품종 등록시 출하 지역는 필수값이다.")
    @NullAndEmptySource
    @ParameterizedTest
    void createAuctionVarietyWithoutRegion(String region) throws Exception {
        AuctionVarietyCreateRequest request = AuctionVarietyCreateRequest.builder()
            .plantGrade("SUPER")
            .plantCount("10")
            .auctionStartPrice("4500")
            .region(region)
            .shipper("김출하")
            .build();

        mockMvc.perform(
                post("/auction-service/auction-variety")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("출하 지역을 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("경매 품종 등록시 출하자는 필수값이다.")
    @NullAndEmptySource
    @ParameterizedTest
    void createAuctionVarietyWithoutShipper(String region) throws Exception {
        AuctionVarietyCreateRequest request = AuctionVarietyCreateRequest.builder()
            .plantGrade("SUPER")
            .plantCount("10")
            .auctionStartPrice("4500")
            .region("광주")
            .shipper(region)
            .build();

        mockMvc.perform(
                post("/auction-service/auction-variety")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("출하자을 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("경매 품종을 등록한다.")
    @NullAndEmptySource
    @ParameterizedTest
    void createAuctionVariety(String region) throws Exception {
        AuctionVarietyCreateRequest request = AuctionVarietyCreateRequest.builder()
            .plantGrade("SUPER")
            .plantCount("10")
            .auctionStartPrice("4500")
            .region("광주")
            .shipper("김출하")
            .build();

        mockMvc.perform(
                post("/auction-service/auction-variety")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated());
    }
}