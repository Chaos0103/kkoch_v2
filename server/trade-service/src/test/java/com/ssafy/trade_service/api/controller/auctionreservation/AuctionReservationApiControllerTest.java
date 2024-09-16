package com.ssafy.trade_service.api.controller.auctionreservation;

import com.ssafy.trade_service.ControllerTestSupport;
import com.ssafy.trade_service.api.controller.auctionreservation.request.AuctionReservationCreateRequest;
import com.ssafy.trade_service.api.controller.auctionreservation.request.AuctionReservationModifyRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuctionReservationApiControllerTest extends ControllerTestSupport {

    @DisplayName("경매 예약 등록시 품종코드는 필수값이다.")
    @NullAndEmptySource
    @ParameterizedTest
    void createAuctionReservationWithoutVarietyCode(String varietyCode) throws Exception {
        AuctionReservationCreateRequest request = AuctionReservationCreateRequest.builder()
            .varietyCode(varietyCode)
            .plantGrade("SUPER")
            .plantCount(10)
            .desiredPrice(3000)
            .build();

        mockMvc.perform(
                post("/trade-service/auction-schedules/{auctionScheduleId}/auction-reservations", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("품종코드를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("경매 예약 등록시 화훼등급은 필수값이다.")
    @NullAndEmptySource
    @ParameterizedTest
    void createAuctionReservationWithoutPlantGrade(String plantGrade) throws Exception {
        AuctionReservationCreateRequest request = AuctionReservationCreateRequest.builder()
            .varietyCode("10031204")
            .plantGrade(plantGrade)
            .plantCount(10)
            .desiredPrice(3000)
            .build();

        mockMvc.perform(
                post("/trade-service/auction-schedules/{auctionScheduleId}/auction-reservations", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("화훼등급을 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("경매 예약 등록시 화훼단수는 양수값이다.")
    @Test
    void createAuctionReservationIsPlantCountZero() throws Exception {
        AuctionReservationCreateRequest request = AuctionReservationCreateRequest.builder()
            .varietyCode("10031204")
            .plantGrade("SUPER")
            .plantCount(0)
            .desiredPrice(3000)
            .build();

        mockMvc.perform(
                post("/trade-service/auction-schedules/{auctionScheduleId}/auction-reservations", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("화훼단수를 올바르게 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("경매 예약 등록시 화훼단수는 필수값이다.")
    @Test
    void createAuctionReservationWithoutPlantCount() throws Exception {
        AuctionReservationCreateRequest request = AuctionReservationCreateRequest.builder()
            .varietyCode("10031204")
            .plantGrade("SUPER")
            .plantCount(null)
            .desiredPrice(3000)
            .build();

        mockMvc.perform(
                post("/trade-service/auction-schedules/{auctionScheduleId}/auction-reservations", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("화훼단수를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("경매 예약 등록시 희망가격은 양수값이다.")
    @Test
    void createAuctionReservationIsDesiredPriceZero() throws Exception {
        AuctionReservationCreateRequest request = AuctionReservationCreateRequest.builder()
            .varietyCode("10031204")
            .plantGrade("SUPER")
            .plantCount(10)
            .desiredPrice(0)
            .build();

        mockMvc.perform(
                post("/trade-service/auction-schedules/{auctionScheduleId}/auction-reservations", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("희망가격을 올바르게 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("경매 예약 등록시 희망가격은 필수값이다.")
    @Test
    void createAuctionReservationWithoutDesiredPrice() throws Exception {
        AuctionReservationCreateRequest request = AuctionReservationCreateRequest.builder()
            .varietyCode("10031204")
            .plantGrade("SUPER")
            .plantCount(10)
            .desiredPrice(null)
            .build();

        mockMvc.perform(
                post("/trade-service/auction-schedules/{auctionScheduleId}/auction-reservations", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("희망가격을 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("경매 예약을 등록한다.")
    @Test
    void createAuctionReservation() throws Exception {
        AuctionReservationCreateRequest request = AuctionReservationCreateRequest.builder()
            .varietyCode("10031204")
            .plantGrade("SUPER")
            .plantCount(10)
            .desiredPrice(3000)
            .build();

        mockMvc.perform(
                post("/trade-service/auction-schedules/{auctionScheduleId}/auction-reservations", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isCreated());
    }

    @DisplayName("경매 예약 수정시 화훼등급은 필수값이다.")
    @NullAndEmptySource
    @ParameterizedTest
    void modifyAuctionReservationWithoutPlantGrade(String plantGrade) throws Exception {
        AuctionReservationModifyRequest request = AuctionReservationModifyRequest.builder()
            .plantGrade(plantGrade)
            .plantCount(10)
            .desiredPrice(3000)
            .build();

        mockMvc.perform(
                patch("/trade-service/auction-schedules/{auctionScheduleId}/auction-reservations/{auctionReservationId}", 1, 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("화훼등급을 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("경매 예약 등록시 화훼단수는 양수값이다.")
    @Test
    void modifyAuctionReservationIsPlantCountZero() throws Exception {
        AuctionReservationModifyRequest request = AuctionReservationModifyRequest.builder()
            .plantGrade("SUPER")
            .plantCount(0)
            .desiredPrice(3000)
            .build();

        mockMvc.perform(
                patch("/trade-service/auction-schedules/{auctionScheduleId}/auction-reservations/{auctionReservationId}", 1, 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("화훼단수를 올바르게 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("경매 예약 등록시 화훼단수는 필수값이다.")
    @Test
    void modifyAuctionReservationWithoutPlantCount() throws Exception {
        AuctionReservationModifyRequest request = AuctionReservationModifyRequest.builder()
            .plantGrade("SUPER")
            .plantCount(null)
            .desiredPrice(3000)
            .build();

        mockMvc.perform(
                patch("/trade-service/auction-schedules/{auctionScheduleId}/auction-reservations/{auctionReservationId}", 1, 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("화훼단수를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("경매 예약 등록시 희망가격은 양수값이다.")
    @Test
    void modifyAuctionReservationIsDesiredPriceZero() throws Exception {
        AuctionReservationModifyRequest request = AuctionReservationModifyRequest.builder()
            .plantGrade("SUPER")
            .plantCount(10)
            .desiredPrice(0)
            .build();

        mockMvc.perform(
                patch("/trade-service/auction-schedules/{auctionScheduleId}/auction-reservations/{auctionReservationId}", 1, 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("희망가격을 올바르게 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("경매 예약 등록시 희망가격은 필수값이다.")
    @Test
    void modifyAuctionReservationWithoutDesiredPrice() throws Exception {
        AuctionReservationModifyRequest request = AuctionReservationModifyRequest.builder()
            .plantGrade("SUPER")
            .plantCount(10)
            .desiredPrice(null)
            .build();

        mockMvc.perform(
                patch("/trade-service/auction-schedules/{auctionScheduleId}/auction-reservations/{auctionReservationId}", 1, 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("희망가격을 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("경매 예약을 등록한다.")
    @Test
    void modifyAuctionReservation() throws Exception {
        AuctionReservationModifyRequest request = AuctionReservationModifyRequest.builder()
            .plantGrade("SUPER")
            .plantCount(10)
            .desiredPrice(3000)
            .build();

        mockMvc.perform(
                patch("/trade-service/auction-schedules/{auctionScheduleId}/auction-reservations/{auctionReservationId}", 1, 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isOk());
    }
}