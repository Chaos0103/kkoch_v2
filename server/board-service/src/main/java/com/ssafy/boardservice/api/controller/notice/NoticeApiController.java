package com.ssafy.boardservice.api.controller.notice;

import com.ssafy.boardservice.api.controller.notice.request.NoticeCreateRequest;
import com.ssafy.boardservice.api.controller.notice.request.NoticeModifyRequest;
import com.ssafy.boardservice.api.service.notice.NoticeService;
import com.ssafy.boardservice.api.service.notice.response.NoticeCreateResponse;
import com.ssafy.boardservice.api.service.notice.response.NoticeModifyResponse;
import com.ssafy.boardservice.api.service.notice.response.NoticeRemoveResponse;
import com.ssafy.common.api.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static com.ssafy.common.global.utils.TimeUtils.getCurrentDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/board-service/v1/notices")
public class NoticeApiController {

    private final NoticeService noticeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<NoticeCreateResponse> createNotice(@Valid @RequestBody NoticeCreateRequest request) {
        log.debug("신규 공지사항 등록 요청 [{}]", request);
        LocalDateTime currentDateTime = getCurrentDateTime();

        NoticeCreateResponse response = noticeService.createNotice(request.toServiceRequest(), currentDateTime);

        return ApiResponse.created(response);
    }

    @PatchMapping("/{noticeId}")
    public ApiResponse<NoticeModifyResponse> modifyNotice(@PathVariable Integer noticeId, @Valid @RequestBody NoticeModifyRequest request) {
        log.debug("공지사항 수정 요청 [noticeId = {}, {}]", noticeId, request);
        LocalDateTime currentDateTime = getCurrentDateTime();

        NoticeModifyResponse response = noticeService.modifyNotice(noticeId, request.toServiceRequest(), currentDateTime);

        return ApiResponse.ok(response);
    }

    @DeleteMapping("/{noticeId}")
    public ApiResponse<NoticeRemoveResponse> removeNotice(@PathVariable Integer noticeId) {
        log.debug("공지사항 삭제 요청 [noticeId = {}]", noticeId);
        NoticeRemoveResponse response = noticeService.removeNotice(noticeId);

        return ApiResponse.ok(response);
    }
}
