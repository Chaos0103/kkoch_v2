package com.kkoch.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kkoch.admin.api.controller.admin.AdminApiController;
import com.kkoch.admin.api.controller.notice.NoticeApiController;
import com.kkoch.admin.api.service.admin.AdminService;
import com.kkoch.admin.api.service.notice.NoticeQueryService;
import com.kkoch.admin.api.service.notice.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
    AdminApiController.class,
    NoticeApiController.class
})
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected AdminService adminService;

    @MockBean
    protected NoticeService noticeService;

    @MockBean
    protected NoticeQueryService noticeQueryService;
}
