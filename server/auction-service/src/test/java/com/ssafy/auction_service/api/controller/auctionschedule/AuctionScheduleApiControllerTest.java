package com.ssafy.auction_service.api.controller.auctionschedule;

import com.ssafy.auction_service.ControllerTestSupport;
import com.ssafy.auction_service.api.controller.auctionschedule.request.AuctionScheduleCreateRequest;
import com.ssafy.auction_service.api.controller.auctionschedule.request.AuctionScheduleModifyRequest;
import com.ssafy.auction_service.common.util.TimeUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuctionScheduleApiControllerTest extends ControllerTestSupport {

    @MockBean
    TimeUtils timeUtils;

    @DisplayName("경매 일정 등록시 화훼부류는 필수값이다.")
    @NullAndEmptySource
    @ParameterizedTest
    void createAuctionScheduleWithoutPlantCategory(String plantCategory) throws Exception {
        AuctionScheduleCreateRequest request = AuctionScheduleCreateRequest.builder()
            .plantCategory(plantCategory)
            .jointMarket("YANGJAE")
            .auctionDescription("경매를 진행할 예정입니다.")
            .auctionStartDateTime("2024-07-15T05:00")
            .build();

        mockMvc.perform(
                post("/auction-service/auction-schedules")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("화훼부류를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("경매 일정 등록시 공판장은 필수값이다.")
    @NullAndEmptySource
    @ParameterizedTest
    void createAuctionScheduleWithoutJointMarket(String jointMarket) throws Exception {
        AuctionScheduleCreateRequest request = AuctionScheduleCreateRequest.builder()
            .plantCategory("CUT_FLOWERS")
            .jointMarket(jointMarket)
            .auctionDescription("경매를 진행할 예정입니다.")
            .auctionStartDateTime("2024-07-15T05:00")
            .build();

        mockMvc.perform(
                post("/auction-service/auction-schedules")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("공판장을 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("경매 일정 등록시 경매 설명은 필수값이 아니다.")
    @NullAndEmptySource
    @ParameterizedTest
    void createAuctionScheduleWithoutAuctionDescription(String auctionDescription) throws Exception {
        AuctionScheduleCreateRequest request = AuctionScheduleCreateRequest.builder()
            .plantCategory("CUT_FLOWERS")
            .jointMarket("YANGJAE")
            .auctionDescription(auctionDescription)
            .auctionStartDateTime("2024-07-15T05:00")
            .build();

        mockMvc.perform(
                post("/auction-service/auction-schedules")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated());
    }

    @DisplayName("경매 일정 등록시 경매 시작일시는 필수값이다.")
    @NullAndEmptySource
    @ParameterizedTest
    void createAuctionScheduleWithoutAuctionStartDateTime(String auctionStartDateTime) throws Exception {
        AuctionScheduleCreateRequest request = AuctionScheduleCreateRequest.builder()
            .plantCategory("CUT_FLOWERS")
            .jointMarket("YANGJAE")
            .auctionDescription("경매를 진행할 예정입니다.")
            .auctionStartDateTime(auctionStartDateTime)
            .build();

        mockMvc.perform(
                post("/auction-service/auction-schedules")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("경매 시작일시를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("경매 일정을 등록한다.")
    @Test
    void createAuctionSchedule() throws Exception {
        AuctionScheduleCreateRequest request = AuctionScheduleCreateRequest.builder()
            .plantCategory("CUT_FLOWERS")
            .jointMarket("YANGJAE")
            .auctionDescription("경매를 진행할 예정입니다.")
            .auctionStartDateTime("2024-07-15T05:00")
            .build();

        mockMvc.perform(
                post("/auction-service/auction-schedules")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated());
    }

    @DisplayName("경매 일정 수정시 경매 시작일시는 필수값이다.")
    @Test
    void modifyAuctionScheduleWithoutAuctionStartDateTime() throws Exception {
        AuctionScheduleModifyRequest request = AuctionScheduleModifyRequest.builder()
            .auctionStartDateTime(null)
            .auctionDescription("경매를 진행할 예정입니다.")
            .build();

        mockMvc.perform(
                patch("/auction-service/auction-schedules/{auctionScheduleId}", 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("경매 시작일시를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("경매 일정 수정시 경매 설명은 필수값이 아니다.")
    @Test
    void modifyAuctionScheduleWithoutAuctionDescription() throws Exception {
        AuctionScheduleModifyRequest request = AuctionScheduleModifyRequest.builder()
            .auctionStartDateTime("2024-07-15T05:00")
            .auctionDescription("경매를 진행할 예정입니다.")
            .build();

        mockMvc.perform(
                patch("/auction-service/auction-schedules/{auctionScheduleId}", 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @DisplayName("경매 일정을 수정한다.")
    @Test
    void modifyAuctionSchedule() throws Exception {
        AuctionScheduleModifyRequest request = AuctionScheduleModifyRequest.builder()
            .auctionStartDateTime("2024-07-15T05:00")
            .auctionDescription("경매를 진행할 예정입니다.")
            .build();

        mockMvc.perform(
                patch("/auction-service/auction-schedules/{auctionScheduleId}", 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @DisplayName("경매 일정 상태를 READY로 수정한다.")
    @Test
    void modifyAuctionStatusToReady() throws Exception {
        mockMvc.perform(
                post("/auction-service/auction-schedules/{auctionScheduleId}/ready", 1)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @DisplayName("경매 일정 상태를 PROGRESS로 수정한다.")
    @Test
    void modifyAuctionStatusToProgress() throws Exception {
        mockMvc.perform(
                post("/auction-service/auction-schedules/{auctionScheduleId}/progress", 1)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @DisplayName("경매 일정 상태를 COMPLETE로 수정한다.")
    @Test
    void modifyAuctionStatusToComplete() throws Exception {
        mockMvc.perform(
                post("/auction-service/auction-schedules/{auctionScheduleId}/complete", 1)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @DisplayName("경매 일정을 삭제한다.")
    @Test
    void removeAuctionSchedule() throws Exception {
        mockMvc.perform(
                delete("/auction-service/auction-schedules/{auctionScheduleId}", 1)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }
}