package com.ssafy.auction_service.docs.variety;

import com.ssafy.auction_service.api.controller.variety.VarietyApiController;
import com.ssafy.auction_service.api.controller.variety.request.VarietyCreateRequest;
import com.ssafy.auction_service.api.controller.variety.request.VarietyModifyRequest;
import com.ssafy.auction_service.api.service.variety.VarietyService;
import com.ssafy.auction_service.api.service.variety.response.VarietyCreateResponse;
import com.ssafy.auction_service.api.service.variety.response.VarietyModifyResponse;
import com.ssafy.auction_service.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VarietyApiControllerDocsTest extends RestDocsSupport {

    private final VarietyService varietyService = mock(VarietyService.class);

    @Override
    protected Object initController() {
        return new VarietyApiController(varietyService);
    }

    @DisplayName("품종 등록 API")
    @Test
    void createVariety() throws Exception {
        VarietyCreateRequest request = VarietyCreateRequest.builder()
            .category("CUT_FLOWERS")
            .itemName("장미")
            .varietyName("하트앤소울")
            .build();

        VarietyCreateResponse response = VarietyCreateResponse.builder()
            .code("10000001")
            .plantCategory("절화")
            .itemName("장미")
            .varietyName("하트앤소울")
            .createdDateTime(LocalDateTime.now())
            .build();

        given(varietyService.createVariety(any()))
            .willReturn(response);

        mockMvc.perform(
                post("/auction-service/varieties")
                    .header(HttpHeaders.AUTHORIZATION, "issued.access.token")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-variety",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("회원 토큰")
                ),
                requestFields(
                    fieldWithPath("category").type(JsonFieldType.STRING)
                        .description("화훼부류"),
                    fieldWithPath("itemName").type(JsonFieldType.STRING)
                        .description("품목명"),
                    fieldWithPath("varietyName").type(JsonFieldType.STRING)
                        .description("품종명")
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
                    fieldWithPath("data.code").type(JsonFieldType.STRING)
                        .description("품종코드"),
                    fieldWithPath("data.plantCategory").type(JsonFieldType.STRING)
                        .description("화훼부류"),
                    fieldWithPath("data.itemName").type(JsonFieldType.STRING)
                        .description("품목명"),
                    fieldWithPath("data.varietyName").type(JsonFieldType.STRING)
                        .description("품종명"),
                    fieldWithPath("data.createdDateTime").type(JsonFieldType.ARRAY)
                        .description("품종 등록일시")
                )
            ));
    }

    @DisplayName("품종 수정 API")
    @Test
    void modifyVariety() throws Exception {
        VarietyModifyRequest request = VarietyModifyRequest.builder()
            .varietyName("하젤")
            .build();

        VarietyModifyResponse response = VarietyModifyResponse.builder()
            .code("10031204")
            .plantCategory("절화")
            .itemName("장미")
            .varietyName("하젤")
            .modifiedDateTime(LocalDateTime.now())
            .build();

        given(varietyService.modifyVariety(anyString(), anyString(), any()))
            .willReturn(response);

        mockMvc.perform(
                patch("/auction-service/varieties/{varietyCode}", "10031204")
                    .header(HttpHeaders.AUTHORIZATION, "issued.access.token")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("modify-variety",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("회원 토큰")
                ),
                pathParameters(
                    parameterWithName("varietyCode")
                        .description("품종코드")
                ),
                requestFields(
                    fieldWithPath("varietyName").type(JsonFieldType.STRING)
                        .description("품종명")
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
                    fieldWithPath("data.code").type(JsonFieldType.STRING)
                        .description("품종코드"),
                    fieldWithPath("data.plantCategory").type(JsonFieldType.STRING)
                        .description("화훼부류"),
                    fieldWithPath("data.itemName").type(JsonFieldType.STRING)
                        .description("품목명"),
                    fieldWithPath("data.varietyName").type(JsonFieldType.STRING)
                        .description("품종명"),
                    fieldWithPath("data.modifiedDateTime").type(JsonFieldType.ARRAY)
                        .description("품종 수정일시")
                )
            ));
    }
}
