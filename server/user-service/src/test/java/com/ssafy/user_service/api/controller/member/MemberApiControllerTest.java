package com.ssafy.user_service.api.controller.member;

import com.ssafy.user_service.ControllerTestSupport;
import com.ssafy.user_service.api.controller.member.request.AdminMemberCreateRequest;
import com.ssafy.user_service.api.controller.member.request.MemberCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberApiControllerTest extends ControllerTestSupport {

    @DisplayName("일반 회원 가입시 이메일은 필수값이다.")
    @Test
    void createUserMemberWithoutEmail() throws Exception {
        MemberCreateRequest request = MemberCreateRequest.builder()
            .password("ssafy1234!")
            .name("김싸피")
            .tel("01012341234")
            .businessNumber("1231212345")
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
    void createUserMemberWithoutPassword() throws Exception {
        MemberCreateRequest request = MemberCreateRequest.builder()
            .email("ssafy@ssafy.com")
            .name("김싸피")
            .tel("01012341234")
            .businessNumber("1231212345")
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
    void createUserMemberWithoutName() throws Exception {
        MemberCreateRequest request = MemberCreateRequest.builder()
            .email("ssafy@ssafy.com")
            .password("ssafy1234!")
            .tel("01012341234")
            .businessNumber("1231212345")
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
    void createUserMemberWithoutTel() throws Exception {
        MemberCreateRequest request = MemberCreateRequest.builder()
            .email("ssafy@ssafy.com")
            .password("ssafy1234!")
            .name("김싸피")
            .businessNumber("1231212345")
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

    @DisplayName("일반 회원 가입시 사업자 번호는 필수값이다.")
    @Test
    void createUserMemberWithoutBusinessNumber() throws Exception {
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
            .andExpect(jsonPath("$.message").value("사업자 번호를 입력해주세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("일반 회원 가입을 한다.")
    @Test
    void createUserMember() throws Exception {
        MemberCreateRequest request = MemberCreateRequest.builder()
            .email("ssafy@ssafy.com")
            .password("ssafy1234!")
            .name("김싸피")
            .tel("01012341234")
            .businessNumber("1231212345")
            .build();

        mockMvc.perform(
                post("/members")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isCreated());
    }

    @DisplayName("관리자 회원 가입시 이메일은 필수값이다.")
    @Test
    void createAdminMemberWithoutEmail() throws Exception {
        AdminMemberCreateRequest request = AdminMemberCreateRequest.builder()
            .password("ssafy1234!")
            .name("김싸피")
            .tel("01012341234")
            .build();

        mockMvc.perform(
                post("/members/admin")
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

    @DisplayName("관리자 회원 가입시 비밀번호는 필수값이다.")
    @Test
    void createAdminMemberWithoutPassword() throws Exception {
        AdminMemberCreateRequest request = AdminMemberCreateRequest.builder()
            .email("ssafy@ssafy.com")
            .name("김싸피")
            .tel("01012341234")
            .build();

        mockMvc.perform(
                post("/members/admin")
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

    @DisplayName("관지라 회원 가입시 이름은 필수값이다.")
    @Test
    void createAdminMemberWithoutName() throws Exception {
        AdminMemberCreateRequest request = AdminMemberCreateRequest.builder()
            .email("ssafy@ssafy.com")
            .password("ssafy1234!")
            .tel("01012341234")
            .build();

        mockMvc.perform(
                post("/members/admin")
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

    @DisplayName("관리자 회원 가입시 연락처는 필수값이다.")
    @Test
    void createAdminMemberWithoutTel() throws Exception {
        AdminMemberCreateRequest request = AdminMemberCreateRequest.builder()
            .email("ssafy@ssafy.com")
            .password("ssafy1234!")
            .name("김싸피")
            .build();

        mockMvc.perform(
                post("/members/admin")
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

    @DisplayName("관리자 회원 가입을 한다.")
    @Test
    void createAdminMember() throws Exception {
        AdminMemberCreateRequest request = AdminMemberCreateRequest.builder()
            .email("ssafy@ssafy.com")
            .password("ssafy1234!")
            .name("김싸피")
            .tel("01012341234")
            .build();

        mockMvc.perform(
                post("/members/admin")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf())
            )
            .andExpect(status().isCreated());
    }
}