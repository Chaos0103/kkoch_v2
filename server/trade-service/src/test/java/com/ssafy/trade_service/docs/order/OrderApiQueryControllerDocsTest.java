package com.ssafy.trade_service.docs.order;

import com.ssafy.trade_service.api.PageResponse;
import com.ssafy.trade_service.api.controller.order.OrderApiQueryController;
import com.ssafy.trade_service.api.service.order.OrderQueryService;
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
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
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
}
