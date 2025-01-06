package com.ssafy.trade_service.docs.auctionstatistics;

import com.ssafy.trade_service.api.ListResponse;
import com.ssafy.trade_service.api.controller.auctionstatistics.AuctionStatisticsApiQueryController;
import com.ssafy.trade_service.api.controller.auctionstatistics.param.AuctionStatisticsParam;
import com.ssafy.trade_service.api.service.auctionstatistics.AuctionStatisticsQueryService;
import com.ssafy.trade_service.docs.RestDocsSupport;
import com.ssafy.trade_service.domain.auctionstatistics.repository.response.AuctionStatisticsResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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

class AuctionStatisticsApiQueryControllerDocsTest extends RestDocsSupport {

    private final AuctionStatisticsQueryService auctionStatisticsQueryService = mock(AuctionStatisticsQueryService.class);

    @Override
    protected Object initController() {
        return new AuctionStatisticsApiQueryController(auctionStatisticsQueryService);
    }

    @DisplayName("경매 통계 목록 조회 API")
    @Test
    void searchAuctionStatistics() throws Exception {
        AuctionStatisticsResponse auctionStatistics = AuctionStatisticsResponse.builder()
            .varietyCode("10031204")
            .plantGrade("SUPER")
            .plantCount(100)
            .avg(3400)
            .max(4000)
            .min(2800)
            .calculatedDate(LocalDate.of(2024, 8, 15))
            .build();

        given(auctionStatisticsQueryService.searchAuctionStatistics(anyString(), any()))
            .willReturn(ListResponse.of(List.of(auctionStatistics)));

        AuctionStatisticsParam param = AuctionStatisticsParam.builder()
            .from(LocalDate.of(2024, 8, 1))
            .to(LocalDate.of(2024, 8, 31))
            .plantGrade("SUPER")
            .build();

        mockMvc.perform(
                get("/trade-service/auction-statistics/{varietyCode}", "10031204")
                    .queryParam("from", param.getFrom().toString())
                    .queryParam("to", param.getTo().toString())
                    .queryParam("plantGrade", param.getPlantGrade())
                    .header(HttpHeaders.AUTHORIZATION, "issued.access.token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-auction-statistics",
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("회원 토큰")
                ),
                pathParameters(
                    parameterWithName("varietyCode")
                        .description("품종코드")
                ),
                queryParameters(
                    parameterWithName("from")
                        .description("조회 시작일"),
                    parameterWithName("to")
                        .description("조회 종료일"),
                    parameterWithName("plantGrade")
                        .optional()
                        .description("화훼등급")
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
                        .description("조회된 경매 통계 목록"),
                    fieldWithPath("data.content[].varietyCode").type(JsonFieldType.STRING)
                        .description("품종코드"),
                    fieldWithPath("data.content[].plantGrade").type(JsonFieldType.STRING)
                        .description("화훼등급"),
                    fieldWithPath("data.content[].plantCount").type(JsonFieldType.NUMBER)
                        .description("화훼단수"),
                    fieldWithPath("data.content[].avg").type(JsonFieldType.NUMBER)
                        .description("평균 낙찰 가격"),
                    fieldWithPath("data.content[].max").type(JsonFieldType.NUMBER)
                        .description("최대 낙찰 가격"),
                    fieldWithPath("data.content[].min").type(JsonFieldType.NUMBER)
                        .description("최소 낙찰 가격"),
                    fieldWithPath("data.content[].calculatedDate").type(JsonFieldType.ARRAY)
                        .description("계산된 일자"),
                    fieldWithPath("data.size").type(JsonFieldType.NUMBER)
                        .description("조회된 경매 통계 갯수")
                )
            ));
    }
}
