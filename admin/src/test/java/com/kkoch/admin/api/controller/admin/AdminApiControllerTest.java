package com.kkoch.admin.api.controller.admin;

import com.kkoch.admin.ControllerTestSupport;
import com.kkoch.admin.api.controller.admin.request.AdminCreateRequest;
import com.kkoch.admin.api.controller.admin.request.AdminPwdModifyRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminApiControllerTest extends ControllerTestSupport {

    @DisplayName("관리자 신규 등록시 모든 데이터는 필수값이다.")
    @CsvSource({
        ",ssafy1234!,김관리,010-1234-1234,MASTER,이메일을 입력해주세요.",
        "admin@ssafy.com,,김관리,010-1234-1234,MASTER,비밀번호를 입력해주세요.",
        "admin@ssafy.com,ssafy1234!,,010-1234-1234,MASTER,이름을 입력해주세요.",
        "admin@ssafy.com,ssafy1234!,김관리,,MASTER,연락처를 입력해주세요.",
        "admin@ssafy.com,ssafy1234!,김관리,010-1234-1234,,관리 권한을 입력해주세요."
    })
    @ParameterizedTest
    void createAdminWithoutValue(String email, String pwd, String name, String tel, String auth, String message) throws Exception {
        AdminCreateRequest request = AdminCreateRequest.builder()
            .email(email)
            .pwd(pwd)
            .name(name)
            .tel(tel)
            .auth(auth)
            .build();

        mockMvc.perform(
                post("/admin-service/admins")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value(message))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("관리자를 신규 등록한다.")
    @Test
    void createAdmin() throws Exception {
        AdminCreateRequest request = AdminCreateRequest.builder()
            .email("admin@ssafy.com")
            .pwd("ssafy1234!")
            .name("김관리")
            .tel("010-1234-1234")
            .auth("MASTER")
            .build();

        mockMvc.perform(
                post("/admin-service/admins")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated());
    }

    @DisplayName("관리자 비밀번호 수정시 모든 데이터는 필수값이다.")
    @CsvSource({
        ",ssafy1234@,현재 비밀번호를 입력해주세요.",
        "ssafy1234!,,신규 비밀번호를 입력해주세요."
    })
    @ParameterizedTest
    void modifyAdminPwdWithoutValue(String currentPwd, String newPwd, String message) throws Exception {
        AdminPwdModifyRequest request = AdminPwdModifyRequest.builder()
            .currentPwd(currentPwd)
            .newPwd(newPwd)
            .build();

        mockMvc.perform(
                patch("/admin-service/admins/{adminId}", 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value(message))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("관리자 비밀번호를 수정한다.")
    @Test
    void modifyAdminPwd() throws Exception {
        AdminPwdModifyRequest request = AdminPwdModifyRequest.builder()
            .currentPwd("ssafy1234!")
            .newPwd("ssafy1234@")
            .build();

        mockMvc.perform(
                patch("/admin-service/admins/{adminId}", 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @DisplayName("관리자를 삭제한다.")
    @Test
    void removeAdmin() throws Exception {
        mockMvc.perform(
                delete("/admin-service/admins/{adminId}", 1)
            )
            .andExpect(status().isOk());
    }
}