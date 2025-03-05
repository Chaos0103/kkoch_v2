package com.ssafy.userservice.api.controller.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.userservice.UserServiceApiTestSupport;
import com.ssafy.userservice.api.controller.member.request.*;
import com.ssafy.userservice.domain.member.vo.BankAccount;
import common.NullAndEmptyAndBlankSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
class MemberApiControllerTest extends UserServiceApiTestSupport {

    @Autowired
    public MemberApiControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        super(mockMvc, objectMapper);
    }

    @WithAnonymousUser
    @DisplayName("신규 회원 등록 시 이메일은 필수값이다.")
    @NullAndEmptyAndBlankSource
    @ParameterizedTest
    void createMemberWithoutEmail(String email) throws Exception {
        MemberCreateRequest request = MemberCreateRequest.builder()
            .email(email)
            .password("ssafy1234!")
            .name("김싸피")
            .tel("01012341234")
            .role("USER")
            .build();

        mockMvc.perform(
                post("/v1/members")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("이메일을 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @WithAnonymousUser
    @DisplayName("신규 회원 등록 시 비밀번호는 필수값이다.")
    @NullAndEmptyAndBlankSource
    @ParameterizedTest
    void createMemberWithoutPassword(String password) throws Exception {
        MemberCreateRequest request = MemberCreateRequest.builder()
            .email("ssafy@gmail.com")
            .password(password)
            .name("김싸피")
            .tel("01012341234")
            .role("USER")
            .build();

        mockMvc.perform(
                post("/v1/members")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("비밀번호를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @WithAnonymousUser
    @DisplayName("신규 회원 등록 시 이름은 필수값이다.")
    @NullAndEmptyAndBlankSource
    @ParameterizedTest
    void createMemberWithoutName(String name) throws Exception {
        MemberCreateRequest request = MemberCreateRequest.builder()
            .email("ssafy@gmail.com")
            .password("ssafy1234!")
            .name(name)
            .tel("01012341234")
            .role("USER")
            .build();

        mockMvc.perform(
                post("/v1/members")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("이름을 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @WithAnonymousUser
    @DisplayName("신규 회원 등록 시 연락처는 필수값이다.")
    @NullAndEmptyAndBlankSource
    @ParameterizedTest
    void createMemberWithoutTel(String tel) throws Exception {
        MemberCreateRequest request = MemberCreateRequest.builder()
            .email("ssafy@gmail.com")
            .password("ssafy1234!")
            .name("김싸피")
            .tel(tel)
            .role("USER")
            .build();

        mockMvc.perform(
                post("/v1/members")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("연락처를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @WithAnonymousUser
    @DisplayName("신규 회원 등록 시 회원 권한은 필수값이다.")
    @NullAndEmptyAndBlankSource
    @ParameterizedTest
    void createMemberWithoutRole(String role) throws Exception {
        MemberCreateRequest request = MemberCreateRequest.builder()
            .email("ssafy@gmail.com")
            .password("ssafy1234!")
            .name("김싸피")
            .tel("01012341234")
            .role(role)
            .build();

        mockMvc.perform(
                post("/v1/members")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("회원 권한을 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @WithAnonymousUser
    @DisplayName("신규 회원을 등록한다.")
    @Test
    void createMember() throws Exception {
        MemberCreateRequest request = MemberCreateRequest.builder()
            .email("ssafy@gmail.com")
            .password("ssafy1234!")
            .name("김싸피")
            .tel("01012341234")
            .role("USER")
            .build();

        mockMvc.perform(
                post("/v1/members")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .with(csrf())
            )
            .andExpect(status().isCreated());
    }

    @DisplayName("회원 비밀번호 수정 시 현재 비밀번호는 필수값이다.")
    @NullAndEmptyAndBlankSource
    @ParameterizedTest
    void modifyPasswordWithoutCurrentPassword(String currentPassword) throws Exception {
        MemberPasswordModifyRequest request = MemberPasswordModifyRequest.builder()
            .currentPassword(currentPassword)
            .newPassword("new5678@")
            .build();

        mockMvc.perform(
                patch("/v1/members/password")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("현재 비밀번호를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("회원 비밀번호 수정 시 신규 비밀번호는 필수값이다.")
    @NullAndEmptyAndBlankSource
    @ParameterizedTest
    void modifyPasswordWithoutNewPassword(String newPassword) throws Exception {
        MemberPasswordModifyRequest request = MemberPasswordModifyRequest.builder()
            .currentPassword("old1234!")
            .newPassword(newPassword)
            .build();

        mockMvc.perform(
                patch("/v1/members/password")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("신규 비밀번호를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("회원의 비밀번호를 수정한다.")
    @Test
    void modifyPassword() throws Exception {
        MemberPasswordModifyRequest request = MemberPasswordModifyRequest.builder()
            .currentPassword("old1234!")
            .newPassword("new5678@")
            .build();

        mockMvc.perform(
                patch("/v1/members/password")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .with(csrf())
            )
            .andExpect(status().isOk());
    }

    @DisplayName("회원 연락처 수정 시 연락처는 필수값이다.")
    @NullAndEmptyAndBlankSource
    @ParameterizedTest
    void modifyTelWithoutTel(String tel) throws Exception {
        MemberTelModifyRequest request = MemberTelModifyRequest.builder()
            .tel(tel)
            .build();

        mockMvc.perform(
                patch("/v1/members/tel")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("연락처를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("회원의 연락처를 수정한다.")
    @Test
    void modifyTel() throws Exception {
        MemberTelModifyRequest request = MemberTelModifyRequest.builder()
            .tel("01012345678")
            .build();

        mockMvc.perform(
                patch("/v1/members/tel")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .with(csrf())
            )
            .andExpect(status().isOk());
    }

    @DisplayName("회원 사업자 번호 등록 시 사업자 번호는 필수값이다.")
    @NullAndEmptyAndBlankSource
    @ParameterizedTest
    void registerBusinessNumberWithoutBusinessNumber(String businessNumber) throws Exception {
        RegisterBusinessNumberRequest request = RegisterBusinessNumberRequest.builder()
            .businessNumber(businessNumber)
            .build();

        mockMvc.perform(
                post("/v1/members/business-number")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("사업자 번호를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("회원의 사업자 번호를 등록한다.")
    @Test
    void registerBusinessNumber() throws Exception {
        RegisterBusinessNumberRequest request = RegisterBusinessNumberRequest.builder()
            .businessNumber("1231212345")
            .build();

        mockMvc.perform(
                post("/v1/members/business-number")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .with(csrf())
            )
            .andExpect(status().isOk());
    }

    @DisplayName("회원 은행 계좌 인증 번호 발송 시 은행 코드는 필수값이다.")
    @NullAndEmptyAndBlankSource
    @ParameterizedTest
    void sendBankAccountAuthenticationNumberWithoutBankCode(String bankCode) throws Exception {
        SendBankAccountAuthenticationNumberRequest request = SendBankAccountAuthenticationNumberRequest.builder()
            .bankCode(bankCode)
            .accountNumber("123123123456")
            .build();

        mockMvc.perform(
                post("/v1/members/bank-account")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("은행 코드를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("회원 은행 계좌 인증 번호 발송 시 은행 계좌는 필수값이다.")
    @NullAndEmptyAndBlankSource
    @ParameterizedTest
    void sendBankAccountAuthenticationNumberWithoutAccountNumber(String accountNumber) throws Exception {
        SendBankAccountAuthenticationNumberRequest request = SendBankAccountAuthenticationNumberRequest.builder()
            .bankCode("088")
            .accountNumber(accountNumber)
            .build();

        mockMvc.perform(
                post("/v1/members/bank-account")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("은행 계좌를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("회원 은행 계좌 수정을 위해 인증 번호를 발송한다.")
    @Test
    void sendBankAccountAuthenticationNumber() throws Exception {
        SendBankAccountAuthenticationNumberRequest request = SendBankAccountAuthenticationNumberRequest.builder()
            .bankCode("088")
            .accountNumber("123123123456")
            .build();

        mockMvc.perform(
                post("/v1/members/bank-account")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .with(csrf())
            )
            .andExpect(status().isOk());
    }

    @DisplayName("은행 계좌 수정 시 인증 번호는 필수값이다.")
    @NullAndEmptyAndBlankSource
    @ParameterizedTest
    void modifyBankAccountWithoutAuthenticationNumber(String authenticationNumber) throws Exception {
        MemberBankAccountModifyRequest request = MemberBankAccountModifyRequest.builder()
            .authenticationNumber(authenticationNumber)
            .build();

        mockMvc.perform(
                patch("/v1/members/bank-account")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("인증 번호를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("회원의 은행 계좌를 수정한다.")
    @Test
    void modifyBankAccount() throws Exception {
        MemberBankAccountModifyRequest request = MemberBankAccountModifyRequest.builder()
            .authenticationNumber("123")
            .build();

        BankAccount bankAccount = BankAccount.builder()
            .bankCode("088")
            .accountNumber("123123123456")
            .build();

        given(authenticationService.checkAuthenticationNumberToBankAccount(anyString(), anyString()))
            .willReturn(bankAccount);

        mockMvc.perform(
                patch("/v1/members/bank-account")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .with(csrf())
            )
            .andExpect(status().isOk());
    }

    @DisplayName("회원 탈퇴 시 비밀번호는 필수값이다.")
    @NullAndEmptyAndBlankSource
    @ParameterizedTest
    void removeMemberWithoutPassword(String password) throws Exception {
        MemberRemoveRequest request = MemberRemoveRequest.builder()
            .password(password)
            .build();

        mockMvc.perform(
                post("/v1/members/withdraw")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("비밀번호를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("회원 탈퇴를 한다.")
    @Test
    void removeMember() throws Exception {
        MemberRemoveRequest request = MemberRemoveRequest.builder()
            .password("ssafy1234!")
            .build();

        mockMvc.perform(
                post("/v1/members/withdraw")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .with(csrf())
            )
            .andExpect(status().isOk());
    }
}