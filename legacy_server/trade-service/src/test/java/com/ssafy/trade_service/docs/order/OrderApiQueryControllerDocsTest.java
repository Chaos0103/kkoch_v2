package com.ssafy.trade_service.docs.order;

import com.ssafy.trade_service.api.PageResponse;
import com.ssafy.trade_service.api.controller.order.OrderApiQueryController;
import com.ssafy.trade_service.api.service.order.OrderQueryService;
import com.ssafy.trade_service.api.service.order.response.OrderDetailResponse;
import com.ssafy.trade_service.api.service.order.response.orderdetail.BidResult;
import com.ssafy.trade_service.api.service.order.response.orderdetail.Variety;
import com.ssafy.trade_service.docs.RestDocsSupport;
import com.ssafy.trade_service.domain.order.OrderStatus;
import com.ssafy.trade_service.domain.order.repository.response.OrderResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
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

class OrderApiQueryControllerDocsTest extends RestDocsSupport {

    private final OrderQueryService orderQueryService = mock(OrderQueryService.class);

    @Override
    protected Object initController() {
        return new OrderApiQueryController(orderQueryService);
    }

    @DisplayName("주문 목록 조회 API")
    @Test
    void searchOrders() throws Exception {
        OrderResponse order = OrderResponse.builder()
            .id(1L)
            .orderStatus(OrderStatus.INIT)
            .totalPrice(10_000)
            .isPickUp(false)
            .pickUpDateTime(LocalDateTime.now())
            .orderCount(4)
            .build();
        PageRequest pageRequest = PageRequest.of(0, 10);

        given(orderQueryService.searchOrders(anyInt()))
            .willReturn(PageResponse.create(List.of(order), pageRequest, 1));

        mockMvc.perform(
                get("/trade-service/orders")
                    .header(HttpHeaders.AUTHORIZATION, "issued.access.token")
                    .queryParam("page", "1")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-orders",
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("회원 토큰")
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
                        .description("조회된 주문 목록"),
                    fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER)
                        .description("주문 ID"),
                    fieldWithPath("data.content[].orderStatus").type(JsonFieldType.STRING)
                        .description("주문 상태"),
                    fieldWithPath("data.content[].totalPrice").type(JsonFieldType.NUMBER)
                        .description("총 주문 가격"),
                    fieldWithPath("data.content[].isPickUp").type(JsonFieldType.BOOLEAN)
                        .description("픽업 여부"),
                    fieldWithPath("data.content[].pickUpDateTime").type(JsonFieldType.ARRAY)
                        .optional()
                        .description("픽업 시간"),
                    fieldWithPath("data.content[].orderCount").type(JsonFieldType.NUMBER)
                        .description("주문 품종 갯수"),
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

    @DisplayName("주문 내역 상세 조회 API")
    @Test
    void searchOrder() throws Exception {
        Variety variety = Variety.of("10031204", "CUT_FLOWERS", "장미", "하트앤소울", "SUPER", 10, "광주", "김출하");
        BidResult bidResult = BidResult.builder()
            .id(1L)
            .variety(variety)
            .bidPrice(3000)
            .bidDateTime(LocalDateTime.of(2024, 9, 18, 5, 11))
            .build();
        OrderDetailResponse response = OrderDetailResponse.builder()
            .id(1L)
            .orderStatus(OrderStatus.INIT)
            .totalPrice(96000)
            .isPickUp(false)
            .pickUpDateTime(null)
            .bidResults(List.of(bidResult))
            .build();

        given(orderQueryService.searchOrder(anyLong()))
            .willReturn(response);

        mockMvc.perform(
                get("/trade-service/orders/{orderId}", 1)
                    .header(HttpHeaders.AUTHORIZATION, "issued.access.token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-order",
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("회원 토큰")
                ),
                pathParameters(
                    parameterWithName("orderId")
                        .description("주문 ID")
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
                        .description("주문 ID"),
                    fieldWithPath("data.orderStatus").type(JsonFieldType.STRING)
                        .description("주문 상태"),
                    fieldWithPath("data.totalPrice").type(JsonFieldType.NUMBER)
                        .description("총 주문 가격"),
                    fieldWithPath("data.isPickUp").type(JsonFieldType.BOOLEAN)
                        .description("주문 상품 픽업 여부"),
                    fieldWithPath("data.pickUpDateTime").type(JsonFieldType.ARRAY)
                        .optional()
                        .description("주문 상품 픽업 시간"),
                    fieldWithPath("data.bidResults").type(JsonFieldType.ARRAY)
                        .description("낙찰 결과 목록"),
                    fieldWithPath("data.bidResults[].id").type(JsonFieldType.NUMBER)
                        .description("낙찰 결과 ID"),
                    fieldWithPath("data.bidResults[].bidPrice").type(JsonFieldType.NUMBER)
                        .description("낙찰 금액"),
                    fieldWithPath("data.bidResults[].bidDateTime").type(JsonFieldType.ARRAY)
                        .description("낙찰 일시"),
                    fieldWithPath("data.bidResults[].variety").type(JsonFieldType.OBJECT)
                        .description("낙찰된 품종 정보"),
                    fieldWithPath("data.bidResults[].variety.varietyInfo.varietyCode").type(JsonFieldType.STRING)
                        .description("품종코드"),
                    fieldWithPath("data.bidResults[].variety.varietyInfo.plantCategory").type(JsonFieldType.STRING)
                        .description("화훼부류"),
                    fieldWithPath("data.bidResults[].variety.varietyInfo.itemName").type(JsonFieldType.STRING)
                        .description("품목명"),
                    fieldWithPath("data.bidResults[].variety.varietyInfo.varietyName").type(JsonFieldType.STRING)
                        .description("품종명"),
                    fieldWithPath("data.bidResults[].variety.plantGrade").type(JsonFieldType.STRING)
                        .description("화훼등급"),
                    fieldWithPath("data.bidResults[].variety.plantCount").type(JsonFieldType.NUMBER)
                        .description("화훼단수"),
                    fieldWithPath("data.bidResults[].variety.region").type(JsonFieldType.STRING)
                        .description("출하지역"),
                    fieldWithPath("data.bidResults[].variety.shipper").type(JsonFieldType.STRING)
                        .description("출하자")
                )
            ));
    }
}
