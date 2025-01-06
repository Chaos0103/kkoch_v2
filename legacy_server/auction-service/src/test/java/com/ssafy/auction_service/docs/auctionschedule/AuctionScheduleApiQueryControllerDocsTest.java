package com.ssafy.auction_service.docs.auctionschedule;

import com.ssafy.auction_service.api.ListResponse;
import com.ssafy.auction_service.api.controller.auctionschedule.AuctionScheduleApiQueryController;
import com.ssafy.auction_service.api.controller.auctionschedule.param.AuctionScheduleSearchParam;
import com.ssafy.auction_service.api.service.auctionschedule.AuctionScheduleQueryService;
import com.ssafy.auction_service.docs.RestDocsSupport;
import com.ssafy.auction_service.domain.auctionschedule.AuctionStatus;
import com.ssafy.auction_service.domain.auctionschedule.JointMarket;
import com.ssafy.auction_service.domain.auctionschedule.repository.response.AuctionScheduleDetailResponse;
import com.ssafy.auction_service.domain.auctionschedule.repository.response.AuctionScheduleResponse;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuctionScheduleApiQueryControllerDocsTest extends RestDocsSupport {

    private final AuctionScheduleQueryService auctionScheduleQueryService = mock(AuctionScheduleQueryService.class);

    @Override
    protected Object initController() {
        return new AuctionScheduleApiQueryController(auctionScheduleQueryService);
    }

    @DisplayName("경매 일정 목록 조회 API")
    @Test
    void searchAuctionSchedules() throws Exception {
        AuctionScheduleSearchParam param = AuctionScheduleSearchParam.builder()
            .plantCategory("CUT_FLOWERS")
            .jointMarket("YANGJAE")
            .build();

        AuctionScheduleResponse as = AuctionScheduleResponse.builder()
            .id(1)
            .plantCategory(PlantCategory.CUT_FLOWERS)
            .jointMarket(JointMarket.YANGJAE)
            .auctionStartDateTime(LocalDateTime.of(2024, 8, 12, 5, 0))
            .auctionStatus(AuctionStatus.INIT)
            .build();

        ListResponse<AuctionScheduleResponse> response = ListResponse.of(List.of(as));

        given(auctionScheduleQueryService.searchAuctionSchedulesByCond(any()))
            .willReturn(response);

        mockMvc.perform(
                get("/auction-service/auction-schedules")
                    .header(HttpHeaders.AUTHORIZATION, "issued.access.token")
                    .queryParam("plantCategory", param.getPlantCategory())
                    .queryParam("jointMarket", param.getJointMarket())
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-auction-schedules",
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("회원 토큰")
                ),
                queryParameters(
                    parameterWithName("plantCategory")
                        .optional()
                        .description("화훼부류"),
                    parameterWithName("jointMarket")
                        .optional()
                        .description("공판장")
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
                    fieldWithPath("data.content").type(JsonFieldType.ARRAY)
                        .description("조회된 경매 일정 목록"),
                    fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER)
                        .description("경매 일정 ID"),
                    fieldWithPath("data.content[].plantCategory").type(JsonFieldType.STRING)
                        .description("화훼부류"),
                    fieldWithPath("data.content[].jointMarket").type(JsonFieldType.STRING)
                        .description("공판장"),
                    fieldWithPath("data.content[].auctionStartDateTime").type(JsonFieldType.ARRAY)
                        .description("경매 시작 일시"),
                    fieldWithPath("data.content[].auctionStatus").type(JsonFieldType.STRING)
                        .description("경매 상태"),
                    fieldWithPath("data.size").type(JsonFieldType.NUMBER)
                        .description("조회된 경매 일정 갯수")
                )
            ));
    }

    @DisplayName("경매 일정 상세 조회 API")
    @Test
    void searchAuctionSchedule() throws Exception {
        AuctionScheduleDetailResponse response = AuctionScheduleDetailResponse.builder()
            .id(1)
            .plantCategory(PlantCategory.CUT_FLOWERS)
            .jointMarket(JointMarket.YANGJAE)
            .auctionStartDateTime(LocalDateTime.of(2024, 8, 12, 5, 0))
            .auctionStatus(AuctionStatus.INIT)
            .auctionDescription("경매 설명입니다.")
            .auctionVarietyCount(10)
            .build();

        given(auctionScheduleQueryService.searchAuctionSchedule(anyInt()))
            .willReturn(response);

        mockMvc.perform(
                get("/auction-service/auction-schedules/{auctionScheduleId}", 1)
                    .header(HttpHeaders.AUTHORIZATION, "issued.access.token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-auction-schedule",
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
                        .description("경매 시작 일시"),
                    fieldWithPath("data.auctionStatus").type(JsonFieldType.STRING)
                        .description("경매 상태"),
                    fieldWithPath("data.auctionDescription").type(JsonFieldType.STRING)
                        .description("경매 설명"),
                    fieldWithPath("data.auctionVarietyCount").type(JsonFieldType.NUMBER)
                        .description("경매에 등록된 품종 갯수")
                )
            ));
    }
}
