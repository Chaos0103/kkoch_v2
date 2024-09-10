package com.ssafy.auction_service.docs.auctionschedule;

import com.ssafy.auction_service.api.controller.auctionschedule.AuctionScheduleApiController;
import com.ssafy.auction_service.api.controller.auctionschedule.request.AuctionScheduleCreateRequest;
import com.ssafy.auction_service.api.controller.auctionschedule.request.AuctionScheduleModifyRequest;
import com.ssafy.auction_service.api.service.auctionschedule.AuctionScheduleService;
import com.ssafy.auction_service.api.service.auctionschedule.response.AuctionScheduleCreateResponse;
import com.ssafy.auction_service.api.service.auctionschedule.response.AuctionScheduleModifyResponse;
import com.ssafy.auction_service.api.service.auctionschedule.response.AuctionScheduleRemoveResponse;
import com.ssafy.auction_service.api.service.auctionschedule.response.AuctionStatusModifyResponse;
import com.ssafy.auction_service.docs.RestDocsSupport;
import com.ssafy.auction_service.domain.auctionschedule.AuctionStatus;
import com.ssafy.auction_service.domain.auctionschedule.JointMarket;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuctionScheduleApiControllerDocsTest extends RestDocsSupport {

    private final AuctionScheduleService auctionScheduleService = mock(AuctionScheduleService.class);

    @Override
    protected Object initController() {
        return new AuctionScheduleApiController(auctionScheduleService);
    }

    @DisplayName("경매 일정 등록 API")
    @Test
    void createAuctionSchedule() throws Exception {
        AuctionScheduleCreateRequest request = AuctionScheduleCreateRequest.builder()
            .plantCategory("CUT_FLOWERS")
            .jointMarket("YANGJAE")
            .auctionDescription("경매를 진행할 예정입니다.")
            .auctionStartDateTime("2024-07-15T05:00")
            .build();

        AuctionScheduleCreateResponse response = AuctionScheduleCreateResponse.builder()
            .id(1)
            .plantCategory(PlantCategory.CUT_FLOWERS.getDescription())
            .jointMarket(JointMarket.YANGJAE.getKorean())
            .auctionStartDateTime(LocalDateTime.of(2024, 7, 15, 5, 0))
            .auctionStatus(AuctionStatus.INIT)
            .createdDateTime(LocalDateTime.now())
            .build();

        given(auctionScheduleService.createAuctionSchedule(any(), any()))
            .willReturn(response);

        mockMvc.perform(
                post("/auction-service/auction-schedules")
                    .header(HttpHeaders.AUTHORIZATION, "issued.access.token")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-auction-schedule",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("회원 토큰")
                ),
                requestFields(
                    fieldWithPath("plantCategory").type(JsonFieldType.STRING)
                        .description("화훼부류"),
                    fieldWithPath("jointMarket").type(JsonFieldType.STRING)
                        .description("공판장"),
                    fieldWithPath("auctionDescription").type(JsonFieldType.STRING)
                        .optional()
                        .description("경매 설명"),
                    fieldWithPath("auctionStartDateTime").type(JsonFieldType.STRING)
                        .description("경매 시작일시(yyyy-MM-ddThh:mm")
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
                    fieldWithPath("data.id").type(JsonFieldType.NUMBER)
                        .description("경매 일정 ID"),
                    fieldWithPath("data.plantCategory").type(JsonFieldType.STRING)
                        .description("화훼부류"),
                    fieldWithPath("data.jointMarket").type(JsonFieldType.STRING)
                        .description("공판장"),
                    fieldWithPath("data.auctionStartDateTime").type(JsonFieldType.ARRAY)
                        .description("경매 시작일시"),
                    fieldWithPath("data.auctionStatus").type(JsonFieldType.STRING)
                        .description("경매 상태"),
                    fieldWithPath("data.createdDateTime").type(JsonFieldType.ARRAY)
                        .description("경매 일정 등록일시")
                )
            ));
    }

    @DisplayName("경매 일정 수정 API")
    @Test
    void modifyAuctionSchedule() throws Exception {
        AuctionScheduleModifyRequest request = AuctionScheduleModifyRequest.builder()
            .auctionStartDateTime("2024-07-15T05:00")
            .auctionDescription("경매를 진행할 예정입니다.")
            .build();

        AuctionScheduleModifyResponse response = AuctionScheduleModifyResponse.builder()
            .id(1)
            .plantCategory(PlantCategory.CUT_FLOWERS.getDescription())
            .jointMarket(JointMarket.YANGJAE.getKorean())
            .auctionStartDateTime(LocalDateTime.of(2024, 7, 15, 5, 0))
            .auctionStatus(AuctionStatus.INIT)
            .modifiedDateTime(LocalDateTime.now())
            .build();

        given(auctionScheduleService.modifyAuctionSchedule(anyInt(), any(), any()))
            .willReturn(response);

        mockMvc.perform(
                patch("/auction-service/auction-schedules/{auctionScheduleId}", 1)
                    .header(HttpHeaders.AUTHORIZATION, "issued.access.token")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("modify-auction-schedule",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("회원 토큰")
                ),
                pathParameters(
                    parameterWithName("auctionScheduleId")
                        .description("경매 일정 ID")
                ),
                requestFields(
                    fieldWithPath("auctionStartDateTime").type(JsonFieldType.STRING)
                        .description("경매 시작일시(yyyy-MM-ddThh:mm"),
                    fieldWithPath("auctionDescription").type(JsonFieldType.STRING)
                        .optional()
                        .description("경매 설명")
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
                    fieldWithPath("data.id").type(JsonFieldType.NUMBER)
                        .description("경매 일정 ID"),
                    fieldWithPath("data.plantCategory").type(JsonFieldType.STRING)
                        .description("화훼부류"),
                    fieldWithPath("data.jointMarket").type(JsonFieldType.STRING)
                        .description("공판장"),
                    fieldWithPath("data.auctionStartDateTime").type(JsonFieldType.ARRAY)
                        .description("경매 시작일시"),
                    fieldWithPath("data.auctionStatus").type(JsonFieldType.STRING)
                        .description("경매 상태"),
                    fieldWithPath("data.modifiedDateTime").type(JsonFieldType.ARRAY)
                        .description("경매 일정 수정일시")
                )
            ));
    }

    @DisplayName("경매 준비 API")
    @Test
    void modifyAuctionStatusToReady() throws Exception {
        AuctionStatusModifyResponse response = AuctionStatusModifyResponse.builder()
            .id(1)
            .auctionStatus(AuctionStatus.READY)
            .modifiedDateTime(LocalDateTime.now())
            .build();

        given(auctionScheduleService.modifyAuctionStatusToReady(anyInt(), any()))
            .willReturn(response);

        mockMvc.perform(
                post("/auction-service/auction-schedules/{auctionScheduleId}/ready", 1)
                    .header(HttpHeaders.AUTHORIZATION, "issued.access.token")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("modify-auction-status-to-ready",
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("회원 토큰")
                ),
                pathParameters(
                    parameterWithName("auctionScheduleId")
                        .description("경매 일정 ID")
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
                    fieldWithPath("data.id").type(JsonFieldType.NUMBER)
                        .description("경매 일정 ID"),
                    fieldWithPath("data.auctionStatus").type(JsonFieldType.STRING)
                        .description("경매 상태"),
                    fieldWithPath("data.modifiedDateTime").type(JsonFieldType.ARRAY)
                        .description("경매 상태 수정일시")
                )
            ));
    }

    @DisplayName("경매 진행 API")
    @Test
    void modifyAuctionStatusToProgress() throws Exception {
        AuctionStatusModifyResponse response = AuctionStatusModifyResponse.builder()
            .id(1)
            .auctionStatus(AuctionStatus.PROGRESS)
            .modifiedDateTime(LocalDateTime.now())
            .build();

        given(auctionScheduleService.modifyAuctionStatusToProgress(anyInt(), any()))
            .willReturn(response);

        mockMvc.perform(
                post("/auction-service/auction-schedules/{auctionScheduleId}/progress", 1)
                    .header(HttpHeaders.AUTHORIZATION, "issued.access.token")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("modify-auction-status-to-progress",
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("회원 토큰")
                ),
                pathParameters(
                    parameterWithName("auctionScheduleId")
                        .description("경매 일정 ID")
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
                    fieldWithPath("data.id").type(JsonFieldType.NUMBER)
                        .description("경매 일정 ID"),
                    fieldWithPath("data.auctionStatus").type(JsonFieldType.STRING)
                        .description("경매 상태"),
                    fieldWithPath("data.modifiedDateTime").type(JsonFieldType.ARRAY)
                        .description("경매 상태 수정일시")
                )
            ));
    }

    @DisplayName("경매 완료 API")
    @Test
    void modifyAuctionStatusToComplete() throws Exception {
        AuctionStatusModifyResponse response = AuctionStatusModifyResponse.builder()
            .id(1)
            .auctionStatus(AuctionStatus.COMPLETE)
            .modifiedDateTime(LocalDateTime.now())
            .build();

        given(auctionScheduleService.modifyAuctionStatusToComplete(anyInt(), any()))
            .willReturn(response);

        mockMvc.perform(
                post("/auction-service/auction-schedules/{auctionScheduleId}/complete", 1)
                    .header(HttpHeaders.AUTHORIZATION, "issued.access.token")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("modify-auction-status-to-complete",
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("회원 토큰")
                ),
                pathParameters(
                    parameterWithName("auctionScheduleId")
                        .description("경매 일정 ID")
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
                    fieldWithPath("data.id").type(JsonFieldType.NUMBER)
                        .description("경매 일정 ID"),
                    fieldWithPath("data.auctionStatus").type(JsonFieldType.STRING)
                        .description("경매 상태"),
                    fieldWithPath("data.modifiedDateTime").type(JsonFieldType.ARRAY)
                        .description("경매 상태 수정일시")
                )
            ));
    }

    @DisplayName("경매 일정 삭제 API")
    @Test
    void removeAuctionSchedule() throws Exception {
        AuctionScheduleRemoveResponse response = AuctionScheduleRemoveResponse.builder()
            .id(1)
            .plantCategory(PlantCategory.CUT_FLOWERS.getDescription())
            .jointMarket(JointMarket.YANGJAE.getKorean())
            .auctionStartDateTime(LocalDateTime.of(2024, 7, 15, 5, 0))
            .auctionStatus(AuctionStatus.INIT)
            .removedDateTime(LocalDateTime.now())
            .build();

        given(auctionScheduleService.removeAuctionSchedule(anyInt(), any()))
            .willReturn(response);

        mockMvc.perform(
                delete("/auction-service/auction-schedules/{auctionScheduleId}", 1)
                    .header(HttpHeaders.AUTHORIZATION, "issued.access.token")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("remove-auction-schedule",
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("회원 토큰")
                ),
                pathParameters(
                    parameterWithName("auctionScheduleId")
                        .description("경매 일정 ID")
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
                    fieldWithPath("data.id").type(JsonFieldType.NUMBER)
                        .description("경매 일정 ID"),
                    fieldWithPath("data.plantCategory").type(JsonFieldType.STRING)
                        .description("화훼부류"),
                    fieldWithPath("data.jointMarket").type(JsonFieldType.STRING)
                        .description("공판장"),
                    fieldWithPath("data.auctionStartDateTime").type(JsonFieldType.ARRAY)
                        .description("경매 시작일시"),
                    fieldWithPath("data.auctionStatus").type(JsonFieldType.STRING)
                        .description("경매 상태"),
                    fieldWithPath("data.removedDateTime").type(JsonFieldType.ARRAY)
                        .description("경매 일정 삭제일시")
                )
            ));
    }
}
