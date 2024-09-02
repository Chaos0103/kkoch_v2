package com.ssafy.auction_service.docs.auctionschedule;

import com.ssafy.auction_service.api.controller.auctionschedule.AuctionScheduleApiController;
import com.ssafy.auction_service.api.controller.auctionschedule.request.AuctionScheduleCreateRequest;
import com.ssafy.auction_service.api.service.auctionschedule.AuctionScheduleService;
import com.ssafy.auction_service.api.service.auctionschedule.response.AuctionScheduleCreateResponse;
import com.ssafy.auction_service.docs.RestDocsSupport;
import com.ssafy.auction_service.domain.auctionschedule.AuctionStatue;
import com.ssafy.auction_service.domain.auctionschedule.JointMarket;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    void createVariety() throws Exception {
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
            .auctionStatus(AuctionStatue.INIT)
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
}
