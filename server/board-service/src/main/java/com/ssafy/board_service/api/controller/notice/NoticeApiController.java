package com.ssafy.board_service.api.controller.notice;

import com.ssafy.board_service.api.ApiResponse;
import com.ssafy.board_service.api.controller.notice.request.NoticeCreateRequest;
import com.ssafy.board_service.api.service.notice.NoticeService;
import com.ssafy.board_service.api.service.notice.response.NoticeCreateResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board-service/notices")
public class NoticeApiController {

    private final NoticeService noticeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<NoticeCreateResponse> createNotice(@Valid @RequestBody NoticeCreateRequest request) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        NoticeCreateResponse response = noticeService.createNotice(currentDateTime, request.toServiceRequest());

        return ApiResponse.created(response);
    }

}
