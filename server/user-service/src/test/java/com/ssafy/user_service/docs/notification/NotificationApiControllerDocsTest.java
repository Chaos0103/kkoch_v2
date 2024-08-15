package com.ssafy.user_service.docs.notification;

import com.ssafy.user_service.api.controller.notification.NotificationApiController;
import com.ssafy.user_service.api.controller.notification.request.NotificationOpenRequest;
import com.ssafy.user_service.api.controller.notification.request.NotificationRemoveRequest;
import com.ssafy.user_service.api.service.notification.NotificationService;
import com.ssafy.user_service.api.service.notification.response.NotificationOpenResponse;
import com.ssafy.user_service.api.service.notification.response.NotificationRemoveResponse;
import com.ssafy.user_service.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NotificationApiControllerDocsTest extends RestDocsSupport {

    private final NotificationService notificationService = mock(NotificationService.class);

    @Override
    protected Object initController() {
        return new NotificationApiController(notificationService);
    }

    @DisplayName("알림 다중 열기 API")
    @Test
    void openNotifications() throws Exception {
        NotificationOpenRequest request = NotificationOpenRequest.builder()
            .ids(List.of(1L, 2L, 3L))
            .build();

        NotificationOpenResponse response = NotificationOpenResponse.builder()
            .openNotificationCount(3)
            .openStatusModifiedDateTime(LocalDateTime.now())
            .build();

        given(notificationService.openNotifications(anyList(), any()))
            .willReturn(response);

        mockMvc.perform(
                post("/notifications/open")
                    .header(HttpHeaders.AUTHORIZATION, "issued.access.token")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("open-notifications",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("회원 토큰")
                ),
                requestFields(
                    fieldWithPath("ids").type(JsonFieldType.ARRAY)
                        .description("알림 ID 목록")
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
                    fieldWithPath("data.openNotificationCount").type(JsonFieldType.NUMBER)
                        .description("오픈 알림 갯수"),
                    fieldWithPath("data.openStatusModifiedDateTime").type(JsonFieldType.ARRAY)
                        .description("알림 오픈 일시")
                )
            ));
    }

    @DisplayName("알림 다중 삭제 API")
    @Test
    void removeNotifications() throws Exception {
        NotificationRemoveRequest request = NotificationRemoveRequest.builder()
            .ids(List.of(1L, 2L, 3L))
            .build();

        NotificationRemoveResponse response = NotificationRemoveResponse.builder()
            .removedNotificationCount(3)
            .removedDateTime(LocalDateTime.now())
            .build();

        given(notificationService.removeNotifications(anyList(), any()))
            .willReturn(response);

        mockMvc.perform(
                post("/notifications/remove")
                    .header(HttpHeaders.AUTHORIZATION, "issued.access.token")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("remove-notifications",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("회원 토큰")
                ),
                requestFields(
                    fieldWithPath("ids").type(JsonFieldType.ARRAY)
                        .description("알림 ID 목록")
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
                    fieldWithPath("data.removedNotificationCount").type(JsonFieldType.NUMBER)
                        .description("삭제 알림 갯수"),
                    fieldWithPath("data.removedDateTime").type(JsonFieldType.ARRAY)
                        .description("알림 삭제 일시")
                )
            ));
    }
}
