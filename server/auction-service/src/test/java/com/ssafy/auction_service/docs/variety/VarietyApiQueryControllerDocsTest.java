package com.ssafy.auction_service.docs.variety;

import com.ssafy.auction_service.api.PageResponse;
import com.ssafy.auction_service.api.controller.variety.VarietyApiQueryController;
import com.ssafy.auction_service.api.controller.variety.param.VarietySearchParam;
import com.ssafy.auction_service.api.service.variety.VarietyQueryService;
import com.ssafy.auction_service.docs.RestDocsSupport;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import com.ssafy.auction_service.domain.variety.repository.response.VarietyResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
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

class VarietyApiQueryControllerDocsTest extends RestDocsSupport {

    private final VarietyQueryService varietyQueryService = mock(VarietyQueryService.class);

    @Override
    protected Object initController() {
        return new VarietyApiQueryController(varietyQueryService);
    }

    @DisplayName("품종 목록 조회 API")
    @Test
    void searchVarieties() throws Exception {
        VarietySearchParam param = VarietySearchParam.builder()
            .page("1")
            .plantCategory("CUT_FLOWERS")
            .itemName("장미")
            .build();

        VarietyResponse auctionVariety = VarietyResponse.builder()
            .code("10000001")
            .plantCategory(PlantCategory.CUT_FLOWERS)
            .itemName("장미")
            .varietyName("하트앤소울")
            .build();
        PageRequest pageRequest = PageRequest.of(0, 10);

        PageResponse<VarietyResponse> response = PageResponse.of(new PageImpl<>(List.of(auctionVariety), pageRequest, 1));

        given(varietyQueryService.searchVarieties(any(), anyInt()))
            .willReturn(response);

        mockMvc.perform(
                get("/auction-service/varieties")
                    .header(HttpHeaders.AUTHORIZATION, "issued.access.token")
                    .queryParam("page", param.getPage())
                    .queryParam("plantCategory", param.getPlantCategory())
                    .queryParam("itemName", param.getItemName())
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-varieties",
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("회원 토큰")
                ),
                queryParameters(
                    parameterWithName("page")
                        .optional()
                        .description("페이지 번호(default: 1)"),
                    parameterWithName("plantCategory")
                        .description("화훼부류"),
                    parameterWithName("itemName")
                        .optional()
                        .description("품목명")
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
                        .description("조회된 품종 목록"),
                    fieldWithPath("data.content[].code").type(JsonFieldType.STRING)
                        .description("품종코드"),
                    fieldWithPath("data.content[].plantCategory").type(JsonFieldType.STRING)
                        .description("화훼부류"),
                    fieldWithPath("data.content[].itemName").type(JsonFieldType.STRING)
                        .description("품목명"),
                    fieldWithPath("data.content[].varietyName").type(JsonFieldType.STRING)
                        .description("품종명"),
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
