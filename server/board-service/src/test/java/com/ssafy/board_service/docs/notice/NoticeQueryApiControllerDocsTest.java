package com.ssafy.board_service.docs.notice;

import com.ssafy.board_service.api.PageResponse;
import com.ssafy.board_service.api.controller.notice.NoticeQueryApiController;
import com.ssafy.board_service.api.controller.notice.param.NoticeSearchParam;
import com.ssafy.board_service.api.service.notice.response.FixedNoticeResponse;
import com.ssafy.board_service.api.service.notice.NoticeQueryService;
import com.ssafy.board_service.docs.RestDocsSupport;
import com.ssafy.board_service.domain.notice.repository.response.NoticeDetailResponse;
import com.ssafy.board_service.domain.notice.repository.response.NoticeResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NoticeQueryApiControllerDocsTest extends RestDocsSupport {

    private final NoticeQueryService noticeQueryService = mock(NoticeQueryService.class);

    @Override
    protected Object initController() {
        return new NoticeQueryApiController(noticeQueryService);
    }

    @DisplayName("공지사항 목록 조회 API")
    @Test
    void searchNotFixedNotices() throws Exception {
        NoticeSearchParam param = NoticeSearchParam.builder()
            .page("1")
            .keyword("점검")
            .build();

        NoticeResponse notice = NoticeResponse.builder()
            .id(1)
            .title("서버 긴급 점검 안내")
            .isFixed(false)
            .createdDateTime(LocalDateTime.of(2024, 8, 10, 10, 0, 0))
            .build();

        PageRequest pageable = PageRequest.of(0, 10);
        PageResponse<NoticeResponse> response = PageResponse.create(List.of(notice), pageable, 1);

        given(noticeQueryService.searchNotFixedNotices(anyInt(), anyString(), any()))
            .willReturn(response);

        mockMvc.perform(
                get("/board-service/notices")
                    .queryParam("page", param.getPage())
                    .queryParam("keyword", param.getKeyword())
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-not-fixed-notices",
                preprocessResponse(prettyPrint()),
                queryParameters(
                    parameterWithName("page")
                        .optional()
                        .description("페이지 번호(default: 1)"),
                    parameterWithName("keyword")
                        .optional()
                        .description("조회 키워드")
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
                        .description("조회된 공지사항 목록"),
                    fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER)
                        .description("공지사항 ID"),
                    fieldWithPath("data.content[].title").type(JsonFieldType.STRING)
                        .description("공지사항 제목"),
                    fieldWithPath("data.content[].isFixed").type(JsonFieldType.BOOLEAN)
                        .description("공지사항 고정 여부"),
                    fieldWithPath("data.content[].createdDateTime").type(JsonFieldType.ARRAY)
                        .description("공지사항 등록일시"),
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

    @DisplayName("고정된 공지사항 목록 조회 API")
    @Test
    void searchFixedNotices() throws Exception {
        NoticeResponse notice = NoticeResponse.builder()
            .id(1)
            .title("서버 긴급 점검 안내")
            .isFixed(true)
            .createdDateTime(LocalDateTime.of(2024, 8, 10, 10, 0, 0))
            .build();

        FixedNoticeResponse response = FixedNoticeResponse.builder()
            .content(List.of(notice))
            .build();

        given(noticeQueryService.searchFixedNotices(any()))
            .willReturn(response);

        mockMvc.perform(
                get("/board-service/notices/fixed")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-fixed-notices",
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
                        .description("조회된 공지사항 목록"),
                    fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER)
                        .description("공지사항 ID"),
                    fieldWithPath("data.content[].title").type(JsonFieldType.STRING)
                        .description("공지사항 제목"),
                    fieldWithPath("data.content[].isFixed").type(JsonFieldType.BOOLEAN)
                        .description("공지사항 고정 여부"),
                    fieldWithPath("data.content[].createdDateTime").type(JsonFieldType.ARRAY)
                        .description("공지사항 등록일시"),
                    fieldWithPath("data.size").type(JsonFieldType.NUMBER)
                        .description("조회된 데이터 갯수")
                )
            ));
    }

    @DisplayName("공지사항 상세 조회 API")
    @Test
    void searchNotice() throws Exception {
        NoticeDetailResponse response = NoticeDetailResponse.builder()
            .id(1)
            .title("서버 긴급 점검 안내")
            .content("<h1>서버 긴급 점검 안내</h1>")
            .createdDateTime(LocalDateTime.of(2024, 8, 10, 10, 0, 0))
            .build();

        given(noticeQueryService.searchNotice(anyInt()))
            .willReturn(response);

        mockMvc.perform(
                get("/board-service/notices/{noticeId}", 1)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-notice",
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("noticeId")
                        .description("공지사항 ID")
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
                        .description("공지사항 ID"),
                    fieldWithPath("data.title").type(JsonFieldType.STRING)
                        .description("공지사항 제목"),
                    fieldWithPath("data.content").type(JsonFieldType.STRING)
                        .description("공지사항 내용"),
                    fieldWithPath("data.createdDateTime").type(JsonFieldType.ARRAY)
                        .description("공지사항 등록일시")
                )
            ));
    }
}
