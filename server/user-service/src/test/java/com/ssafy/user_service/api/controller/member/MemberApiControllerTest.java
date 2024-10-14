package com.ssafy.user_service.api.controller.member;

import com.ssafy.user_service.ControllerTestSupport;
import com.ssafy.user_service.api.controller.member.request.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberApiControllerTest extends ControllerTestSupport {

    @DisplayName("일반 회원 가입시 이메일은 필수값이다.")
    @Test
    void createMemberWithoutEmail() throws Exception {
        MemberCreateRequest request = MemberCreateRequest.builder()
            .password("ssafy1234!")
            .name("김싸피")
            .tel("01012341234")
            .role("USER")
            .build();

        mockMvc.perform(
                post("/members")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("이메일을 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("일반 회원 가입시 비밀번호는 필수값이다.")
    @Test
    void createMemberWithoutPassword() throws Exception {
        MemberCreateRequest request = MemberCreateRequest.builder()
            .email("ssafy@ssafy.com")
            .name("김싸피")
            .tel("01012341234")
            .role("USER")
            .build();

        mockMvc.perform(
                post("/members")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("비밀번호를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("일반 회원 가입시 이름은 필수값이다.")
    @Test
    void createMemberWithoutName() throws Exception {
        MemberCreateRequest request = MemberCreateRequest.builder()
            .email("ssafy@ssafy.com")
            .password("ssafy1234!")
            .tel("01012341234")
            .role("USER")
            .build();

        mockMvc.perform(
                post("/members")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("이름을 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("일반 회원 가입시 연락처는 필수값이다.")
    @Test
    void createMemberWithoutTel() throws Exception {
        MemberCreateRequest request = MemberCreateRequest.builder()
            .email("ssafy@ssafy.com")
            .password("ssafy1234!")
            .name("김싸피")
            .role("USER")
            .build();

        mockMvc.perform(
                post("/members")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("연락처를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("일반 회원 가입시 회원 구분은 필수값이다.")
    @Test
    void createMemberWithoutBusinessNumber() throws Exception {
        MemberCreateRequest request = MemberCreateRequest.builder()
            .email("ssafy@ssafy.com")
            .password("ssafy1234!")
            .name("김싸피")
            .tel("01012341234")
            .build();

        mockMvc.perform(
                post("/members")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("회원 구분을 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("일반 회원 가입을 한다.")
    @Test
    void createMember() throws Exception {
        MemberCreateRequest request = MemberCreateRequest.builder()
            .email("ssafy@ssafy.com")
            .password("ssafy1234!")
            .name("김싸피")
            .tel("01012341234")
            .role("USER")
            .build();

        mockMvc.perform(
                post("/members")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isCreated());
    }

    @DisplayName("비밀번호 수정시 현재 비밀번호는 필수값이다.")
    @Test
    void modifyPasswordWithoutCurrentPassword() throws Exception {
        MemberPasswordModifyRequest request = MemberPasswordModifyRequest.builder()
            .newPassword("ssafy5678@")
            .build();

        mockMvc.perform(
                patch("/members/password")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("현재 비밀번호를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("비밀번호 수정시 현재 비밀번호는 필수값이다.")
    @Test
    void modifyPasswordWithoutNewPassword() throws Exception {
        MemberPasswordModifyRequest request = MemberPasswordModifyRequest.builder()
            .currentPassword("ssafy1234!")
            .build();

        mockMvc.perform(
                patch("/members/password")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("신규 비밀번호를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("비밀번호 수정을 한다.")
    @Test
    void modifyPassword() throws Exception {
        MemberPasswordModifyRequest request = MemberPasswordModifyRequest.builder()
            .currentPassword("ssafy1234!")
            .newPassword("ssafy5678@")
            .build();

        mockMvc.perform(
                patch("/members/password")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isOk());
    }

    @DisplayName("연락처 수정시 연락처는 필수값이다.")
    @Test
    void modifyTelWithoutTel() throws Exception {
        MemberTelModifyRequest request = MemberTelModifyRequest.builder()
            .build();

        mockMvc.perform(
                patch("/members/tel")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("연락처를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("연락처 수정을 한다.")
    @Test
    void modifyTel() throws Exception {
        MemberTelModifyRequest request = MemberTelModifyRequest.builder()
            .tel("01056785678")
            .build();

        mockMvc.perform(
                patch("/members/tel")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isOk());
    }

    @DisplayName("은행 계좌 1원 인증 요청시 은행 코드는 필수값이다.")
    @Test
    void sendOneCoinAuthNumberWithoutBankCode() throws Exception {
        BankAccountRequest request = BankAccountRequest.builder()
            .accountNumber("123123123456")
            .build();

        mockMvc.perform(
                post("/members/bank-account")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("은행 코드를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("은행 계좌 1원 인증 요청시 은행 계좌는 필수값이다.")
    @Test
    void sendOneCoinAuthNumberWithoutAccountNumber() throws Exception {
        BankAccountRequest request = BankAccountRequest.builder()
            .bankCode("088")
            .build();

        mockMvc.perform(
                post("/members/bank-account")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("은행 계좌를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("은행 계좌 1원 인증을 요청한다.")
    @Test
    void sendOneCoinAuthNumber() throws Exception {
        BankAccountRequest request = BankAccountRequest.builder()
            .bankCode("088")
            .accountNumber("123123123456")
            .build();

        mockMvc.perform(
                post("/members/bank-account")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isOk());
    }

    @DisplayName("은행 계좌 수정시 은행 코드는 필수값이다.")
    @Test
    void modifyBankAccountWithoutBankCode() throws Exception {
        MemberBankAccountModifyRequest request = MemberBankAccountModifyRequest.builder()
            .accountNumber("123123123456")
            .authNumber("012")
            .build();

        mockMvc.perform(
                patch("/members/bank-account")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("은행 코드를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("은행 계좌 수정시 은행 계좌는 필수값이다.")
    @Test
    void modifyBankAccountWithoutAccountNumber() throws Exception {
        MemberBankAccountModifyRequest request = MemberBankAccountModifyRequest.builder()
            .bankCode("088")
            .authNumber("012")
            .build();

        mockMvc.perform(
                patch("/members/bank-account")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("은행 계좌를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("은행 계좌 수정시 인증 번호는 필수값이다.")
    @Test
    void modifyBankAccountWithoutAuthNumber() throws Exception {
        MemberBankAccountModifyRequest request = MemberBankAccountModifyRequest.builder()
            .bankCode("088")
            .accountNumber("123123123456")
            .build();

        mockMvc.perform(
                patch("/members/bank-account")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("인증 번호를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("은행 계좌를 수정한다.")
    @Test
    void modifyBankAccount() throws Exception {
        MemberBankAccountModifyRequest request = MemberBankAccountModifyRequest.builder()
            .bankCode("088")
            .accountNumber("123123123456")
            .authNumber("012")
            .build();

        mockMvc.perform(
                patch("/members/bank-account")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isOk());
    }

    @DisplayName("회원 탈퇴시 비밀번호는 필수값이다.")
    @Test
    void removeMemberWithoutPassword() throws Exception {
        MemberRemoveRequest request = MemberRemoveRequest.builder()
            .build();

        mockMvc.perform(
                post("/members/withdraw")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
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
                post("/members/withdraw")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isOk());
    }
}