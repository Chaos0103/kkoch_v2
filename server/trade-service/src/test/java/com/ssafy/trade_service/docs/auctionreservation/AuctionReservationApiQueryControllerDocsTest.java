package com.ssafy.trade_service.docs.auctionreservation;

import com.ssafy.trade_service.api.ListResponse;
import com.ssafy.trade_service.api.controller.auctionreservation.AuctionReservationApiQueryController;
import com.ssafy.trade_service.api.service.auctionreservation.AuctionReservationQueryService;
import com.ssafy.trade_service.docs.RestDocsSupport;
import com.ssafy.trade_service.domain.auctionreservation.PlantGrade;
import com.ssafy.trade_service.domain.auctionreservation.repository.response.AuctionReservationResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;

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
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuctionReservationApiQueryControllerDocsTest extends RestDocsSupport {

    private final AuctionReservationQueryService auctionReservationQueryService = mock(AuctionReservationQueryService.class);

    @Override
    protected Object initController() {
        return new AuctionReservationApiQueryController(auctionReservationQueryService);
    }

    @DisplayName("경매 예약 목록 조회 API")
    @Test
    void searchAuctionReservations() throws Exception {
        AuctionReservationResponse response = AuctionReservationResponse.builder()
            .id(1L)
            .varietyCode("10031204")
            .plantGrade(PlantGrade.SUPER)
            .plantCount(10)
            .desiredPrice(3000)
            .build();
        given(auctionReservationQueryService.searchAuctionReservations(anyInt()))
            .willReturn(ListResponse.of(List.of(response)));

        mockMvc.perform(
                get("/trade-service/auction-schedules/{auctionScheduleId}/auction-reservations", 1)
                    .header(HttpHeaders.AUTHORIZATION, "issued.access.token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-auction-reservations",
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
                    fieldWithPath("data.content").type(JsonFieldType.ARRAY)
                        .description("조회된 경매 예약 목록"),
                    fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER)
                        .description("경매 예약 ID"),
                    fieldWithPath("data.content[].varietyCode").type(JsonFieldType.STRING)
                        .description("품종코드"),
                    fieldWithPath("data.content[].plantGrade").type(JsonFieldType.STRING)
                        .description("화훼등급"),
                    fieldWithPath("data.content[].plantCount").type(JsonFieldType.NUMBER)
                        .description("화훼단수"),
                    fieldWithPath("data.content[].desiredPrice").type(JsonFieldType.NUMBER)
                        .description("희망가격"),
                    fieldWithPath("data.size").type(JsonFieldType.NUMBER)
                        .description("조회된 경매 예약 갯수")
                )
            ));
    }
}
