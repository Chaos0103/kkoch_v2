package com.kkoch.admin.docs.admin;

import com.kkoch.admin.api.controller.admin.AdminApiController;
import com.kkoch.admin.api.controller.admin.request.AdminCreateRequest;
import com.kkoch.admin.api.controller.admin.request.AdminPwdModifyRequest;
import com.kkoch.admin.api.service.admin.AdminService;
import com.kkoch.admin.api.service.admin.response.AdminCreateResponse;
import com.kkoch.admin.api.service.admin.response.AdminRemoveResponse;
import com.kkoch.admin.docs.RestDocsSupport;
import com.kkoch.admin.domain.admin.AdminAuth;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminApiControllerDocsTest extends RestDocsSupport {

    private final AdminService adminService = mock(AdminService.class);

    @Override
    protected Object initController() {
        return new AdminApiController(adminService);
    }

    @DisplayName("관리자 신규 등록 API")
    @Test
    void createAdmin() throws Exception {
        AdminCreateRequest request = AdminCreateRequest.builder()
            .email("admin@ssafy.com")
            .pwd("ssafy1234!")
            .name("김관리")
            .tel("010-1234-1234")
            .auth("MASTER")
            .build();

        AdminCreateResponse response = AdminCreateResponse.builder()
            .email("admin@ssafy.com")
            .name("김관리")
            .auth(AdminAuth.MASTER)
            .createdDateTime(LocalDateTime.now())
            .build();

        given(adminService.createAdmin(any()))
            .willReturn(response);

        mockMvc.perform(
                post("/admin-service/admins")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-admin",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("email").type(JsonFieldType.STRING)
                        .description("이메일"),
                    fieldWithPath("pwd").type(JsonFieldType.STRING)
                        .description("계정 비밀번호"),
                    fieldWithPath("name").type(JsonFieldType.STRING)
                        .description("관리자명"),
                    fieldWithPath("tel").type(JsonFieldType.STRING)
                        .description("연락처"),
                    fieldWithPath("auth").type(JsonFieldType.STRING)
                        .description("관리 권한")
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
                        .description("이메일"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING)
                        .description("관리자명"),
                    fieldWithPath("data.auth").type(JsonFieldType.STRING)
                        .description("관리 권한"),
                    fieldWithPath("data.createdDateTime").type(JsonFieldType.ARRAY)
                        .description("관리자 등록일시")
                )
            ));
    }

    @DisplayName("관리자 비밀번호 수정 API")
    @Test
    void modifyAdminPwd() throws Exception {
        AdminPwdModifyRequest request = AdminPwdModifyRequest.builder()
            .currentPwd("ssafy1234!")
            .newPwd("ssafy1234@")
            .build();

        given(adminService.modifyPwd(anyInt(), any()))
            .willReturn(1);

        mockMvc.perform(
                patch("/admin-service/admins/{adminId}", 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("modify-pwd-member",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("currentPwd").type(JsonFieldType.STRING)
                        .description("현재 비밀번호"),
                    fieldWithPath("newPwd").type(JsonFieldType.STRING)
                        .description("신규 비밀번호")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.NUMBER)
                        .description("응답 데이터")
                )
            ));
    }

    @DisplayName("관리자 삭제 API")
    @Test
    void removeAdmin() throws Exception {
        AdminRemoveResponse response = AdminRemoveResponse.builder()
            .email("admin@ssafy.com")
            .name("김관리")
            .auth(AdminAuth.MASTER)
            .removedDateTime(LocalDateTime.now())
            .build();

        given(adminService.removeAdmin(anyInt()))
            .willReturn(response);

        mockMvc.perform(
                delete("/admin-service/admins/{adminId}", 1)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("remove-admin",
                preprocessResponse(prettyPrint()),
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
                        .description("이메일"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING)
                        .description("관리자명"),
                    fieldWithPath("data.auth").type(JsonFieldType.STRING)
                        .description("관리 권한"),
                    fieldWithPath("data.removedDateTime").type(JsonFieldType.ARRAY)
                        .description("관리자 삭제일시")
                )
            ));
    }
}
