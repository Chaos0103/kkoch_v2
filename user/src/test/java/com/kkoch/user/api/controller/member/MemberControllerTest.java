package com.kkoch.user.api.controller.member;

import com.kkoch.user.ControllerTestSupport;
import com.kkoch.user.api.controller.member.request.MemberCreateRequest;
import com.kkoch.user.api.controller.member.request.MemberPwdModifyRequest;
import com.kkoch.user.api.controller.member.request.MemberRemoveRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberControllerTest extends ControllerTestSupport {

    @DisplayName("신규 회원 등록시 유효성 검사를 한다.")
    @CsvSource({
        ",ssafy1234!,김싸피,010-1234-1234,123-12-12345,이메일을 입력해주세요.",
        "ssafy@ssafy.com,,김싸피,010-1234-1234,123-12-12345,비밀번호를 입력해주세요.",
        "ssafy@ssafy.com,ssafy1234!,,010-1234-1234,123-12-12345,이름을 입력해주세요.",
        "ssafy@ssafy.com,ssafy1234!,김싸피,,123-12-12345,연락처를 입력해주세요.",
        "ssafy@ssafy.com,ssafy1234!,김싸피,010-1234-1234,,사업자 번호를 입력해주세요."
    })
    @ParameterizedTest
    void createMemberWithoutValue(String email, String loginPw, String name, String tel, String businessNumber, String message) throws Exception {
        MemberCreateRequest request = MemberCreateRequest.builder()
            .email(email)
            .loginPw(loginPw)
            .name(name)
            .tel(tel)
            .businessNumber(businessNumber)
            .build();

        mockMvc.perform(
                post("/join")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value(message))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 회원을 등록한다.")
    @Test
    void createMember() throws Exception {
        MemberCreateRequest request = MemberCreateRequest.builder()
            .email("ssafy@ssafy.com")
            .loginPw("ssafy1234!")
            .name("김싸피")
            .tel("010-1234-1234")
            .businessNumber("123-12-12345")
            .build();

        mockMvc.perform(
                post("/join")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isCreated());
    }

    @DisplayName("계정 비밀번호 수정시 유효성 검사를 한다.")
    @CsvSource({",ssafy1234@,현재 비밀번호를 입력해주세요.", "ssafy1234!,,새로운 비밀번호를 입력해주세요."})
    @ParameterizedTest
    void modifyPwdWithoutValue(String currentPwd, String newPwd, String message) throws Exception {
        String memberKey = generateMemberKey();

        MemberPwdModifyRequest request = MemberPwdModifyRequest.builder()
            .currentPwd(currentPwd)
            .newPwd(newPwd)
            .build();

        mockMvc.perform(
                patch("/{memberKey}/pwd", memberKey)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value(message))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("계정 비밀번호를 수정한다.")
    @Test
    void modifyPwd() throws Exception {
        String memberKey = generateMemberKey();

        MemberPwdModifyRequest request = MemberPwdModifyRequest.builder()
            .currentPwd("ssafy1234!")
            .newPwd("ssafy1234@")
            .build();

        mockMvc.perform(
                patch("/{memberKey}/pwd", memberKey)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isOk());
    }

    @DisplayName("회원 탈퇴시 비밀번호는 필수값이다.")
    @Test
    void removeMemberWithoutPwd() throws Exception {
        String memberKey = generateMemberKey();
        MemberRemoveRequest request = MemberRemoveRequest.builder()
            .build();

        mockMvc.perform(
                patch("/{memberKey}/withdrawal", memberKey)
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
        String memberKey = generateMemberKey();
        MemberRemoveRequest request = MemberRemoveRequest.builder()
            .pwd("ssafy1234!")
            .build();

        mockMvc.perform(
                patch("/{memberKey}/withdrawal", memberKey)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isOk());
    }

    private String generateMemberKey() {
        return UUID.randomUUID().toString();
    }
}