package com.ssafy.board_service.api.controller.notice;

import com.ssafy.board_service.ControllerTestSupport;
import com.ssafy.board_service.api.controller.notice.param.NoticeSearchParam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NoticeQueryApiControllerTest extends ControllerTestSupport {

    @DisplayName("공지사항 목록 조회시 페이지 번호가 양수가 아니라면 기본값으로 조회한다.")
    @ValueSource(strings = {"0", "-1", "a"})
    @NullAndEmptySource
    @ParameterizedTest
    void searchNotFixedNoticesIsNotPositiveNumberPage(String page) throws Exception {
        NoticeSearchParam param = NoticeSearchParam.builder()
            .page(page)
            .keyword("점검")
            .build();

        mockMvc.perform(
                get("/board-service/notices")
                    .queryParam("page", param.getPage())
                    .queryParam("keyword", param.getKeyword())
            )
            .andExpect(status().isOk());
    }

    @DisplayName("공지사항 목록 조회시 조회 키워드는 필수값이 아니다.")
    @Test
    void searchNotFixedNoticesWithoutKeyword() throws Exception {
        NoticeSearchParam param = NoticeSearchParam.builder()
            .page("1")
            .build();

        mockMvc.perform(
                get("/board-service/notices")
                    .queryParam("page", param.getPage())
                    .queryParam("keyword", param.getKeyword())
            )
            .andExpect(status().isOk());
    }

    @DisplayName("공지사항 목록을 조회한다.")
    @Test
    void searchNotFixedNotices() throws Exception {
        NoticeSearchParam param = NoticeSearchParam.builder()
            .page("1")
            .keyword("점검")
            .build();

        mockMvc.perform(
                get("/board-service/notices")
                    .param("page", param.getPage())
                    .param("keyword", param.getKeyword())
            )
            .andExpect(status().isOk());
    }
}