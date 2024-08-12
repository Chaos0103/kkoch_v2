package com.ssafy.user_service.docs.member;

import com.ssafy.user_service.api.controller.member.MemberApiController;
import com.ssafy.user_service.api.controller.member.request.*;
import com.ssafy.user_service.api.service.member.AuthService;
import com.ssafy.user_service.api.service.member.MemberService;
import com.ssafy.user_service.api.service.member.response.BankAccountAuthResponse;
import com.ssafy.user_service.api.service.member.response.MemberCreateResponse;
import com.ssafy.user_service.api.service.member.response.MemberPasswordModifyResponse;
import com.ssafy.user_service.api.service.member.response.MemberTelModifyResponse;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberApiControllerDocsTest extends RestDocsSupport {

    private final MemberService memberService = mock(MemberService.class);
    private final AuthService authService = mock(AuthService.class);

    @Override
    protected Object initController() {
        return new MemberApiController(memberService, authService);
    }

    @DisplayName("일반 회원 가입 API")
    @Test
    void createUserMember() throws Exception {
        MemberCreateRequest request = MemberCreateRequest.builder()
            .email("ssafy@ssafy.com")
            .password("ssafy1234!")
            .name("김싸피")
            .tel("01012341234")
            .businessNumber("1231212345")
            .build();

        MemberCreateResponse response = MemberCreateResponse.builder()
            .email("ss***@ssafy.com")
            .name("김싸피")
            .createdDateTime(LocalDateTime.now())
            .build();

        given(memberService.createUserMember(any()))
            .willReturn(response);

        mockMvc.perform(
                post("/members")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-user-member",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("email").type(JsonFieldType.STRING)
                        .description("이메일"),
                    fieldWithPath("password").type(JsonFieldType.STRING)
                        .description("비밀번호"),
                    fieldWithPath("name").type(JsonFieldType.STRING)
                        .description("이름"),
                    fieldWithPath("tel").type(JsonFieldType.STRING)
                        .description("연락처"),
                    fieldWithPath("businessNumber").type(JsonFieldType.STRING)
                        .description("사업자 번호")
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
                        .description("회원 가입된 이메일"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING)
                        .description("회원 가입된 이름"),
                    fieldWithPath("data.createdDateTime").type(JsonFieldType.ARRAY)
                        .description("회원 가입 일시")
                )
            ));
    }

    @DisplayName("관리자 회원 가입 API")
    @Test
    void createAdminMember() throws Exception {
        AdminMemberCreateRequest request = AdminMemberCreateRequest.builder()
            .email("ssafy@ssafy.com")
            .password("ssafy1234!")
            .name("김싸피")
            .tel("01012341234")
            .build();

        MemberCreateResponse response = MemberCreateResponse.builder()
            .email("ss***@ssafy.com")
            .name("김싸피")
            .createdDateTime(LocalDateTime.now())
            .build();

        given(memberService.createAdminMember(any()))
            .willReturn(response);

        mockMvc.perform(
                post("/members/admin")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-admin-member",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("email").type(JsonFieldType.STRING)
                        .description("이메일"),
                    fieldWithPath("password").type(JsonFieldType.STRING)
                        .description("비밀번호"),
                    fieldWithPath("name").type(JsonFieldType.STRING)
                        .description("이름"),
                    fieldWithPath("tel").type(JsonFieldType.STRING)
                        .description("연락처")
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
                        .description("회원 가입된 이메일"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING)
                        .description("회원 가입된 이름"),
                    fieldWithPath("data.createdDateTime").type(JsonFieldType.ARRAY)
                        .description("회원 가입 일시")
                )
            ));
    }

    @DisplayName("비밀번호 수정 API")
    @Test
    void modifyPassword() throws Exception {
        MemberPasswordModifyRequest request = MemberPasswordModifyRequest.builder()
            .currentPassword("ssafy1234!")
            .newPassword("ssafy5678@")
            .build();

        MemberPasswordModifyResponse response = MemberPasswordModifyResponse.builder()
            .passwordModifiedDateTime(LocalDateTime.now())
            .build();

        given(memberService.modifyPassword(anyString(), any(), any()))
            .willReturn(response);

        mockMvc.perform(
                patch("/members/password")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("modify-password",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("currentPassword").type(JsonFieldType.STRING)
                        .description("현재 비밀번호"),
                    fieldWithPath("newPassword").type(JsonFieldType.STRING)
                        .description("신규 비밀번호")
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
                    fieldWithPath("data.passwordModifiedDateTime").type(JsonFieldType.ARRAY)
                        .description("비밀번호 변경 일시")
                )
            ));
    }

    @DisplayName("연락처 수정 API")
    @Test
    void modifyTel() throws Exception {
        MemberTelModifyRequest request = MemberTelModifyRequest.builder()
            .tel("01056785678")
            .build();

        MemberTelModifyResponse response = MemberTelModifyResponse.builder()
            .modifiedTel("01056785678")
            .telModifiedDateTime(LocalDateTime.now())
            .build();

        given(memberService.modifyTel(anyString(), any(), any()))
            .willReturn(response);

        mockMvc.perform(
                patch("/members/tel")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("modify-tel",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("tel").type(JsonFieldType.STRING)
                        .description("연락처")
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
                    fieldWithPath("data.modifiedTel").type(JsonFieldType.STRING)
                        .description("변경된 연락처"),
                    fieldWithPath("data.telModifiedDateTime").type(JsonFieldType.ARRAY)
                        .description("비밀번호 변경 일시")
                )
            ));
    }

    @DisplayName("은행 계좌 1원 인증 요청 API")
    @Test
    void sendOneCoinAuthNumber() throws Exception {
        BankAccountRequest request = BankAccountRequest.builder()
            .bankCode("088")
            .accountNumber("123123123456")
            .build();

        BankAccountAuthResponse response = BankAccountAuthResponse.builder()
            .expiredDateTime(LocalDateTime.now())
            .build();

        given(authService.sendAuthNumberToBankAccount(any(), anyString(), any()))
            .willReturn(response);

        mockMvc.perform(
                post("/members/bank-account")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("send-one-coin-auth-number",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("bankCode").type(JsonFieldType.STRING)
                        .description("은행 코드"),
                    fieldWithPath("accountNumber").type(JsonFieldType.STRING)
                        .description("은행 계좌")
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
                        .description("인증 번호 만료 일시")
                )
            ));
    }
}
