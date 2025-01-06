package com.ssafy.user_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.user_service.api.controller.member.AuthApiController;
import com.ssafy.user_service.api.controller.member.MemberApiController;
import com.ssafy.user_service.api.controller.member.MemberQueryApiController;
import com.ssafy.user_service.api.controller.notification.NotificationApiController;
import com.ssafy.user_service.api.controller.notification.NotificationQueryApiController;
import com.ssafy.user_service.api.service.member.AuthService;
import com.ssafy.user_service.api.service.member.MemberQueryService;
import com.ssafy.user_service.api.service.member.MemberService;
import com.ssafy.user_service.api.service.notification.NotificationQueryService;
import com.ssafy.user_service.api.service.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser
@WebMvcTest(controllers = {
    AuthApiController.class,
    MemberApiController.class, MemberQueryApiController.class,
    NotificationApiController.class, NotificationQueryApiController.class
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

    @MockBean
    protected MemberQueryService memberQueryService;

    @MockBean
    protected NotificationService notificationService;

    @MockBean
    protected NotificationQueryService notificationQueryService;
}
