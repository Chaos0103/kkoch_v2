package com.kkoch.admin.docs.notice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kkoch.admin.api.PageResponse;
import com.kkoch.admin.api.controller.notice.NoticeApiController;
import com.kkoch.admin.api.controller.notice.request.NoticeCreateRequest;
import com.kkoch.admin.api.controller.notice.request.NoticeModifyRequest;
import com.kkoch.admin.api.service.notice.response.NoticeCreateResponse;
import com.kkoch.admin.api.service.notice.response.NoticeModifyResponse;
import com.kkoch.admin.api.service.notice.response.NoticeRemoveResponse;
import com.kkoch.admin.domain.notice.repository.response.NoticeDetailResponse;
import com.kkoch.admin.domain.notice.repository.response.NoticeResponse;
import com.kkoch.admin.api.service.notice.NoticeQueryService;
import com.kkoch.admin.api.service.notice.NoticeService;
import com.kkoch.admin.docs.RestDocsSupport;
import com.kkoch.admin.domain.notice.repository.dto.NoticeSearchCond;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class NoticeApiControllerDocsTest extends RestDocsSupport {

    private final NoticeService noticeService = mock(NoticeService.class);
    private final NoticeQueryService noticeQueryService = mock(NoticeQueryService.class);

    @Override
    protected Object initController() {
        return new NoticeApiController(noticeService, noticeQueryService);
    }

    @DisplayName("공지사항 신규 등록 API")
    @Test
    void createNotice() throws Exception {
        NoticeCreateRequest request = NoticeCreateRequest.builder()
            .title("공지사항 제목")
            .content("공지사항 내용")
            .build();

        NoticeCreateResponse response = NoticeCreateResponse.builder()
            .noticeId(1)
            .title("공지사항 제목")
            .createdDateTime(LocalDateTime.now())
            .build();

        given(noticeService.createNotice(anyInt(), any()))
            .willReturn(response);

        mockMvc.perform(
                post("/admin-service/notices")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-notice",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING)
                        .description("공지사항 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING)
                        .description("공지사항 내용")
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
                    fieldWithPath("data.noticeId").type(JsonFieldType.NUMBER)
                        .description("공지사항 ID"),
                    fieldWithPath("data.title").type(JsonFieldType.STRING)
                        .description("공지사항 제목"),
                    fieldWithPath("data.createdDateTime").type(JsonFieldType.ARRAY)
                        .description("공지사항 등록일시")
                )
            ));
    }

    @DisplayName("공지사항 목록 조회 API")
    @Test
    void searchNotices() throws Exception {
        NoticeResponse notice = createNoticeResponse();
        PageRequest pageRequest = PageRequest.of(0, 10);
        PageResponse<NoticeResponse> response = PageResponse.of(new PageImpl<>(List.of(notice), pageRequest, 1));

        given(noticeQueryService.searchNotices(any(), any()))
            .willReturn(response);

        mockMvc.perform(
                get("/admin-service/notices")
                    .queryParam("page", "1")
                    .queryParam("keyword", "점검")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-notices",
                preprocessResponse(prettyPrint()),
                requestParameters(
                    parameterWithName("page")
                        .optional()
                        .description("페이지 번호(기본값: 1)"),
                    parameterWithName("keyword")
                        .optional()
                        .description("검색 키워드")
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
                        .description("공지사항 목록"),
                    fieldWithPath("data.content[].noticeId").type(JsonFieldType.NUMBER)
                        .description("공지사항 ID"),
                    fieldWithPath("data.content[].title").type(JsonFieldType.STRING)
                        .description("공지사항 제목"),
                    fieldWithPath("data.content[].content").type(JsonFieldType.STRING)
                        .description("공지사항 내용"),
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

    @DisplayName("공지사항 상세 조회 API")
    @Test
    void searchNotice() throws Exception {
        NoticeDetailResponse response = NoticeDetailResponse.builder()
            .noticeId(1)
            .title("점검 공지사항")
            .content("content")
            .createdDateTime(LocalDateTime.now())
            .build();

        given(noticeQueryService.searchNotice(anyInt()))
            .willReturn(response);

        mockMvc.perform(
                get("/admin-service/notices/{noticeId}", 1)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-notice",
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
                    fieldWithPath("data.noticeId").type(JsonFieldType.NUMBER)
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

    @DisplayName("공지사항 수정 API")
    @Test
    void modifyNotice() throws Exception {
        NoticeModifyRequest request = NoticeModifyRequest.builder()
            .title("수정 공지사항 제목")
            .content("수정 공지사항 내용")
            .build();

        NoticeModifyResponse response = NoticeModifyResponse.builder()
            .noticeId(1)
            .modifyDateTime(LocalDateTime.now())
            .build();

        given(noticeService.modifyNotice(anyInt(), anyInt(), any()))
            .willReturn(response);

        mockMvc.perform(
                patch("/admin-service/notices/{noticeId}", 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("modify-notice",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING)
                        .description("공지사항 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING)
                        .description("공지사항 내용")
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
                    fieldWithPath("data.noticeId").type(JsonFieldType.NUMBER)
                        .description("공지사항 ID"),
                    fieldWithPath("data.modifyDateTime").type(JsonFieldType.ARRAY)
                        .description("공지사항 수정일시")
                )
            ));
    }

    @DisplayName("공지사항 삭제 API")
    @Test
    void removeNotice() throws Exception {
        NoticeRemoveResponse response = NoticeRemoveResponse.builder()
            .noticeId(1)
            .removedDateTime(LocalDateTime.now())
            .build();

        given(noticeService.removeNotice(anyInt(), anyInt()))
            .willReturn(response);

        mockMvc.perform(
                delete("/admin-service/notices/{noticeId}", 1)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("remove-notice",
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
                    fieldWithPath("data.noticeId").type(JsonFieldType.NUMBER)
                        .description("공지사항 ID"),
                    fieldWithPath("data.removedDateTime").type(JsonFieldType.ARRAY)
                        .description("공지사항 삭제일시")
                )
            ));
    }

    private NoticeResponse createNoticeResponse() {
        return NoticeResponse.builder()
            .noticeId(1)
            .title("점검 공지사항")
            .content("content")
            .createdDateTime(LocalDateTime.now())
            .build();
    }
}