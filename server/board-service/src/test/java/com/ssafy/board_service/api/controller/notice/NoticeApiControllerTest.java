package com.ssafy.board_service.api.controller.notice;

import com.ssafy.board_service.ControllerTestSupport;
import com.ssafy.board_service.api.controller.notice.request.NoticeCreateRequest;
import com.ssafy.board_service.api.controller.notice.request.NoticeModifyRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NoticeApiControllerTest extends ControllerTestSupport {

    @DisplayName("공지사항 등록시 공지사항 제목은 필수값이다.")
    @NullAndEmptySource
    @ParameterizedTest
    void createNoticeWithoutTitle(String title) throws Exception {
        NoticeCreateRequest request = NoticeCreateRequest.builder()
            .title(title)
            .content("<h1>공지사항 내용입니다.</h1>")
            .toFixedDateTime("2024-01-01T07:00:00")
            .build();

        mockMvc.perform(
                post("/board-service/notices")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("공지사항 제목을 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("공지사항 등록시 공지사항 내용은 필수값이다.")
    @NullAndEmptySource
    @ParameterizedTest
    void createNoticeWithoutContent(String content) throws Exception {
        NoticeCreateRequest request = NoticeCreateRequest.builder()
            .title("공지사항 제목입니다.")
            .content(content)
            .toFixedDateTime("2024-01-01T07:00:00")
            .build();

        mockMvc.perform(
                post("/board-service/notices")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("공지사항 내용을 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("공지사항 등록시 상단 고정 종료일시는 필수값이 아니다.")
    @Test
    void createNoticeWithoutToFixedDateTime() throws Exception {
        NoticeCreateRequest request = NoticeCreateRequest.builder()
            .title("공지사항 제목입니다.")
            .content("<h1>공지사항 내용입니다.</h1>")
            .toFixedDateTime(null)
            .build();

        mockMvc.perform(
                post("/board-service/notices")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated());
    }

    @DisplayName("공지사항 등록을 한다.")
    @Test
    void createNotice() throws Exception {
        NoticeCreateRequest request = NoticeCreateRequest.builder()
            .title("공지사항 제목입니다.")
            .content("<h1>공지사항 내용입니다.</h1>")
            .toFixedDateTime("2024-01-01T07:00:00")
            .build();

        mockMvc.perform(
                post("/board-service/notices")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated());
    }

    @DisplayName("공지사항 수정시 공지사항 제목은 필수값이다.")
    @NullAndEmptySource
    @ParameterizedTest
    void modifyNoticeWithoutTitle(String title) throws Exception {
        NoticeModifyRequest request = NoticeModifyRequest.builder()
            .title(title)
            .content("<h1>공지사항 내용입니다.</h1>")
            .toFixedDateTime("2024-01-01T07:00:00")
            .build();

        mockMvc.perform(
                patch("/board-service/notices/{noticeId}", 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("공지사항 제목을 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("공지사항 수정시 공지사항 내용은 필수값이다.")
    @NullAndEmptySource
    @ParameterizedTest
    void modifyNoticeWithoutContent(String content) throws Exception {
        NoticeModifyRequest request = NoticeModifyRequest.builder()
            .title("공지사항 제목입니다.")
            .content(content)
            .toFixedDateTime("2024-01-01T07:00:00")
            .build();

        mockMvc.perform(
                patch("/board-service/notices/{noticeId}", 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("공지사항 내용을 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("공지사항 수정시 상단 고정 종료일시는 필수값이 아니다.")
    @Test
    void modifyNoticeWithoutToFixedDateTime() throws Exception {
        NoticeModifyRequest request = NoticeModifyRequest.builder()
            .title("공지사항 제목입니다.")
            .content("<h1>공지사항 내용입니다.</h1>")
            .toFixedDateTime(null)
            .build();

        mockMvc.perform(
                patch("/board-service/notices/{noticeId}", 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated());
    }

    @DisplayName("공지사항 수정을 한다.")
    @Test
    void modifyNotice() throws Exception {
        NoticeModifyRequest request = NoticeModifyRequest.builder()
            .title("공지사항 제목입니다.")
            .content("<h1>공지사항 내용입니다.</h1>")
            .toFixedDateTime("2024-01-01T07:00:00")
            .build();

        mockMvc.perform(
                patch("/board-service/notices/{noticeId}", 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated());
    }
}