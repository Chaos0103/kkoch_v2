package com.ssafy.user_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.user_service.api.controller.member.AuthApiController;
import com.ssafy.user_service.api.controller.member.MemberApiController;
import com.ssafy.user_service.api.service.member.AuthService;
import com.ssafy.user_service.api.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser
@WebMvcTest(controllers = {
    AuthApiController.class,
    MemberApiController.class
})
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected AuthService authService;

    @MockBean
    protected MemberService memberService;
}
