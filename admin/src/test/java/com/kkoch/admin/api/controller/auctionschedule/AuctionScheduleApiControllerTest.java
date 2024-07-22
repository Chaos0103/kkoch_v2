package com.kkoch.admin.api.controller.auctionschedule;

import com.kkoch.admin.ControllerTestSupport;
import com.kkoch.admin.api.controller.auctionschedule.request.AuctionScheduleCreateRequest;
import com.kkoch.admin.api.controller.auctionschedule.request.AuctionScheduleModifyRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuctionScheduleApiControllerTest extends ControllerTestSupport {

    @DisplayName("경매 일정 신규 등록시 화훼 코드는 필수값이다.")
    @Test
    void createAuctionScheduleWithoutCode() throws Exception {
        AuctionScheduleCreateRequest request = AuctionScheduleCreateRequest.builder()
            .code(" ")
            .auctionDateTime(LocalDateTime.of(2024, 7, 12, 5, 0))
            .build();

        mockMvc.perform(
                post("/admin-service/auction-schedules")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("화훼 코드를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("경매 일정 신규 등록시 경매 일시는 필수값이다.")
    @Test
    void createAuctionScheduleWithoutAuctionDateTime() throws Exception {
        AuctionScheduleCreateRequest request = AuctionScheduleCreateRequest.builder()
            .code("CUT_FLOWERS")
            .build();

        mockMvc.perform(
                post("/admin-service/auction-schedules")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("경매 일시를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("경매 일정을 신규 등록한다.")
    @Test
    void createAuctionSchedule() throws Exception {
        AuctionScheduleCreateRequest request = AuctionScheduleCreateRequest.builder()
            .code("CUT_FLOWERS")
            .auctionDateTime(LocalDateTime.of(2024, 7, 12, 5, 0))
            .build();

        mockMvc.perform(
                post("/admin-service/auction-schedules")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated());
    }

    @DisplayName("경매 일정 수정시 화훼 코드는 필수값이다.")
    @Test
    void modifyAuctionScheduleWithoutCode() throws Exception {
        AuctionScheduleModifyRequest request = AuctionScheduleModifyRequest.builder()
            .code(" ")
            .auctionDateTime(LocalDateTime.of(2024, 7, 12, 5, 0))
            .build();

        mockMvc.perform(
                patch("/admin-service/auction-schedules/{auctionScheduleId}", 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("화훼 코드를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("경매 일정 수정시 경매 일시은 필수값이다.")
    @Test
    void modifyAuctionScheduleWithoutAuctionDateTime() throws Exception {
        AuctionScheduleModifyRequest request = AuctionScheduleModifyRequest.builder()
            .code("CUT_FLOWERS")
            .build();

        mockMvc.perform(
                patch("/admin-service/auction-schedules/{auctionScheduleId}", 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("경매 일시를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("경매 일정을 수정한다.")
    @Test
    void modifyAuctionSchedule() throws Exception {
        AuctionScheduleModifyRequest request = AuctionScheduleModifyRequest.builder()
            .code("CUT_FLOWERS")
            .auctionDateTime(LocalDateTime.of(2024, 7, 12, 5, 0))
            .build();

        mockMvc.perform(
                patch("/admin-service/auction-schedules/{auctionScheduleId}", 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @DisplayName("경매 방 상태를 준비 상태로 수정한다.")
    @Test
    void readyAuctionSchedule() throws Exception {
        mockMvc.perform(
                post("/admin-service/auction-schedules/{auctionScheduleId}/ready", 1)
            )
            .andExpect(status().isOk());
    }

    @DisplayName("경매 방 상태를 진행중 상태로 수정한다.")
    @Test
    void openAuctionSchedule() throws Exception {
        mockMvc.perform(
                post("/admin-service/auction-schedules/{auctionScheduleId}/open", 1)
            )
            .andExpect(status().isOk());
    }

    @DisplayName("경매 방 상태를 마감 상태로 수정한다.")
    @Test
    void closeAuctionSchedule() throws Exception {
        mockMvc.perform(
                post("/admin-service/auction-schedules/{auctionScheduleId}/close", 1)
            )
            .andExpect(status().isOk());
    }

    @DisplayName("경매 일정을 삭제한다.")
    @Test
    void removeAuctionSchedule() throws Exception {
        mockMvc.perform(
                delete("/admin-service/auction-schedules/{auctionScheduleId}", 1)
            )
            .andExpect(status().isOk());
    }

    @DisplayName("진행중인 경매 일정 조회시 조회된 데이터가 없으면 null과 메세지를 반환한다.")
    @Test
    void searchOpenedAuctionWithoutResponse() throws Exception {
        mockMvc.perform(
                get("/admin-service/auction-schedules/open")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("현재 진행중인 경매가 없습니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("경매 일정 목록을 조회한다.")
    @Test
    void searchAuctionSchedules() throws Exception {
        mockMvc.perform(
                get("/admin-service/auction-schedules")
            )
            .andExpect(status().isOk());
    }

    @DisplayName("진행중인 경매 일정을 조회한다.")
    @Test
    void searchOpenedAuction() throws Exception {
        mockMvc.perform(
                get("/admin-service/auction-schedules/open")
            )
            .andExpect(status().isOk());
    }
}