package com.kkoch.admin.api.controller.notice;

import com.kkoch.admin.ControllerTestSupport;
import com.kkoch.admin.api.controller.notice.request.NoticeCreateRequest;
import com.kkoch.admin.api.controller.notice.request.NoticeModifyRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NoticeApiControllerTest extends ControllerTestSupport {

    @DisplayName("공지사항을 신규 등록시 모든 데이터는 필수값이다.")
    @CsvSource({
        ",공지사항 내용,제목을 입력해주세요.",
        "공자사항 제목,,내용을 입력해주세요."
    })
    @ParameterizedTest
    void createNoticeWithoutValue(String title, String content, String message) throws Exception {
        NoticeCreateRequest request = NoticeCreateRequest.builder()
            .title(title)
            .content(content)
            .build();

        mockMvc.perform(
                post("/admin-service/notices")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value(message))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("공지사항을 신규 등록한다.")
    @Test
    void createNotice() throws Exception {
        NoticeCreateRequest request = NoticeCreateRequest.builder()
            .title("공지사항 제목")
            .content("공지사항 내용")
            .build();

        mockMvc.perform(
                post("/admin-service/notices")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated());
    }

    @DisplayName("공지사항 목록을 조회시 페이지 번호는 양수이다.")
    @Test
    void searchNoticesIsZero() throws Exception {
        mockMvc.perform(
                get("/admin-service/notices")
                    .queryParam("page", "0")
                    .queryParam("keyword", "점검")
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("페이지 번호을 올바르게 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("공지사항 목록을 조회시 페이지 번호가 없다면 기본값으로 조회한다.")
    @Test
    void searchNoticesWithoutPage() throws Exception {
        mockMvc.perform(
                get("/admin-service/notices")
                    .queryParam("keyword", "점검")
            )
            .andExpect(status().isOk());
    }

    @DisplayName("공지사항 목록을 조회시 키워드는 필수값이 아니다.")
    @Test
    void searchNoticesWithoutKeyword() throws Exception {
        mockMvc.perform(
                get("/admin-service/notices")
                    .queryParam("page", "1")
            )
            .andExpect(status().isOk());
    }

    @DisplayName("공지사항 목록을 조회한다.")
    @Test
    void searchNotices() throws Exception {
        mockMvc.perform(
                get("/admin-service/notices")
                    .queryParam("page", "1")
                    .queryParam("keyword", "점검")
            )
            .andExpect(status().isOk());
    }

    @DisplayName("공지사항을 상세 조회한다.")
    @Test
    void searchNotice() throws Exception {
        mockMvc.perform(
                get("/admin-service/notices/{noticeId}", 1)
            )
            .andExpect(status().isOk());
    }

    @DisplayName("공지사항 수정시 모든 데이터는 필수값이다.")
    @CsvSource({
        ",공지사항 내용,제목을 입력해주세요.",
        "공자사항 제목,,내용을 입력해주세요."
    })
    @ParameterizedTest
    void modifyNoticeWithoutValue(String title, String content, String message) throws Exception {
        NoticeModifyRequest request = NoticeModifyRequest.builder()
            .title(title)
            .content(content)
            .build();

        mockMvc.perform(
                patch("/admin-service/notices/{noticeId}", 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value(message))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("공지사항을 수정한다.")
    @Test
    void modifyNotice() throws Exception {
        NoticeModifyRequest request = NoticeModifyRequest.builder()
            .title("수정할 공지사항 제목")
            .content("수정할 공지사항 내용")
            .build();

        mockMvc.perform(
                patch("/admin-service/notices/{noticeId}", 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @DisplayName("공지사항을 삭제한다.")
    @Test
    void removeNotice() throws Exception {
        mockMvc.perform(
                delete("/admin-service/notices/{noticeId}", 1)
            )
            .andExpect(status().isOk());
    }
}