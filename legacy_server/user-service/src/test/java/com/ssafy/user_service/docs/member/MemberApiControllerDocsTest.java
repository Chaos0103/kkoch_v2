package com.ssafy.user_service.docs.member;

import com.ssafy.user_service.api.controller.member.MemberApiController;
import com.ssafy.user_service.api.controller.member.request.*;
import com.ssafy.user_service.api.service.member.AuthService;
import com.ssafy.user_service.api.service.member.MemberService;
import com.ssafy.user_service.api.service.member.response.*;
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

class MemberApiControllerDocsTest extends RestDocsSupport {

    private final MemberService memberService = mock(MemberService.class);
    private final AuthService authService = mock(AuthService.class);

    @Override
    protected Object initController() {
        return new MemberApiController(memberService, authService);
    }

    @DisplayName("일반 회원 가입 API")
    @Test
    void createMember() throws Exception {
        MemberCreateRequest request = MemberCreateRequest.builder()
            .email("ssafy@ssafy.com")
            .password("ssafy1234!")
            .name("김싸피")
            .tel("01012341234")
            .role("USER")
            .build();

        MemberCreateResponse response = MemberCreateResponse.builder()
            .email("ss***@ssafy.com")
            .name("김싸피")
            .createdDateTime(LocalDateTime.now())
            .build();

        given(memberService.createMember(any()))
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
                    fieldWithPath("role").type(JsonFieldType.STRING)
                        .description("회원 구분")
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

    @DisplayName("사업자 번호 등록 API")
    @Test
    void registerBusinessNumber() throws Exception {
        RegisterBusinessNumberRequest request = RegisterBusinessNumberRequest.builder()
            .businessNumber("1231212345")
            .build();

        RegisterBusinessNumberResponse response = RegisterBusinessNumberResponse.builder()
            .businessNumber("1231212345")
            .registeredDateTime(LocalDateTime.now())
            .build();

        given(memberService.registerBusinessNumber(anyString(), any(), any()))
            .willReturn(response);

        mockMvc.perform(
                post("/members/business-number")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("register-business-number",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
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
                    fieldWithPath("data.businessNumber").type(JsonFieldType.STRING)
                        .description("사업자 번호"),
                    fieldWithPath("data.registeredDateTime").type(JsonFieldType.ARRAY)
                        .description("사업자 번호 등록 일시")
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

    @DisplayName("은행 계좌 수정 API")
    @Test
    void modifyBankAccount() throws Exception {
        MemberAdditionalInfoModifyRequest request = MemberAdditionalInfoModifyRequest.builder()
            .bankCode("088")
            .accountNumber("123123123456")
            .authNumber("012")
            .build();

        MemberBankAccountModifyResponse response = MemberBankAccountModifyResponse.builder()
            .bankCode("088")
            .accountNumber("123***123456")
            .bankAccountModifiedDateTime(LocalDateTime.now())
            .build();

        given(authService.validateAuthNumberToBankAccount(any(), anyString()))
            .willReturn(true);

        given(memberService.modifyBankAccount(anyString(), any(), any()))
            .willReturn(response);

        mockMvc.perform(
                patch("/members/bank-account")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("modify-bank-account",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("bankCode").type(JsonFieldType.STRING)
                        .description("은행 코드"),
                    fieldWithPath("accountNumber").type(JsonFieldType.STRING)
                        .description("은행 계좌"),
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
                    fieldWithPath("data.bankCode").type(JsonFieldType.STRING)
                        .description("은행 코드"),
                    fieldWithPath("data.accountNumber").type(JsonFieldType.STRING)
                        .description("은행 계좌"),
                    fieldWithPath("data.bankAccountModifiedDateTime").type(JsonFieldType.ARRAY)
                        .description("은행 계좌 수정 일시")
                )
            ));
    }

    @DisplayName("회원 탈퇴 API")
    @Test
    void removeMember() throws Exception {
        MemberRemoveRequest request = MemberRemoveRequest.builder()
            .password("ssafy1234!")
            .build();

        MemberRemoveResponse response = MemberRemoveResponse.builder()
            .withdrawnDateTime(LocalDateTime.now())
            .build();

        given(memberService.removeMember(anyString(), anyString(), any()))
            .willReturn(response);

        mockMvc.perform(
                post("/members/withdraw")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("remove-member",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("password").type(JsonFieldType.STRING)
                        .description("비밀번호")
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
                    fieldWithPath("data.withdrawnDateTime").type(JsonFieldType.ARRAY)
                        .description("회원 탈퇴 일시")
                )
            ));
    }
}
