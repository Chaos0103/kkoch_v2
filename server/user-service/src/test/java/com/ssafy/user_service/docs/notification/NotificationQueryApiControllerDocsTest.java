package com.ssafy.user_service.docs.notification;

import com.ssafy.user_service.api.PageResponse;
import com.ssafy.user_service.api.controller.notification.NotificationQueryApiController;
import com.ssafy.user_service.api.controller.notification.param.NotificationSearchParam;
import com.ssafy.user_service.api.controller.notification.param.SentNotificationSearchParam;
import com.ssafy.user_service.api.service.notification.NotificationQueryService;
import com.ssafy.user_service.docs.RestDocsSupport;
import com.ssafy.user_service.domain.membernotification.repository.response.NotificationResponse;
import com.ssafy.user_service.domain.notification.NotificationCategory;
import com.ssafy.user_service.domain.notification.repository.response.SentNotificationResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NotificationQueryApiControllerDocsTest extends RestDocsSupport {

    private final NotificationQueryService notificationQueryService = mock(NotificationQueryService.class);

    @Override
    protected Object initController() {
        return new NotificationQueryApiController(notificationQueryService);
    }

    @DisplayName("회원 알림 목록 조회 API")
    @Test
    void searchNotifications() throws Exception {
        NotificationSearchParam param = NotificationSearchParam.builder()
            .page("1")
            .category("AUCTION")
            .build();

        NotificationResponse response2 = NotificationResponse.builder()
            .id(2L)
            .category(NotificationCategory.PAYMENT)
            .content("1,000,000원이 결제되었습니다.")
            .isOpened(false)
            .notificationDateTime(LocalDateTime.of(2024, 7, 15, 10, 0))
            .build();
        NotificationResponse response1 = NotificationResponse.builder()
            .id(1L)
            .category(NotificationCategory.AUCTION)
            .content("2024년 7월 12일 오전 5:00에 절화 경매가 진행될 예정입니다.")
            .isOpened(true)
            .notificationDateTime(LocalDateTime.of(2024, 7, 14, 7, 0))
            .build();
        List<NotificationResponse> content = List.of(response2, response1);

        PageRequest pageRequest = PageRequest.of(0, 10);
        PageResponse<NotificationResponse> response = PageResponse.create(content, pageRequest, 2);

        given(notificationQueryService.searchNotifications(anyString(), anyString(), anyInt()))
            .willReturn(response);

        mockMvc.perform(
                get("/notifications")
                    .header(HttpHeaders.AUTHORIZATION, "issued.access.token")
                    .queryParam("page", param.getPage())
                    .queryParam("category", param.getCategory())
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-notifications",
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("회원 토큰")
                ),
                queryParameters(
                    parameterWithName("page")
                        .optional()
                        .description("페이지 번호(default: 1)"),
                    parameterWithName("category")
                        .optional()
                        .description("카테고리")
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
                        .description("조회된 알림 목록"),
                    fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER)
                        .description("알림 식별키"),
                    fieldWithPath("data.content[].category").type(JsonFieldType.STRING)
                        .description("알림 카테고리"),
                    fieldWithPath("data.content[].content").type(JsonFieldType.STRING)
                        .description("알림 내용"),
                    fieldWithPath("data.content[].isOpened").type(JsonFieldType.BOOLEAN)
                        .description("알림 열람 상태"),
                    fieldWithPath("data.content[].notificationDateTime").type(JsonFieldType.ARRAY)
                        .description("알림 일시"),
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

    @DisplayName("보낸 알림 목록 조회 API")
    @Test
    void searchSentNotifications() throws Exception {
        SentNotificationSearchParam param = SentNotificationSearchParam.builder()
            .page("1")
            .from(LocalDate.of(2024, 1, 1))
            .to(LocalDate.of(2024, 2, 1))
            .build();

        SentNotificationResponse response2 = SentNotificationResponse.builder()
            .id(2L)
            .category(NotificationCategory.PAYMENT)
            .content("1,000,000원이 결제되었습니다.")
            .sentMemberCount(1)
            .sentDateTime(LocalDateTime.of(2024, 7, 15, 10, 0))
            .build();
        SentNotificationResponse response1 = SentNotificationResponse.builder()
            .id(1L)
            .category(NotificationCategory.AUCTION)
            .content("2024년 7월 12일 오전 5:00에 절화 경매가 진행될 예정입니다.")
            .sentMemberCount(10)
            .sentDateTime(LocalDateTime.of(2024, 7, 14, 7, 0))
            .build();
        List<SentNotificationResponse> content = List.of(response2, response1);

        PageRequest pageRequest = PageRequest.of(0, 10);
        PageResponse<SentNotificationResponse> response = PageResponse.create(content, pageRequest, 2);

        given(notificationQueryService.searchSentNotifications(any(), anyInt()))
            .willReturn(response);

        mockMvc.perform(
                get("/notifications/sent")
                    .header(HttpHeaders.AUTHORIZATION, "issued.access.token")
                    .queryParam("page", param.getPage())
                    .queryParam("from", String.valueOf(param.getFrom()))
                    .queryParam("to", String.valueOf(param.getTo()))
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-sent-notifications",
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("회원 토큰")
                ),
                queryParameters(
                    parameterWithName("page")
                        .optional()
                        .description("페이지 번호(default: 1)"),
                    parameterWithName("from")
                        .optional()
                        .description("조회 시작일"),
                    parameterWithName("to")
                        .optional()
                        .description("조회 종료일")
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
                        .description("조회된 알림 목록"),
                    fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER)
                        .description("알림 식별키"),
                    fieldWithPath("data.content[].category").type(JsonFieldType.STRING)
                        .description("알림 카테고리"),
                    fieldWithPath("data.content[].content").type(JsonFieldType.STRING)
                        .description("알림 내용"),
                    fieldWithPath("data.content[].sentMemberCount").type(JsonFieldType.NUMBER)
                        .description("알림을 보낸 회원수"),
                    fieldWithPath("data.content[].sentDateTime").type(JsonFieldType.ARRAY)
                        .description("알림 전송 일시"),
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
}
