package com.kkoch.admin.docs.stats;

import com.kkoch.admin.api.ListResponse;
import com.kkoch.admin.api.controller.stats.StatsApiController;
import com.kkoch.admin.api.service.stats.StatsQueryService;
import com.kkoch.admin.docs.RestDocsSupport;
import com.kkoch.admin.domain.Grade;
import com.kkoch.admin.domain.stats.repository.response.StatsResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StatsApiControllerDocsTest extends RestDocsSupport {

    private final StatsQueryService statsQueryService = mock(StatsQueryService.class);

    @Override
    protected Object initController() {
        return new StatsApiController(statsQueryService);
    }

    @DisplayName("통계 목록 조회 API")
    @Test
    void searchStats() throws Exception {
        StatsResponse stats = StatsResponse.builder()
            .avgPrice(3500)
            .maxPrice(4000)
            .minPrice(3000)
            .grade(Grade.SUPER)
            .plantCount(10)
            .itemName("장미")
            .varietyName("하트앤소울")
            .createdDateTime(LocalDateTime.now())
            .build();
        ListResponse<StatsResponse> response = ListResponse.of(List.of(stats));

        given(statsQueryService.searchStats(any(), any()))
            .willReturn(response);

        mockMvc.perform(
                get("/admin-service/stats")
                    .queryParam("varietyCode", "10000001")
                    .queryParam("period", "7")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-stats",
                preprocessResponse(prettyPrint()),
                requestParameters(
                    parameterWithName("varietyCode")
                        .description("품종코드"),
                    parameterWithName("period")
                        .optional()
                        .description("조회 기간(기본값: 1)")
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
                        .description("통계 목록"),
                    fieldWithPath("data.content[].avgPrice").type(JsonFieldType.NUMBER)
                        .description("평균 낙찰가"),
                    fieldWithPath("data.content[].maxPrice").type(JsonFieldType.NUMBER)
                        .description("최대 낙찰가"),
                    fieldWithPath("data.content[].minPrice").type(JsonFieldType.NUMBER)
                        .description("최소 낙찰가"),
                    fieldWithPath("data.content[].grade").type(JsonFieldType.STRING)
                        .description("등급"),
                    fieldWithPath("data.content[].plantCount").type(JsonFieldType.NUMBER)
                        .description("단수"),
                    fieldWithPath("data.content[].itemName").type(JsonFieldType.STRING)
                        .description("품목명"),
                    fieldWithPath("data.content[].varietyName").type(JsonFieldType.STRING)
                        .description("품종명"),
                    fieldWithPath("data.content[].createdDateTime").type(JsonFieldType.ARRAY)
                        .description("통계 등록일시"),
                    fieldWithPath("data.size").type(JsonFieldType.NUMBER)
                        .description("조회된 데이터 갯수")
                )
            ));
    }
}
