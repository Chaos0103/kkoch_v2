package com.ssafy.auction_service.api.controller.auctionvariety;

import com.ssafy.auction_service.ControllerTestSupport;
import com.ssafy.auction_service.api.controller.auctionvariety.request.AuctionVarietyCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuctionVarietyApiControllerTest extends ControllerTestSupport {

    @DisplayName("경매 품종 등록시 품종 코드는 필수값이다.")
    @NullAndEmptySource
    @ParameterizedTest
    void createAuctionVarietyWithoutVarietyCode(String varietyCode) throws Exception {
        AuctionVarietyCreateRequest request = AuctionVarietyCreateRequest.builder()
            .varietyCode(varietyCode)
            .auctionScheduleId(1)
            .plantGrade("SUPER")
            .plantCount(10)
            .auctionStartPrice(4500)
            .region("광주")
            .shipper("김출하")
            .build();

        mockMvc.perform(
                post("/auction-service/auction-varieties")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("품종 코드를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("경매 품종 등록시 경매 일정 ID는 필수값이다.")
    @Test
    void createAuctionVarietyWithoutAuctionScheduleId() throws Exception {
        AuctionVarietyCreateRequest request = AuctionVarietyCreateRequest.builder()
            .varietyCode("10000001")
            .auctionScheduleId(null)
            .plantGrade("SUPER")
            .plantCount(10)
            .auctionStartPrice(4500)
            .region("광주")
            .shipper("김출하")
            .build();

        mockMvc.perform(
                post("/auction-service/auction-varieties")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("경매 일정 ID를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("경매 품종 등록시 화훼등급은 필수값이다.")
    @NullAndEmptySource
    @ParameterizedTest
    void createAuctionVarietyWithoutPlantGrade(String plantGrade) throws Exception {
        AuctionVarietyCreateRequest request = AuctionVarietyCreateRequest.builder()
            .varietyCode("10000001")
            .auctionScheduleId(1)
            .plantGrade(plantGrade)
            .plantCount(10)
            .auctionStartPrice(4500)
            .region("광주")
            .shipper("김출하")
            .build();

        mockMvc.perform(
                post("/auction-service/auction-varieties")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("화훼등급을 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("경매 품종 등록시 화훼단수는 양수값이다.")
    @Test
    void createAuctionVarietyWithoutPlantCount() throws Exception {
        AuctionVarietyCreateRequest request = AuctionVarietyCreateRequest.builder()
            .varietyCode("10000001")
            .auctionScheduleId(1)
            .plantGrade("SUPER")
            .plantCount(0)
            .auctionStartPrice(4500)
            .region("광주")
            .shipper("김출하")
            .build();

        mockMvc.perform(
                post("/auction-service/auction-varieties")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("화훼단수를 올바르게 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("경매 품종 등록시 경매 시작가는 양수값이다.")
    @Test
    void createAuctionVarietyWithoutAuctionStartPrice() throws Exception {
        AuctionVarietyCreateRequest request = AuctionVarietyCreateRequest.builder()
            .varietyCode("10000001")
            .auctionScheduleId(1)
            .plantGrade("SUPER")
            .plantCount(10)
            .auctionStartPrice(0)
            .region("광주")
            .shipper("김출하")
            .build();

        mockMvc.perform(
                post("/auction-service/auction-varieties")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("경매 시작가를 올바르게 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("경매 품종 등록시 출하 지역는 필수값이다.")
    @NullAndEmptySource
    @ParameterizedTest
    void createAuctionVarietyWithoutRegion(String region) throws Exception {
        AuctionVarietyCreateRequest request = AuctionVarietyCreateRequest.builder()
            .varietyCode("10000001")
            .auctionScheduleId(1)
            .plantGrade("SUPER")
            .plantCount(10)
            .auctionStartPrice(4500)
            .region(region)
            .shipper("김출하")
            .build();

        mockMvc.perform(
                post("/auction-service/auction-varieties")
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
            .varietyCode("10000001")
            .auctionScheduleId(1)
            .plantGrade("SUPER")
            .plantCount(10)
            .auctionStartPrice(4500)
            .region("광주")
            .shipper(region)
            .build();

        mockMvc.perform(
                post("/auction-service/auction-varieties")
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
    @Test
    void createAuctionVariety() throws Exception {
        AuctionVarietyCreateRequest request = AuctionVarietyCreateRequest.builder()
            .varietyCode("10000001")
            .auctionScheduleId(1)
            .plantGrade("SUPER")
            .plantCount(10)
            .auctionStartPrice(4500)
            .region("광주")
            .shipper("김출하")
            .build();

        mockMvc.perform(
                post("/auction-service/auction-varieties")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated());
    }
}