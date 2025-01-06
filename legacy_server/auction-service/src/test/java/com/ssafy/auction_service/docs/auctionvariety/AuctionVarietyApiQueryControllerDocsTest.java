package com.ssafy.auction_service.docs.auctionvariety;

import com.ssafy.auction_service.api.PageResponse;
import com.ssafy.auction_service.api.controller.auctionvariety.AuctionVarietyApiQueryController;
import com.ssafy.auction_service.api.controller.auctionvariety.param.AuctionVarietySearchParam;
import com.ssafy.auction_service.api.service.auctionvariety.AuctionVarietyQueryService;
import com.ssafy.auction_service.docs.RestDocsSupport;
import com.ssafy.auction_service.domain.auctionvariety.PlantGrade;
import com.ssafy.auction_service.domain.auctionvariety.repository.response.AuctionVarietyResponse;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuctionVarietyApiQueryControllerDocsTest extends RestDocsSupport {

    private final AuctionVarietyQueryService auctionVarietyQueryService = mock(AuctionVarietyQueryService.class);

    @Override
    protected Object initController() {
        return new AuctionVarietyApiQueryController(auctionVarietyQueryService);
    }

    @DisplayName("경매 품종 목록 조회 API")
    @Test
    void searchAuctionVarieties() throws Exception {
        AuctionVarietySearchParam param = AuctionVarietySearchParam.builder()
            .page("1")
            .build();

        AuctionVarietyResponse auctionVariety = AuctionVarietyResponse.builder()
            .id(1L)
            .code("10000001")
            .plantCategory(PlantCategory.CUT_FLOWERS)
            .itemName("장미")
            .varietyName("하트앤소울")
            .listingNumber("00001")
            .plantGrade(PlantGrade.SUPER)
            .plantCount(10)
            .auctionStartPrice(4500)
            .region("광주")
            .shipper("김출하")
            .build();
        PageRequest pageRequest = PageRequest.of(0, 10);

        PageResponse<AuctionVarietyResponse> response = PageResponse.of(new PageImpl<>(List.of(auctionVariety), pageRequest, 1));

        given(auctionVarietyQueryService.searchAuctionVarieties(anyInt(), anyInt()))
            .willReturn(response);

        mockMvc.perform(
                get("/auction-service/auction-schedules/{auctionScheduleId}/auction-varieties", 1)
                    .header(HttpHeaders.AUTHORIZATION, "issued.access.token")
                    .queryParam("page", param.getPage())
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-auction-varieties",
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("회원 토큰")
                ),
                pathParameters(
                    parameterWithName("auctionScheduleId")
                        .description("경매 일정 ID")
                ),
                queryParameters(
                    parameterWithName("page")
                        .optional()
                        .description("페이지 번호(default: 1)")
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
                        .description("조회된 경매 품종 목록"),
                    fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER)
                        .description("경매 품종 ID"),
                    fieldWithPath("data.content[].code").type(JsonFieldType.STRING)
                        .description("품종코드"),
                    fieldWithPath("data.content[].plantCategory").type(JsonFieldType.STRING)
                        .description("화훼부류"),
                    fieldWithPath("data.content[].itemName").type(JsonFieldType.STRING)
                        .description("품목명"),
                    fieldWithPath("data.content[].varietyName").type(JsonFieldType.STRING)
                        .description("품종명"),
                    fieldWithPath("data.content[].listingNumber").type(JsonFieldType.STRING)
                        .description("경매번호"),
                    fieldWithPath("data.content[].plantGrade").type(JsonFieldType.STRING)
                        .description("화훼등급"),
                    fieldWithPath("data.content[].plantCount").type(JsonFieldType.NUMBER)
                        .description("화훼단수"),
                    fieldWithPath("data.content[].auctionStartPrice").type(JsonFieldType.NUMBER)
                        .description("경매 시작가"),
                    fieldWithPath("data.content[].region").type(JsonFieldType.STRING)
                        .description("출하 지역"),
                    fieldWithPath("data.content[].shipper").type(JsonFieldType.STRING)
                        .description("출하자"),
                    fieldWithPath("data.currentPage").type(JsonFieldType.NUMBER)
                        .description("현재 페이지"),
                    fieldWithPath("data.size").type(JsonFieldType.NUMBER)
                        .description("조회된 데이터 갯수"),
                    fieldWithPath("data.isFirst").type(JsonFieldType.BOOLEAN)
                        .description("첫 페이지 여부"),
                    fieldWithPath("data.isLast").type(JsonFieldType.BOOLEAN)
                        .description("마지막 페이지 여부")
                )
            ));
    }
}
