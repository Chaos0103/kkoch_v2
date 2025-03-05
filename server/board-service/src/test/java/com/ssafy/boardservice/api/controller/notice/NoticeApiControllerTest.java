package com.ssafy.boardservice.api.controller.notice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.boardservice.BoardServiceApiTestSupport;
import com.ssafy.boardservice.api.controller.notice.request.NoticeCreateRequest;
import com.ssafy.boardservice.api.controller.notice.request.NoticeModifyRequest;
import common.NullAndEmptyAndBlankSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NoticeApiControllerTest extends BoardServiceApiTestSupport {

    @Autowired
    public NoticeApiControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        super(mockMvc, objectMapper);
    }

    @DisplayName("신규 공지사항 등록 시 제목은 필수값이다.")
    @NullAndEmptyAndBlankSource
    @ParameterizedTest
    void createNoticeWithoutTitle(String title) throws Exception {
        NoticeCreateRequest request = NoticeCreateRequest.builder()
            .title(title)
            .content("신규 공지사항 내용입니다.")
            .toFixedDateTime("2025-12-31T18:00")
            .build();

        mockMvc.perform(
                post("/board-service/v1/notices")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("제목을 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 공지사항 등록 시 내용은 필수값이다.")
    @NullAndEmptyAndBlankSource
    @ParameterizedTest
    void createNoticeWithoutContent(String content) throws Exception {
        NoticeCreateRequest request = NoticeCreateRequest.builder()
            .title("신규 공지사항 제목입니다.")
            .content(content)
            .toFixedDateTime("2025-12-31T18:00")
            .build();

        mockMvc.perform(
                post("/board-service/v1/notices")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("내용을 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 공지사항 등록 시 고정일시는 필수값이 아니다.")
    @NullAndEmptyAndBlankSource
    @ParameterizedTest
    void createNoticeWithoutToFixedDateTime(String toFixedDateTime) throws Exception {
        NoticeCreateRequest request = NoticeCreateRequest.builder()
            .title("신규 공지사항 제목입니다.")
            .content("신규 공지사항 내용입니다.")
            .toFixedDateTime(toFixedDateTime)
            .build();

        mockMvc.perform(
                post("/board-service/v1/notices")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isCreated());
    }

    @DisplayName("신규 공지사항을 등록한다.")
    @Test
    void createNotice() throws Exception {
        NoticeCreateRequest request = NoticeCreateRequest.builder()
            .title("신규 공지사항 제목입니다.")
            .content("신규 공지사항 내용입니다.")
            .toFixedDateTime("2025-12-31T18:00")
            .build();

        mockMvc.perform(
                post("/board-service/v1/notices")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isCreated());
    }

    @DisplayName("공지사항 수정 시 제목은 필수값이다.")
    @NullAndEmptyAndBlankSource
    @ParameterizedTest
    void modifyNoticeWithoutTitle(String title) throws Exception {
        NoticeModifyRequest request = NoticeModifyRequest.builder()
            .title(title)
            .content("수정 공지사항 내용입니다.")
            .toFixedDateTime("2025-12-31T18:00")
            .build();

        mockMvc.perform(
                patch("/board-service/v1/notices/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("제목을 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("공지사항 수정 시 내용은 필수값이다.")
    @NullAndEmptyAndBlankSource
    @ParameterizedTest
    void modifyNoticeWithoutContent(String content) throws Exception {
        NoticeModifyRequest request = NoticeModifyRequest.builder()
            .title("수정 공지사항 제목입니다.")
            .content(content)
            .toFixedDateTime("2025-12-31T18:00")
            .build();

        mockMvc.perform(
                patch("/board-service/v1/notices/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("내용을 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("공지사항 수정 시 고정일시는 필수값이 아니다.")
    @NullAndEmptyAndBlankSource
    @ParameterizedTest
    void modifyNoticeWithoutToFixedDateTime(String toFixedDateTime) throws Exception {
        NoticeModifyRequest request = NoticeModifyRequest.builder()
            .title("수정 공지사항 제목입니다.")
            .content("수정 공지사항 내용입니다.")
            .toFixedDateTime(toFixedDateTime)
            .build();

        mockMvc.perform(
                patch("/board-service/v1/notices/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isOk());
    }

    @DisplayName("공지사항을 수정한다.")
    @Test
    void modifyNotice() throws Exception {
        NoticeModifyRequest request = NoticeModifyRequest.builder()
            .title("수정 공지사항 제목입니다.")
            .content("수정 공지사항 내용입니다.")
            .toFixedDateTime("2025-12-31T18:00")
            .build();

        mockMvc.perform(
                patch("/board-service/v1/notices/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isOk());
    }

    @DisplayName("공지사항을 삭제한다.")
    @Test
    void removeNotice() throws Exception {
        NoticeModifyRequest request = NoticeModifyRequest.builder()
            .title("수정 공지사항 제목입니다.")
            .content("수정 공지사항 내용입니다.")
            .toFixedDateTime("2025-12-31T18:00")
            .build();

        mockMvc.perform(
                delete("/board-service/v1/notices/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isOk());
    }
}