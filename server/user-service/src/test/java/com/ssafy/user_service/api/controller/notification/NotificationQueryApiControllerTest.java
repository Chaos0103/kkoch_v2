package com.ssafy.user_service.api.controller.notification;

import com.ssafy.user_service.ControllerTestSupport;
import com.ssafy.user_service.api.controller.notification.param.NotificationSearchParam;
import com.ssafy.user_service.api.controller.notification.param.SentNotificationSearchParam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NotificationQueryApiControllerTest extends ControllerTestSupport {

    @DisplayName("회원의 알림 목록 조회시 페이지 번호가 양수가 아니라면 1로 조회한다.")
    @ValueSource(strings = {"0", "-1", "a"})
    @NullAndEmptySource
    @ParameterizedTest
    void searchNotificationsIsNotPositiveNumberPage(String page) throws Exception {
        NotificationSearchParam param = NotificationSearchParam.builder()
            .page(page)
            .category("AUCTION")
            .build();

        mockMvc.perform(
                get("/notifications")
                    .queryParam("page", param.getPage())
                    .queryParam("category", param.getCategory())
                    .with(csrf())
            )
            .andExpect(status().isOk());
    }

    @DisplayName("회원의 알림 목록 조회시 카테고리는 필수값이 아니다.")
    @Test
    void searchNotificationsWithoutCategory() throws Exception {
        NotificationSearchParam param = NotificationSearchParam.builder()
            .page("1")
            .build();

        mockMvc.perform(
                get("/notifications")
                    .queryParam("page", param.getPage())
                    .queryParam("category", param.getCategory())
                    .with(csrf())
            )
            .andExpect(status().isOk());
    }

    @DisplayName("회원의 알림 목록을 조회한다.")
    @Test
    void searchNotifications() throws Exception {
        NotificationSearchParam param = NotificationSearchParam.builder()
            .page("1")
            .category("AUCTION")
            .build();

        mockMvc.perform(
                get("/notifications")
                    .queryParam("page", param.getPage())
                    .queryParam("category", param.getCategory())
                    .with(csrf())
            )
            .andExpect(status().isOk());
    }

    @DisplayName("보낸 알림 목록 조회시 페이지 번호가 양수가 아니라면 1로 조회한다.")
    @ValueSource(strings = {"0", "-1", "a"})
    @NullAndEmptySource
    @ParameterizedTest
    void searchSentNotificationsIsNotPositiveNumberPage(String page) throws Exception {
        SentNotificationSearchParam param = SentNotificationSearchParam.builder()
            .page(page)
            .from(LocalDate.of(2024, 1, 1))
            .to(LocalDate.of(2024, 2, 1))
            .build();

        mockMvc.perform(
                get("/notifications/sent")
                    .queryParam("page", param.getPage())
                    .queryParam("from", String.valueOf(param.getFrom()))
                    .queryParam("to", String.valueOf(param.getTo()))
                    .with(csrf())
            )
            .andExpect(status().isOk());
    }

    @DisplayName("보낸 알림 목록 조회시 페이지 번호가 양수가 아니라면 1로 조회한다.")
    @Test
    void searchSentNotifications() throws Exception {
        SentNotificationSearchParam param = SentNotificationSearchParam.builder()
            .page("1")
            .from(LocalDate.of(2024, 1, 1))
            .to(LocalDate.of(2024, 2, 1))
            .build();

        mockMvc.perform(
                get("/notifications/sent")
                    .queryParam("page", param.getPage())
                    .queryParam("from", String.valueOf(param.getFrom()))
                    .queryParam("to", String.valueOf(param.getTo()))
                    .with(csrf())
            )
            .andExpect(status().isOk());
    }
}