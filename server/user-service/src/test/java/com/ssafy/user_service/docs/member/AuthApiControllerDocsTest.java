package com.ssafy.user_service.docs.member;

import com.ssafy.user_service.api.controller.member.AuthApiController;
import com.ssafy.user_service.api.controller.member.request.SendAuthNumberRequest;
import com.ssafy.user_service.api.controller.member.request.ValidateAuthNumberRequest;
import com.ssafy.user_service.api.service.member.AuthService;
import com.ssafy.user_service.api.service.member.response.EmailAuthResponse;
import com.ssafy.user_service.api.service.member.response.EmailValidateResponse;
import com.ssafy.user_service.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthApiControllerDocsTest extends RestDocsSupport {

    private final AuthService authService = mock(AuthService.class);

    @Override
    protected Object initController() {
        return new AuthApiController(authService);
    }

    @DisplayName("이메일 인증 번호 API")
    @Test
    void sendAuthNumber() throws Exception {
        SendAuthNumberRequest request = SendAuthNumberRequest.builder()
            .email("ssafy@ssafy.com")
            .build();

        EmailAuthResponse response = EmailAuthResponse.builder()
            .expiredDateTime(LocalDateTime.now())
            .build();

        given(authService.sendAuthNumberToEmail(anyString(), anyString(), any()))
            .willReturn(response);

        mockMvc.perform(
                post("/auth/email")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("send-auth-number",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("email").type(JsonFieldType.STRING)
                        .description("이메일")
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
                    fieldWithPath("data.expiredDateTime").type(JsonFieldType.ARRAY)
                        .description("인증 번호 만료일시")
                )
            ));
    }

    @DisplayName("이메일 인증 번호 검증 API")
    @Test
    void validateAuthNumber() throws Exception {
        ValidateAuthNumberRequest request = ValidateAuthNumberRequest.builder()
            .email("ssafy@ssafy.com")
            .authNumber("123456")
            .build();

        EmailValidateResponse response = EmailValidateResponse.builder()
            .email("ssafy@ssafy.com")
            .isAvailable(true)
            .validatedDateTime(LocalDateTime.now())
            .build();

        given(authService.validateAuthNumberToEmail(anyString(), anyString(), any()))
            .willReturn(response);

        mockMvc.perform(
                post("/auth/email/validate")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("validate-auth-number",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("email").type(JsonFieldType.STRING)
                        .description("이메일"),
                    fieldWithPath("authNumber").type(JsonFieldType.STRING)
                        .description("인증 번호")
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
                    fieldWithPath("data.email").type(JsonFieldType.STRING)
                        .description("검증한 이메일"),
                    fieldWithPath("data.isAvailable").type(JsonFieldType.BOOLEAN)
                        .description("이메일 사용 가능 여부"),
                    fieldWithPath("data.validatedDateTime").type(JsonFieldType.ARRAY)
                        .description("검증 만료일시")
                )
            ));
    }
}
