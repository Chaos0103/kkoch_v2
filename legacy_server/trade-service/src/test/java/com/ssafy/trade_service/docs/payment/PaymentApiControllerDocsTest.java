package com.ssafy.trade_service.docs.payment;

import com.ssafy.trade_service.api.controller.payment.PaymentApiController;
import com.ssafy.trade_service.api.service.payment.PaymentCreateResponse;
import com.ssafy.trade_service.api.service.payment.PaymentService;
import com.ssafy.trade_service.docs.RestDocsSupport;
import com.ssafy.trade_service.domain.payment.PaymentStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PaymentApiControllerDocsTest extends RestDocsSupport {

    private final PaymentService paymentService = mock(PaymentService.class);

    @Override
    protected Object initController() {
        return new PaymentApiController(paymentService);
    }

    @DisplayName("주문 결제 API")
    @Test
    void payment() throws Exception {
        PaymentCreateResponse response = PaymentCreateResponse.builder()
            .id(1L)
            .paymentAmount(100_000)
            .paymentStatus(PaymentStatus.PAYMENT_COMPLETED)
            .paymentDataTime(LocalDateTime.now())
            .build();

        given(paymentService.payment(anyLong(), any()))
            .willReturn(response);

        mockMvc.perform(
                post("/trade-service/orders/{orderId}/payment", 1)
                    .header(HttpHeaders.AUTHORIZATION, "issued.access.token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("payment",
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
                        .description("경매 예약 ID"),
                    fieldWithPath("data.paymentAmount").type(JsonFieldType.NUMBER)
                        .description("결제 금액"),
                    fieldWithPath("data.paymentStatus").type(JsonFieldType.STRING)
                        .description("결제 상태"),
                    fieldWithPath("data.paymentDataTime").type(JsonFieldType.ARRAY)
                        .description("결제 일시")
                )
            ));
    }
}
