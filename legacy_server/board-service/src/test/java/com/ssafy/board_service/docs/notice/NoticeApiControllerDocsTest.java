package com.ssafy.board_service.docs.notice;

import com.ssafy.board_service.api.controller.notice.NoticeApiController;
import com.ssafy.board_service.api.controller.notice.request.NoticeCreateRequest;
import com.ssafy.board_service.api.controller.notice.request.NoticeModifyRequest;
import com.ssafy.board_service.api.service.notice.NoticeService;
import com.ssafy.board_service.api.service.notice.response.NoticeCreateResponse;
import com.ssafy.board_service.api.service.notice.response.NoticeModifyResponse;
import com.ssafy.board_service.api.service.notice.response.NoticeRemoveResponse;
import com.ssafy.board_service.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NoticeApiControllerDocsTest extends RestDocsSupport {

    private final NoticeService noticeService = mock(NoticeService.class);

    @Override
    protected Object initController() {
        return new NoticeApiController(noticeService);
    }

    @DisplayName("공지사항 등록 API")
    @Test
    void createNotice() throws Exception {
        NoticeCreateRequest request = NoticeCreateRequest.builder()
            .title("서비스 점검 안내")
            .content("<h1>2024.08.15 00:00 ~ 07:00 서비스 점검 예정입니다.</h1>")
            .toFixedDateTime("2024-08-15T07:00:00")
            .build();

        NoticeCreateResponse response = NoticeCreateResponse.builder()
            .id(1)
            .title("서비스 점검 안내")
            .isFixed(true)
            .createdDateTime(LocalDateTime.now())
            .build();

        given(noticeService.createNotice(any(), any()))
            .willReturn(response);

        mockMvc.perform(
                post("/board-service/notices")
                    .header(HttpHeaders.AUTHORIZATION, "issued.access.token")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-notice",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("회원 토큰")
                ),
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING)
                        .description("공지사항 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING)
                        .description("공지사항 내용"),
                    fieldWithPath("toFixedDateTime").type(JsonFieldType.STRING)
                        .optional()
                        .description("상단 고정 종료일시(yyyy-MM-ddThh:mm:ss")
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
                    fieldWithPath("data.isFixed").type(JsonFieldType.BOOLEAN)
                        .description("상단 고정 여부"),
                    fieldWithPath("data.createdDateTime").type(JsonFieldType.ARRAY)
                        .description("공지사항 등록일시")
                )
            ));
    }

    @DisplayName("공지사항 수정 API")
    @Test
    void modifyNotice() throws Exception {
        NoticeModifyRequest request = NoticeModifyRequest.builder()
            .title("서비스 점검 안내")
            .content("<h1>2024.08.15 00:00 ~ 07:00 서비스 점검 예정입니다.</h1>")
            .toFixedDateTime("2024-08-15T07:00:00")
            .build();

        NoticeModifyResponse response = NoticeModifyResponse.builder()
            .id(1)
            .title("서비스 점검 안내")
            .isFixed(true)
            .modifiedDateTime(LocalDateTime.now())
            .build();

        given(noticeService.modifyNotice(anyInt(), any(), any()))
            .willReturn(response);

        mockMvc.perform(
                patch("/board-service/notices/{noticeId}", 1)
                    .header(HttpHeaders.AUTHORIZATION, "issued.access.token")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("modify-notice",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("회원 토큰")
                ),
                pathParameters(
                    parameterWithName("noticeId")
                        .description("공지사항 ID")
                ),
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING)
                        .description("공지사항 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING)
                        .description("공지사항 내용"),
                    fieldWithPath("toFixedDateTime").type(JsonFieldType.STRING)
                        .optional()
                        .description("상단 고정 종료일시(yyyy-MM-ddThh:mm:ss")
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
                    fieldWithPath("data.isFixed").type(JsonFieldType.BOOLEAN)
                        .description("상단 고정 여부"),
                    fieldWithPath("data.modifiedDateTime").type(JsonFieldType.ARRAY)
                        .description("공지사항 수정일시")
                )
            ));
    }

    @DisplayName("공지사항 삭제 API")
    @Test
    void removeNotice() throws Exception {
        NoticeRemoveResponse response = NoticeRemoveResponse.builder()
            .id(1)
            .title("서비스 점검 안내")
            .removedDateTime(LocalDateTime.now())
            .build();

        given(noticeService.removeNotice(anyInt(), any()))
            .willReturn(response);

        mockMvc.perform(
                delete("/board-service/notices/{noticeId}", 1)
                    .header(HttpHeaders.AUTHORIZATION, "issued.access.token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("remove-notice",
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("회원 토큰")
                ),
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
                    fieldWithPath("data.removedDateTime").type(JsonFieldType.ARRAY)
                        .description("공지사항 삭제일시")
                )
            ));
    }
}
