package com.ssafy.board_service.api.controller.notice;

import com.ssafy.board_service.api.ApiResponse;
import com.ssafy.board_service.api.controller.notice.request.NoticeCreateRequest;
import com.ssafy.board_service.api.service.notice.NoticeService;
import com.ssafy.board_service.api.service.notice.response.NoticeCreateResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board-service/notices")
public class NoticeApiController {

    private final NoticeService noticeService;

    @PostMapping
    public ApiResponse<NoticeCreateResponse> createNotice(@Valid @RequestBody NoticeCreateRequest request) {
        return null;
    }

}
