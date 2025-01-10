package com.ssafy.userservice.api.controller.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.userservice.UserServiceApiTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
class MemberQueryApiControllerTest extends UserServiceApiTestSupport {

    @Autowired
    public MemberQueryApiControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        super(mockMvc, objectMapper);
    }

    @DisplayName("토큰으로 회원의 기본 정보를 조회한다.")
    @Test
    void searchMemberInfo() throws Exception {
        mockMvc.perform(
                get("/v1/members/account")
            )
            .andExpect(status().isOk());
    }
}