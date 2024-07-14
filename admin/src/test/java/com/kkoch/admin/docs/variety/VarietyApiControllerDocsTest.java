package com.kkoch.admin.docs.variety;

import com.kkoch.admin.api.controller.VarietyApiController;
import com.kkoch.admin.api.controller.request.VarietyCreateRequest;
import com.kkoch.admin.api.service.variety.VarietyQueryService;
import com.kkoch.admin.api.service.variety.VarietyService;
import com.kkoch.admin.api.service.variety.response.VarietyCreateResponse;
import com.kkoch.admin.docs.RestDocsSupport;
import com.kkoch.admin.domain.variety.PlantCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VarietyApiControllerDocsTest extends RestDocsSupport {

    private final VarietyService varietyService = mock(VarietyService.class);
    private final VarietyQueryService varietyQueryService = mock(VarietyQueryService.class);

    @Override
    protected Object initController() {
        return new VarietyApiController(varietyService, varietyQueryService);
    }

    @DisplayName("품종 신규 등록 API")
    @Test
    void createVariety() throws Exception {
        VarietyCreateRequest request = VarietyCreateRequest.builder()
            .plantCategory("CUT_FLOWERS")
            .itemName("장미")
            .varietyName("하트앤소울")
            .build();

        VarietyCreateResponse response = VarietyCreateResponse.builder()
            .varietyCode("10000001")
            .plantCategory(PlantCategory.CUT_FLOWERS)
            .itemName("장미")
            .varietyName("하트앤소울")
            .createdDateTime(LocalDateTime.now())
            .build();

        given(varietyService.createVariety(anyInt(), any()))
            .willReturn(response);

        mockMvc.perform(
                post("/admin-service/varieties")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-variety",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("plantCategory").type(JsonFieldType.STRING)
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
                    fieldWithPath("data.varietyCode").type(JsonFieldType.STRING)
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

    @DisplayName("품목명 목록 조회 API")
    @Test
    void searchItemNames() throws Exception {
        List<String> response = List.of("국화", "장미");

        given(varietyQueryService.searchItemNames(anyString()))
            .willReturn(response);

        mockMvc.perform(
                get("/admin-service/varieties/items")
                    .queryParam("category", "CUT_FLOWERS")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-item-names",
                preprocessResponse(prettyPrint()),
                requestParameters(
                    parameterWithName("category")
                        .optional()
                        .description("화훼부류(기본값: CUT_FLOWERS)")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.ARRAY)
                        .description("응답 데이터")
                )
            ));
    }

    @DisplayName("품종명 목록 조회 API")
    @Test
    void searchVarietyNames() throws Exception {
        List<String> response = List.of("고르키파크", "하트앤소울");

        given(varietyQueryService.searchVarietyNames(anyString()))
            .willReturn(response);

        mockMvc.perform(
                get("/admin-service/varieties/names")
                    .queryParam("item", "장미")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-variety-names",
                preprocessResponse(prettyPrint()),
                requestParameters(
                    parameterWithName("item")
                        .description("품목명")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.ARRAY)
                        .description("응답 데이터")
                )
            ));
    }
}
