package com.ssafy.boardservice.api.controller.notice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.boardservice.BoardServiceApiTestSupport;
import com.ssafy.boardservice.api.controller.notice.request.param.NoticeSearchParam;
import common.NullAndEmptyAndBlankSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NoticeQueryApiControllerTest extends BoardServiceApiTestSupport {

    @Autowired
    public NoticeQueryApiControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        super(mockMvc, objectMapper);
    }

    @DisplayName("공지사항 목록 조회 시 키워드는 필수값이 아니다.")
    @NullAndEmptyAndBlankSource
    @ParameterizedTest
    void searchNotFixedNoticesWithoutKeyword(String keyword) throws Exception {
        NoticeSearchParam param = NoticeSearchParam.builder()
            .keyword(keyword)
            .page("1")
            .build();

        mockMvc.perform(
                get("/board-service/v1/notices")
                    .queryParam("keyword", param.getKeyword())
                    .queryParam("page", param.getPage())
            )
            .andExpect(status().isOk());
    }

    @DisplayName("공지사항 목록 조회 시 페이지 번호는 필수값이 아니다.")
    @NullAndEmptyAndBlankSource
    @ParameterizedTest
    void searchNotFixedNoticesWithoutPage(String page) throws Exception {
        NoticeSearchParam param = NoticeSearchParam.builder()
            .keyword("제목")
            .page(page)
            .build();

        mockMvc.perform(
                get("/board-service/v1/notices")
                    .queryParam("keyword", param.getKeyword())
                    .queryParam("page", param.getPage())
            )
            .andExpect(status().isOk());
    }

    @DisplayName("키워드와 페이지 번호를 입력 받아 공지사항 목록을 조회한다.")
    @Test
    void searchNotFixedNotices() throws Exception {
        NoticeSearchParam param = NoticeSearchParam.builder()
            .keyword("제목")
            .page("1")
            .build();

        mockMvc.perform(
                get("/board-service/v1/notices")
                    .queryParam("keyword", param.getKeyword())
                    .queryParam("page", param.getPage())
            )
            .andExpect(status().isOk());
    }

    @DisplayName("고정된 공지사항 목록을 조회한다.")
    @Test
    void searchFixedNotices() throws Exception {
        mockMvc.perform(
                get("/board-service/v1/notices/fixed")
            )
            .andExpect(status().isOk());
    }

    @DisplayName("공지사항을 조회한다.")
    @Test
    void searchNotice() throws Exception {
        mockMvc.perform(
                get("/board-service/v1/notices/1")
            )
            .andExpect(status().isOk());
    }
}