package com.ssafy.userservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.userservice.api.controller.member.MemberQueryApiController;
import com.ssafy.userservice.api.service.member.MemberQueryService;
import common.ControllerTestSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {MemberQueryApiController.class})
public abstract class UserServiceApiTestSupport extends ControllerTestSupport {

    @Autowired
    protected UserServiceApiTestSupport(MockMvc mockMvc, ObjectMapper objectMapper) {
        super(mockMvc, objectMapper);
    }

    @MockitoBean
    protected MemberQueryService memberQueryService;
}
