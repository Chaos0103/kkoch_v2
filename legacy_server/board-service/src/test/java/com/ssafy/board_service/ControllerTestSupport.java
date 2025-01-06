package com.ssafy.board_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.board_service.api.controller.notice.NoticeApiController;
import com.ssafy.board_service.api.controller.notice.NoticeQueryApiController;
import com.ssafy.board_service.api.service.notice.NoticeQueryService;
import com.ssafy.board_service.api.service.notice.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
    NoticeApiController.class, NoticeQueryApiController.class
})
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected NoticeService noticeService;

    @MockBean
    protected NoticeQueryService noticeQueryService;
}
