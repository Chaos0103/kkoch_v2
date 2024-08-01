package com.kkoch.admin.docs.trade;

import com.kkoch.admin.api.PageResponse;
import com.kkoch.admin.api.controller.trade.TradeApiController;
import com.kkoch.admin.api.service.trade.TradeQueryService;
import com.kkoch.admin.api.service.trade.response.TradeDetailResponse;
import com.kkoch.admin.docs.RestDocsSupport;
import com.kkoch.admin.domain.Grade;
import com.kkoch.admin.domain.trade.repository.response.TradeResponse;
import com.kkoch.admin.domain.variety.PlantCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TradeApiControllerDocsTest extends RestDocsSupport {

    private final TradeQueryService tradeQueryService = mock(TradeQueryService.class);

    @Override
    protected Object initController() {
        return new TradeApiController(tradeQueryService);
    }

    @DisplayName("거래 내역 목록 조회 API")
    @Test
    void searchTrades() throws Exception {
        TradeResponse trade = TradeResponse.builder()
            .tradeId(1L)
            .totalPrice(100_000)
            .bidResultCount(10)
            .isPickUp(true)
            .tradeDateTime(LocalDateTime.of(2024, 7, 12, 7, 40))
            .auctionDateTime(LocalDateTime.of(2024, 7, 12, 5, 0))
            .build();
        PageRequest pageRequest = PageRequest.of(0, 15);
        PageImpl<TradeResponse> page = new PageImpl<>(List.of(trade), pageRequest, 1);

        given(tradeQueryService.searchTrades(anyString(), any()))
            .willReturn(PageResponse.of(page));

        mockMvc.perform(
                get("/admin-service/trades/{memberKey}", UUID.randomUUID().toString())
                    .queryParam("page", "1")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-trades",
                preprocessResponse(prettyPrint()),
                requestParameters(
                    parameterWithName("page")
                        .optional()
                        .description("페이지 번호(기본값: 1)")
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
                        .description("거래 내역 목록"),
                    fieldWithPath("data.content[].tradeId").type(JsonFieldType.NUMBER)
                        .description("거래 내역 ID"),
                    fieldWithPath("data.content[].totalPrice").type(JsonFieldType.NUMBER)
                        .description("거래 총 가격"),
                    fieldWithPath("data.content[].bidResultCount").type(JsonFieldType.NUMBER)
                        .description("낙찰 받은 갯수"),
                    fieldWithPath("data.content[].isPickUp").type(JsonFieldType.BOOLEAN)
                        .description("픽업 여부"),
                    fieldWithPath("data.content[].tradeDateTime").type(JsonFieldType.ARRAY)
                        .optional()
                        .description("거래 일시"),
                    fieldWithPath("data.content[].auctionDateTime").type(JsonFieldType.ARRAY)
                        .description("거래한 경매 일시"),
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

    @DisplayName("거래 내역 상세 조회 API")
    @Test
    void searchTrade() throws Exception {
        TradeDetailResponse.InnerBidResult result = TradeDetailResponse.InnerBidResult.builder()
            .varietyCode("10000001")
            .itemName("장미")
            .varietyName("하트앤소울")
            .grade(Grade.SUPER)
            .plantCount(10)
            .bidPrice(3500)
            .bidDateTime(LocalDateTime.of(2024, 7, 12, 6, 30))
            .build();

        TradeDetailResponse response = TradeDetailResponse.builder()
            .tradeId(1L)
            .totalPrice(100_000)
            .tradeDateTime(LocalDateTime.of(2024, 7, 12, 7, 0))
            .isPickUp(false)
            .auctionSchedule(TradeDetailResponse.InnerAuctionSchedule.builder()
                .plantCategory(PlantCategory.CUT_FLOWERS)
                .auctionDateTime(LocalDateTime.of(2024, 7, 12, 5, 0))
                .build())
            .results(List.of(result))
            .build();

        given(tradeQueryService.searchTrade(anyLong()))
            .willReturn(response);

        mockMvc.perform(
                get("/admin-service/trades/{tradeId}/results", 1L)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-trade",
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
                    fieldWithPath("data.tradeId").type(JsonFieldType.NUMBER)
                        .description("거래 내역 ID"),
                    fieldWithPath("data.totalPrice").type(JsonFieldType.NUMBER)
                        .description("총 거래 가격"),
                    fieldWithPath("data.tradeDateTime").type(JsonFieldType.ARRAY)
                        .optional()
                        .description("거래 일자"),
                    fieldWithPath("data.isPickUp").type(JsonFieldType.BOOLEAN)
                        .description("낙찰 품목 픽업 여부"),
                    fieldWithPath("data.auctionSchedule.plantCategory").type(JsonFieldType.STRING)
                        .description("경매 화훼부류"),
                    fieldWithPath("data.auctionSchedule.auctionDateTime").type(JsonFieldType.ARRAY)
                        .description("경매 진행 일시"),
                    fieldWithPath("data.results").type(JsonFieldType.ARRAY)
                        .description("낙찰 내역 목록"),
                    fieldWithPath("data.results[].varietyCode").type(JsonFieldType.STRING)
                        .description("낙찰 받은 품종코드"),
                    fieldWithPath("data.results[].itemName").type(JsonFieldType.STRING)
                        .description("낙찰 받은 품목명"),
                    fieldWithPath("data.results[].varietyName").type(JsonFieldType.STRING)
                        .description("낙찰 받은 품종명"),
                    fieldWithPath("data.results[].grade").type(JsonFieldType.STRING)
                        .description("낙찰 받은 품종 등급"),
                    fieldWithPath("data.results[].plantCount").type(JsonFieldType.NUMBER)
                        .description("낙찰 받은 단수"),
                    fieldWithPath("data.results[].bidPrice").type(JsonFieldType.NUMBER)
                        .description("낙찰가"),
                    fieldWithPath("data.results[].bidDateTime").type(JsonFieldType.ARRAY)
                        .description("낙찰일시")
                )
            ));
    }
}
