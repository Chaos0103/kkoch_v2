package com.kkoch.user.api.controller.reservation;

import com.kkoch.user.ControllerTestSupport;
import com.kkoch.user.api.controller.reservation.request.ReservationCreateRequest;
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

class ReservationControllerTest extends ControllerTestSupport {

    @DisplayName("신규 거래 예약 등록시 유효성 검사를 한다.")
    @CsvSource({
        ",하젤,10,4500,SUPER,품목명을 입력해주세요.",
        "장미(스텐다드),,10,4500,SUPER,품종명을 입력해주세요.",
        "장미(스텐다드),하젤,0,4500,SUPER,식물 단수를 올바르게 입력해주세요.",
        "장미(스텐다드),하젤,10,0,SUPER,거래 희망 가격을 올바르게 입력해주세요.",
        "장미(스텐다드),하젤,10,4500,,식물 등급을 입력해주세요."
    })
    @ParameterizedTest
    void createReservationWithoutValue(String plantType, String plantName, int plantCount, int desiredPrice, String plantGrade, String message) throws Exception {
        ReservationCreateRequest request = ReservationCreateRequest.builder()
            .plantType(plantType)
            .plantName(plantName)
            .plantCount(plantCount)
            .desiredPrice(desiredPrice)
            .plantGrade(plantGrade)
            .build();

        mockMvc.perform(
                post("/{memberKey}/reservations", generateMemberKey())
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

    @DisplayName("신규 거래 예약을 등록한다.")
    @Test
    void createReservation() throws Exception {
        ReservationCreateRequest request = ReservationCreateRequest.builder()
            .plantType("장미(스텐다드)")
            .plantName("하젤")
            .plantCount(10)
            .desiredPrice(4500)
            .plantGrade("SUPER")
            .build();

        mockMvc.perform(
                post("/{memberKey}/reservations", generateMemberKey())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isCreated());
    }

    @DisplayName("거래 예약 목록 조회시 페이지 번호는 양수이다.")
    @Test
    void searchReservationsIsZero() throws Exception {
        mockMvc.perform(
                get("/{memberKey}/reservations", generateMemberKey())
                    .queryParam("page", "0")
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("페이지 번호를 올바르게 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("거래 예약 목록을 조회한다.")
    @Test
    void searchReservations() throws Exception {
        mockMvc.perform(
                get("/{memberKey}/reservations", generateMemberKey())
                    .queryParam("page", "1")
                    .with(csrf())
            )
            .andExpect(status().isOk());
    }
}