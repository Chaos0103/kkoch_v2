package com.kkoch.admin.docs.auctionschedule;

import com.kkoch.admin.api.controller.auctionschedule.AuctionScheduleApiController;
import com.kkoch.admin.api.controller.auctionschedule.request.AuctionScheduleCreateRequest;
import com.kkoch.admin.api.controller.auctionschedule.request.AuctionScheduleModifyRequest;
import com.kkoch.admin.api.service.auctionschedule.AuctionScheduleQueryService;
import com.kkoch.admin.api.service.auctionschedule.AuctionScheduleService;
import com.kkoch.admin.api.service.auctionschedule.response.AuctionScheduleCreateResponse;
import com.kkoch.admin.api.service.auctionschedule.response.AuctionScheduleModifyResponse;
import com.kkoch.admin.api.service.auctionschedule.response.AuctionScheduleRemoveResponse;
import com.kkoch.admin.api.service.auctionschedule.response.AuctionScheduleStatusResponse;
import com.kkoch.admin.docs.RestDocsSupport;
import com.kkoch.admin.domain.auctionschedule.AuctionRoomStatus;
import com.kkoch.admin.domain.variety.PlantCategory;
import com.kkoch.admin.domain.auctionschedule.repository.response.OpenedAuctionResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ActionScheduleApiControllerDocsTest extends RestDocsSupport {

    private final AuctionScheduleService auctionScheduleService = mock(AuctionScheduleService.class);
    private final AuctionScheduleQueryService auctionScheduleQueryService = mock(AuctionScheduleQueryService.class);

    @Override
    protected Object initController() {
        return new AuctionScheduleApiController(auctionScheduleService, auctionScheduleQueryService);
    }

    @DisplayName("경매 일정 신규 등록 API")
    @Test
    void createAuctionSchedule() throws Exception {
        AuctionScheduleCreateRequest request = AuctionScheduleCreateRequest.builder()
            .code("CUT_FLOWERS")
            .auctionDateTime(LocalDateTime.of(2024, 7, 12, 5, 0))
            .build();

        AuctionScheduleCreateResponse response = AuctionScheduleCreateResponse.builder()
            .auctionScheduleId(1)
            .code(PlantCategory.CUT_FLOWERS)
            .roomStatus(AuctionRoomStatus.INIT)
            .auctionDateTime(LocalDateTime.of(2024, 7, 12, 5, 0))
            .createdDateTime(LocalDateTime.now())
            .build();

        given(auctionScheduleService.createAuctionSchedule(anyInt(), any()))
            .willReturn(response);

        mockMvc.perform(
                post("/admin-service/auction-schedules")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-auction-schedule",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("code").type(JsonFieldType.STRING)
                        .description("화훼 코드"),
                    fieldWithPath("auctionDateTime").type(JsonFieldType.ARRAY)
                        .description("경매 일시")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT)
                        .description("응답 데이터"),
                    fieldWithPath("data.auctionScheduleId").type(JsonFieldType.NUMBER)
                        .description("경매 일정 ID"),
                    fieldWithPath("data.code").type(JsonFieldType.STRING)
                        .description("화훼 코드"),
                    fieldWithPath("data.roomStatus").type(JsonFieldType.STRING)
                        .description("경매 방 상태"),
                    fieldWithPath("data.auctionDateTime").type(JsonFieldType.ARRAY)
                        .description("경매 일시"),
                    fieldWithPath("data.createdDateTime").type(JsonFieldType.ARRAY)
                        .description("경매 일정 등록일시")
                )
            ));
    }

    @DisplayName("경매 일정 수정 API")
    @Test
    void modifyAuctionSchedule() throws Exception {
        AuctionScheduleModifyRequest request = AuctionScheduleModifyRequest.builder()
            .code("CUT_FLOWERS")
            .auctionDateTime(LocalDateTime.of(2024, 7, 12, 5, 0))
            .build();

        AuctionScheduleModifyResponse response = AuctionScheduleModifyResponse.builder()
            .auctionScheduleId(1)
            .code(PlantCategory.CUT_FLOWERS)
            .roomStatus(AuctionRoomStatus.INIT)
            .auctionDateTime(LocalDateTime.of(2024, 7, 12, 5, 0))
            .modifiedDateTime(LocalDateTime.now())
            .build();

        given(auctionScheduleService.modifyAuctionSchedule(anyInt(), anyInt(), any()))
            .willReturn(response);

        mockMvc.perform(
                patch("/admin-service/auction-schedules/{auctionScheduleId}", 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("modify-auction-schedule",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("code").type(JsonFieldType.STRING)
                        .description("화훼 코드"),
                    fieldWithPath("auctionDateTime").type(JsonFieldType.ARRAY)
                        .description("경매 일시")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT)
                        .description("응답 데이터"),
                    fieldWithPath("data.auctionScheduleId").type(JsonFieldType.NUMBER)
                        .description("경매 일정 ID"),
                    fieldWithPath("data.code").type(JsonFieldType.STRING)
                        .description("화훼 코드"),
                    fieldWithPath("data.roomStatus").type(JsonFieldType.STRING)
                        .description("경매 방 상태"),
                    fieldWithPath("data.auctionDateTime").type(JsonFieldType.ARRAY)
                        .description("경매 일시"),
                    fieldWithPath("data.modifiedDateTime").type(JsonFieldType.ARRAY)
                        .description("경매 일정 수정일시")
                )
            ));
    }

    @DisplayName("경매 준비 API")
    @Test
    void readyAuctionSchedule() throws Exception {
        AuctionScheduleStatusResponse response = AuctionScheduleStatusResponse.builder()
            .auctionScheduleId(1)
            .code(PlantCategory.CUT_FLOWERS)
            .roomStatus(AuctionRoomStatus.READY)
            .auctionDateTime(LocalDateTime.of(2024, 7, 12, 5, 0))
            .modifiedDateTime(LocalDateTime.now())
            .build();

        given(auctionScheduleService.modifyAuctionRoomStatus(anyInt(), anyInt(), any()))
            .willReturn(response);

        mockMvc.perform(
                post("/admin-service/auction-schedules/{auctionScheduleId}/ready", 1)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("ready-auction-schedule",
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT)
                        .description("응답 데이터"),
                    fieldWithPath("data.auctionScheduleId").type(JsonFieldType.NUMBER)
                        .description("경매 일정 ID"),
                    fieldWithPath("data.code").type(JsonFieldType.STRING)
                        .description("화훼 코드"),
                    fieldWithPath("data.roomStatus").type(JsonFieldType.STRING)
                        .description("경매 방 상태"),
                    fieldWithPath("data.auctionDateTime").type(JsonFieldType.ARRAY)
                        .description("경매 일시"),
                    fieldWithPath("data.modifiedDateTime").type(JsonFieldType.ARRAY)
                        .description("경매 일정 수정일시")
                )
            ));
    }

    @DisplayName("경매 진행 API")
    @Test
    void openAuctionSchedule() throws Exception {
        AuctionScheduleStatusResponse response = AuctionScheduleStatusResponse.builder()
            .auctionScheduleId(1)
            .code(PlantCategory.CUT_FLOWERS)
            .roomStatus(AuctionRoomStatus.OPEN)
            .auctionDateTime(LocalDateTime.of(2024, 7, 12, 5, 0))
            .modifiedDateTime(LocalDateTime.now())
            .build();

        given(auctionScheduleService.modifyAuctionRoomStatus(anyInt(), anyInt(), any()))
            .willReturn(response);

        mockMvc.perform(
                post("/admin-service/auction-schedules/{auctionScheduleId}/open", 1)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("open-auction-schedule",
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT)
                        .description("응답 데이터"),
                    fieldWithPath("data.auctionScheduleId").type(JsonFieldType.NUMBER)
                        .description("경매 일정 ID"),
                    fieldWithPath("data.code").type(JsonFieldType.STRING)
                        .description("화훼 코드"),
                    fieldWithPath("data.roomStatus").type(JsonFieldType.STRING)
                        .description("경매 방 상태"),
                    fieldWithPath("data.auctionDateTime").type(JsonFieldType.ARRAY)
                        .description("경매 일시"),
                    fieldWithPath("data.modifiedDateTime").type(JsonFieldType.ARRAY)
                        .description("경매 일정 수정일시")
                )
            ));
    }

    @DisplayName("경매 마감 API")
    @Test
    void closeAuctionSchedule() throws Exception {
        AuctionScheduleStatusResponse response = AuctionScheduleStatusResponse.builder()
            .auctionScheduleId(1)
            .code(PlantCategory.CUT_FLOWERS)
            .roomStatus(AuctionRoomStatus.CLOSE)
            .auctionDateTime(LocalDateTime.of(2024, 7, 12, 5, 0))
            .modifiedDateTime(LocalDateTime.now())
            .build();

        given(auctionScheduleService.modifyAuctionRoomStatus(anyInt(), anyInt(), any()))
            .willReturn(response);

        mockMvc.perform(
                post("/admin-service/auction-schedules/{auctionScheduleId}/close", 1)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("close-auction-schedule",
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT)
                        .description("응답 데이터"),
                    fieldWithPath("data.auctionScheduleId").type(JsonFieldType.NUMBER)
                        .description("경매 일정 ID"),
                    fieldWithPath("data.code").type(JsonFieldType.STRING)
                        .description("화훼 코드"),
                    fieldWithPath("data.roomStatus").type(JsonFieldType.STRING)
                        .description("경매 방 상태"),
                    fieldWithPath("data.auctionDateTime").type(JsonFieldType.ARRAY)
                        .description("경매 일시"),
                    fieldWithPath("data.modifiedDateTime").type(JsonFieldType.ARRAY)
                        .description("경매 일정 수정일시")
                )
            ));
    }

    @DisplayName("경매 일정 삭제 API")
    @Test
    void removeAuctionSchedule() throws Exception {
        AuctionScheduleRemoveResponse response = AuctionScheduleRemoveResponse.builder()
            .auctionScheduleId(1)
            .code(PlantCategory.CUT_FLOWERS)
            .roomStatus(AuctionRoomStatus.READY)
            .auctionDateTime(LocalDateTime.of(2024, 7, 12, 5, 0))
            .removedDateTime(LocalDateTime.now())
            .build();

        given(auctionScheduleService.removeAuctionSchedule(anyInt(), anyInt()))
            .willReturn(response);

        mockMvc.perform(
                delete("/admin-service/auction-schedules/{auctionScheduleId}", 1)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("remove-auction-schedule",
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT)
                        .description("응답 데이터"),
                    fieldWithPath("data.auctionScheduleId").type(JsonFieldType.NUMBER)
                        .description("경매 일정 ID"),
                    fieldWithPath("data.code").type(JsonFieldType.STRING)
                        .description("화훼 코드"),
                    fieldWithPath("data.roomStatus").type(JsonFieldType.STRING)
                        .description("경매 방 상태"),
                    fieldWithPath("data.auctionDateTime").type(JsonFieldType.ARRAY)
                        .description("경매 일시"),
                    fieldWithPath("data.removedDateTime").type(JsonFieldType.ARRAY)
                        .description("경매 일정 삭제일시")
                )
            ));
    }

    @DisplayName("진행중인 경매 일정 조회 API")
    @Test
    void searchOpenedAuction() throws Exception {
        OpenedAuctionResponse response = OpenedAuctionResponse.builder()
            .auctionScheduleId(1)
            .code(PlantCategory.CUT_FLOWERS)
            .roomStatus(AuctionRoomStatus.OPEN)
            .auctionDateTime(LocalDateTime.of(2024, 7, 12, 5, 0))
            .build();

        given(auctionScheduleQueryService.searchOpenedAuction())
            .willReturn(response);

        mockMvc.perform(
                get("/admin-service/auction-schedules/open")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-opened-auction",
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT)
                        .description("응답 데이터"),
                    fieldWithPath("data.auctionScheduleId").type(JsonFieldType.NUMBER)
                        .description("경매 일정 ID"),
                    fieldWithPath("data.code").type(JsonFieldType.STRING)
                        .description("화훼 코드"),
                    fieldWithPath("data.roomStatus").type(JsonFieldType.STRING)
                        .description("경매 방 상태"),
                    fieldWithPath("data.auctionDateTime").type(JsonFieldType.ARRAY)
                        .description("경매 일시")
                )
            ));
    }
}
