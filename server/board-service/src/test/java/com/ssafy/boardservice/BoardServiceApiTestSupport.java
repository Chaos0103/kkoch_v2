package com.ssafy.boardservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.boardservice.api.controller.notice.NoticeApiController;
import com.ssafy.boardservice.api.controller.notice.NoticeQueryApiController;
import com.ssafy.boardservice.api.service.notice.NoticeQueryService;
import com.ssafy.boardservice.api.service.notice.NoticeService;
import common.ControllerTestSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {NoticeApiController.class, NoticeQueryApiController.class})
public class BoardServiceApiTestSupport extends ControllerTestSupport {

    @Autowired
    protected BoardServiceApiTestSupport(MockMvc mockMvc, ObjectMapper objectMapper) {
        super(mockMvc, objectMapper);
    }

    @MockitoBean
    protected NoticeService noticeService;

    @MockitoBean
    protected NoticeQueryService noticeQueryService;
}
