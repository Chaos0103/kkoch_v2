package com.kkoch.user.docs.alarm;

import com.kkoch.user.api.controller.alarm.AlarmController;
import com.kkoch.user.domain.alarm.repository.response.AlarmResponse;
import com.kkoch.user.api.service.alarm.AlamService;
import com.kkoch.user.api.service.alarm.AlarmQueryService;
import com.kkoch.user.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AlarmControllerDocsTest extends RestDocsSupport {

    private final AlamService alamService = mock(AlamService.class);
    private final AlarmQueryService alarmQueryService = mock(AlarmQueryService.class);

    @Override
    protected Object initController() {
        return new AlarmController(alamService, alarmQueryService);
    }

    @DisplayName("알림 목록 조회 API")
    @Test
    void searchAlarms() throws Exception {
        AlarmResponse response1 = createAlarmResponse(1L, LocalDateTime.of(2023, 8, 8, 5, 0));
        AlarmResponse response2 = createAlarmResponse(2L, LocalDateTime.of(2023, 8, 8, 6, 0));
        AlarmResponse response3 = createAlarmResponse(3L, LocalDateTime.of(2023, 8, 8, 7, 0));
        List<AlarmResponse> responses = List.of(response3, response2, response1);

        given(alamService.openAllAlarm(anyString()))
            .willReturn(3);

        given(alarmQueryService.searchAlarms(anyString()))
            .willReturn(responses);

        mockMvc.perform(
                get("/{memberKey}/alarms", generateMemberKey())
                    .header("Authorization", "token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-alarms",
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.ARRAY)
                        .description("응답 데이터"),
                    fieldWithPath("data[].alarmId").type(JsonFieldType.NUMBER)
                        .description("알림 식별키"),
                    fieldWithPath("data[].content").type(JsonFieldType.STRING)
                        .description("알림 내용"),
                    fieldWithPath("data[].isOpened").type(JsonFieldType.BOOLEAN)
                        .description("알림 열람 여부"),
                    fieldWithPath("data[].createdDateTime").type(JsonFieldType.ARRAY)
                        .description("알림 등록일시")
                )
            ));
    }

    private AlarmResponse createAlarmResponse(Long alarmId, LocalDateTime createdDateTime) {
        return AlarmResponse.builder()
            .alarmId(alarmId)
            .content("alarm content")
            .isOpened(false)
            .createdDateTime(createdDateTime)
            .build();
    }
}
