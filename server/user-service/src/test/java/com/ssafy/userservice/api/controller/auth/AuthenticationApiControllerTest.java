package com.ssafy.userservice.api.controller.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.userservice.UserServiceApiTestSupport;
import com.ssafy.userservice.api.controller.NullAndEmptyAndBlankSource;
import com.ssafy.userservice.api.controller.auth.request.BusinessNumberAuthenticationRequest;
import com.ssafy.userservice.api.controller.auth.request.EmailAuthenticationRequest;
import com.ssafy.userservice.api.controller.auth.request.SendEmailAuthenticationNumberRequest;
import com.ssafy.userservice.api.controller.auth.request.TelAuthenticationRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthenticationApiControllerTest extends UserServiceApiTestSupport {

    @Autowired
    public AuthenticationApiControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        super(mockMvc, objectMapper);
    }

    @DisplayName("이메일 인증 번호 전송 시 이메일은 필수값이다.")
    @NullAndEmptyAndBlankSource
    @ParameterizedTest
    void sendAuthenticationNumberToEmailWithoutEmail(String email) throws Exception {
        SendEmailAuthenticationNumberRequest request = SendEmailAuthenticationNumberRequest.builder()
            .email(email)
            .build();

        mockMvc.perform(
                post("/v1/auth/email")
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

    @DisplayName("이메일 인증 번호를 전송한다.")
    @Test
    void sendAuthenticationNumberToEmail() throws Exception {
        SendEmailAuthenticationNumberRequest request = SendEmailAuthenticationNumberRequest.builder()
            .email("ssafy@gmail.com")
            .build();

        mockMvc.perform(
                post("/v1/auth/email")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .with(csrf())
            )
            .andExpect(status().isOk());
    }

    @DisplayName("이메일 인증 번호 확인 시 이메일은 필수값이다.")
    @NullAndEmptyAndBlankSource
    @ParameterizedTest
    void checkAuthenticationNumberToEmailWithoutEmail(String email) throws Exception {
        EmailAuthenticationRequest request = EmailAuthenticationRequest.builder()
            .email(email)
            .authenticationNumber("012345")
            .build();

        mockMvc.perform(
                patch("/v1/auth/email")
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

    @DisplayName("이메일 인증 번호 확인 시 인증 번호는 필수값이다.")
    @NullAndEmptyAndBlankSource
    @ParameterizedTest
    void checkAuthenticationNumberToEmailWithoutAuthenticationNumber(String authenticationNumber) throws Exception {
        EmailAuthenticationRequest request = EmailAuthenticationRequest.builder()
            .email("ssafy@naver.com")
            .authenticationNumber(authenticationNumber)
            .build();

        mockMvc.perform(
                patch("/v1/auth/email")
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

    @DisplayName("이메일 인증 번호가 일치하면 사용 가능한 이메일인지 확인한다.")
    @Test
    void checkAuthenticationNumberToEmail() throws Exception {
        EmailAuthenticationRequest request = EmailAuthenticationRequest.builder()
            .email("ssafy@naver.com")
            .authenticationNumber("012345")
            .build();

        mockMvc.perform(
                patch("/v1/auth/email")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .with(csrf())
            )
            .andExpect(status().isOk());
    }

    @DisplayName("연락처 사용 가능 여부 확인 시 연락처는 필수값이다.")
    @NullAndEmptyAndBlankSource
    @ParameterizedTest
    void checkTelWithoutTel(String tel) throws Exception {
        TelAuthenticationRequest request = TelAuthenticationRequest.builder()
            .tel(tel)
            .build();

        mockMvc.perform(
                patch("/v1/auth/tel")
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

    @DisplayName("연락처가 사용 가능한 연락처인지 확인한다.")
    @Test
    void checkTel() throws Exception {
        TelAuthenticationRequest request = TelAuthenticationRequest.builder()
            .tel("01012341234")
            .build();

        mockMvc.perform(
                patch("/v1/auth/tel")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .with(csrf())
            )
            .andExpect(status().isOk());
    }

    @DisplayName("사업자 번호 사용 가능 여부 확인 시 사업자 번호는 필수값이다.")
    @NullAndEmptyAndBlankSource
    @ParameterizedTest
    void checkBusinessNumberWithoutBusinessNumber(String businessNumber) throws Exception {
        BusinessNumberAuthenticationRequest request = BusinessNumberAuthenticationRequest.builder()
            .businessNumber(businessNumber)
            .build();

        mockMvc.perform(
                patch("/v1/auth/business-number")
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

    @DisplayName("사업자 번호가 사용 가능한 사업자 번호인지 확인한다.")
    @Test
    void checkBusinessNumber() throws Exception {
        BusinessNumberAuthenticationRequest request = BusinessNumberAuthenticationRequest.builder()
            .businessNumber("1231212345")
            .build();

        mockMvc.perform(
                patch("/v1/auth/business-number")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .with(csrf())
            )
            .andExpect(status().isOk());
    }
}