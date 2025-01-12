package com.ssafy.userservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.userservice.api.controller.member.MemberApiController;
import com.ssafy.userservice.api.controller.member.MemberQueryApiController;
import com.ssafy.userservice.api.service.auth.AuthenticationService;
import com.ssafy.userservice.api.service.member.MemberQueryService;
import com.ssafy.userservice.api.service.member.MemberService;
import com.ssafy.userservice.config.SecurityConfig;
import com.ssafy.userservice.domain.member.repository.MemberRepository;
import com.ssafy.userservice.security.JwtTokenProvider;
import common.ControllerTestSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = {MemberApiController.class, MemberQueryApiController.class})
public abstract class UserServiceApiTestSupport extends ControllerTestSupport {

    @Autowired
    protected UserServiceApiTestSupport(MockMvc mockMvc, ObjectMapper objectMapper) {
        super(mockMvc, objectMapper);
    }

    @MockitoBean
    protected MemberService memberService;

    @MockitoBean
    protected MemberQueryService memberQueryService;

    @MockitoBean
    protected AuthenticationService authenticationService;

    @MockitoBean
    protected MemberRepository memberRepository;

    @MockitoBean
    protected BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockitoBean
    private JwtTokenProvider tokenProvider;
}
