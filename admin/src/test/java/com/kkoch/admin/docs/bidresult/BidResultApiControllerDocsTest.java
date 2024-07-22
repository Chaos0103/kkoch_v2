package com.kkoch.admin.docs.bidresult;

import com.kkoch.admin.api.PageResponse;
import com.kkoch.admin.api.controller.bidresult.BidResultApiController;
import com.kkoch.admin.api.controller.bidresult.request.BidResultCreateRequest;
import com.kkoch.admin.api.service.bidresult.BidResultQueryService;
import com.kkoch.admin.api.service.bidresult.BidResultService;
import com.kkoch.admin.docs.RestDocsSupport;
import com.kkoch.admin.domain.Grade;
import com.kkoch.admin.domain.bidresult.repository.response.BidResultResponse;
import com.kkoch.admin.domain.variety.PlantCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BidResultApiControllerDocsTest extends RestDocsSupport {

    private final BidResultService bidResultService = mock(BidResultService.class);
    private final BidResultQueryService bidResultQueryService = mock(BidResultQueryService.class);

    @Override
    protected Object initController() {
        return new BidResultApiController(bidResultService, bidResultQueryService);
    }

    @DisplayName("낙찰 신규 등록 API")
    @Test
    void createBidResult() throws Exception {
        BidResultCreateRequest request = BidResultCreateRequest.builder()
            .auctionVarietyId(1)
            .bidPrice(3500)
            .bidDateTime(LocalDateTime.of(2024, 7, 12, 6, 11))
            .build();

        given(bidResultService.createBidResult(anyString(), any()))
            .willReturn(1L);

        mockMvc.perform(
                post("/admin-service/bid-results/{memberKey}", UUID.randomUUID().toString())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-bid-result",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("auctionVarietyId").type(JsonFieldType.NUMBER)
                        .description("경매품종 ID"),
                    fieldWithPath("bidPrice").type(JsonFieldType.NUMBER)
                        .description("낙찰가"),
                    fieldWithPath("bidDateTime").type(JsonFieldType.ARRAY)
                        .description("낙찰일시")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.NUMBER)
                        .description("응답 데이터")
                )
            ));
    }

    @DisplayName("낙찰 결과 목록 조회 API")
    @Test
    void searchBidResults() throws Exception {
        BidResultResponse bidResult = BidResultResponse.builder()
            .plantCategory(PlantCategory.CUT_FLOWERS)
            .itemName("장미")
            .varietyName("하트앤소울")
            .grade(Grade.SUPER)
            .plantCount(10)
            .bidPrice(45)
            .bidDateTime(LocalDateTime.of(2024, 7, 12, 5, 34))
            .region("광주")
            .build();

        PageRequest pageRequest = PageRequest.of(0, 10);
        PageResponse<BidResultResponse> response = PageResponse.create(List.of(bidResult), pageRequest, 1);

        given(bidResultQueryService.searchBidResultsBy(any(), any()))
            .willReturn(response);

        mockMvc.perform(
                get("/admin-service/bid-results")
                    .queryParam("page", "1")
                    .queryParam("startDate", "2024-07-11")
                    .queryParam("endDate", "2024-07-12")
                    .queryParam("plantCategory", "CUT_FLOWERS")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-bid-results",
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
                    fieldWithPath("data.content").type(JsonFieldType.ARRAY)
                        .description("낙찰 결과 목록"),
                    fieldWithPath("data.content[].plantCategory").type(JsonFieldType.STRING)
                        .description("화훼부류"),
                    fieldWithPath("data.content[].itemName").type(JsonFieldType.STRING)
                        .description("품목명"),
                    fieldWithPath("data.content[].varietyName").type(JsonFieldType.STRING)
                        .description("품종명"),
                    fieldWithPath("data.content[].grade").type(JsonFieldType.STRING)
                        .description("등급"),
                    fieldWithPath("data.content[].plantCount").type(JsonFieldType.NUMBER)
                        .description("단수"),
                    fieldWithPath("data.content[].bidPrice").type(JsonFieldType.NUMBER)
                        .description("낙찰 가격"),
                    fieldWithPath("data.content[].bidDateTime").type(JsonFieldType.ARRAY)
                        .description("낙찰 시간"),
                    fieldWithPath("data.content[].region").type(JsonFieldType.STRING)
                        .description("출하지역"),
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
