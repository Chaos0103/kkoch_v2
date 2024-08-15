package com.ssafy.user_service.api.controller.notification;

import com.ssafy.user_service.ControllerTestSupport;
import com.ssafy.user_service.api.controller.notification.request.NotificationOpenRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NotificationApiControllerTest extends ControllerTestSupport {

    @DisplayName("알림 다중 열기시 알림 ID 목록은 필수값이다.")
    @NullAndEmptySource
    @ParameterizedTest
    void searchNotificationsWithoutIds(List<Long> ids) throws Exception {
        NotificationOpenRequest request = NotificationOpenRequest.builder()
            .ids(ids)
            .build();

        mockMvc.perform(
                post("/notifications/open")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("알림 ID를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("알림 다중 열기를 한다.")
    @Test
    void searchNotifications() throws Exception {
        NotificationOpenRequest request = NotificationOpenRequest.builder()
            .ids(List.of(1L, 2L, 3L))
            .build();

        mockMvc.perform(
                post("/notifications/open")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isOk());
    }
}