package com.kkoch.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kkoch.user.api.controller.member.MemberController;
import com.kkoch.user.api.controller.pointlog.PointLogController;
import com.kkoch.user.api.service.member.MemberQueryService;
import com.kkoch.user.api.service.member.MemberService;
import com.kkoch.user.api.service.pointlog.PointLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

@WithMockUser
@WebMvcTest(controllers = {MemberController.class, PointLogController.class})
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected MemberService memberService;

    @MockBean
    protected MemberQueryService memberQueryService;

    @MockBean
    protected PointLogService pointLogService;

    protected String generateMemberKey() {
        return UUID.randomUUID().toString();
    }
}
