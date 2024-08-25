package com.ssafy.board_service.api.controller.notice;

import com.ssafy.board_service.api.ApiResponse;
import com.ssafy.board_service.api.controller.notice.request.NoticeCreateRequest;
import com.ssafy.board_service.api.controller.notice.request.NoticeModifyRequest;
import com.ssafy.board_service.api.service.notice.NoticeService;
import com.ssafy.board_service.api.service.notice.response.NoticeCreateResponse;
import com.ssafy.board_service.api.service.notice.response.NoticeModifyResponse;
import com.ssafy.board_service.api.service.notice.response.NoticeRemoveResponse;
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

    @PatchMapping("/{noticeId}")
    public ApiResponse<NoticeModifyResponse> modifyNotice(@PathVariable Integer noticeId, @Valid @RequestBody NoticeModifyRequest request) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        NoticeModifyResponse response = noticeService.modifyNotice(noticeId, currentDateTime, request.toServiceRequest());

        return ApiResponse.ok(response);
    }

    @DeleteMapping("/{noticeId}")
    public ApiResponse<NoticeRemoveResponse> removeNotice(@PathVariable Integer noticeId) {
        return null;
    }
}
