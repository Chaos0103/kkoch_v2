package com.ssafy.user_service.api.controller.member;

import com.ssafy.user_service.ControllerTestSupport;
import com.ssafy.user_service.api.controller.member.request.SendAuthNumberRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthApiControllerTest extends ControllerTestSupport {

    @DisplayName("이메일 인증 번호 발송시 이메일은 필수값이다.")
    @Test
    void sendAuthNumberWithoutEmail() throws Exception {
        SendAuthNumberRequest request = SendAuthNumberRequest.builder()
            .build();

        mockMvc.perform(
                post("/auth/email")
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

    @DisplayName("이메일 인증 번호 발송한다.")
    @Test
    void sendAuthNumber() throws Exception {
        SendAuthNumberRequest request = SendAuthNumberRequest.builder()
            .email("ssafy@ssafy.com")
            .build();

        mockMvc.perform(
                post("/auth/email")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isOk());
    }
}