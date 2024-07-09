package com.kkoch.user.docs.pointlog;

import com.kkoch.user.api.controller.pointlog.PointLogController;
import com.kkoch.user.api.controller.pointlog.request.PointLogCreateRequest;
import com.kkoch.user.api.service.pointlog.PointLogService;
import com.kkoch.user.api.service.pointlog.response.PointLogCreateResponse;
import com.kkoch.user.docs.RestDocsSupport;
import com.kkoch.user.domain.pointlog.Bank;
import com.kkoch.user.domain.pointlog.PointStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PointLogControllerDocsTest extends RestDocsSupport {

    private final PointLogService pointLogService = mock(PointLogService.class);

    @Override
    protected Object initController() {
        return new PointLogController(pointLogService);
    }

    @DisplayName("신규 포인트 내역 등록 API")
    @Test
    void createPointLog() throws Exception {
        PointLogCreateRequest request = PointLogCreateRequest.builder()
            .bank("SHINHAN")
            .amount(100000)
            .status("CHARGE")
            .build();

        PointLogCreateResponse response = PointLogCreateResponse.builder()
            .pointLogId(1L)
            .bank(Bank.SHINHAN)
            .amount(100000)
            .status(PointStatus.CHARGE)
            .balance(100000L)
            .build();

        given(pointLogService.createPointLog(anyString(), any()))
            .willReturn(response);

        mockMvc.perform(
                post("/{memberKey}/points", generateMemberKey())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", "token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("create-point-log",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("bank").type(JsonFieldType.STRING)
                        .description("은행정보"),
                    fieldWithPath("amount").type(JsonFieldType.NUMBER)
                        .description("금액"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("포인트 상태(CHARGE: 충전, RETURN: 반환, PAYMENT: 결제)")
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
                    fieldWithPath("data.pointLogId").type(JsonFieldType.NUMBER)
                        .description("포인트 내역 식별키"),
                    fieldWithPath("data.bank").type(JsonFieldType.STRING)
                        .description("은행정보"),
                    fieldWithPath("data.amount").type(JsonFieldType.NUMBER)
                        .description("금액"),
                    fieldWithPath("data.status").type(JsonFieldType.STRING)
                        .description("포인트 상태(CHARGE: 충전, RETURN: 반환, PAYMENT: 결제)"),
                    fieldWithPath("data.balance").type(JsonFieldType.NUMBER)
                        .description("잔여 포인트")
                )
            ));
    }
}
