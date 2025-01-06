package com.ssafy.user_service.api.controller.member;

import com.ssafy.user_service.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberQueryApiControllerTest extends ControllerTestSupport {

    @DisplayName("회원 정보를 조회한다.")
    @Test
    void searchMemberInfo() throws Exception {
        mockMvc.perform(
                get("/members/info")
                    .with(csrf())
            )
            .andExpect(status().isOk());
    }
}