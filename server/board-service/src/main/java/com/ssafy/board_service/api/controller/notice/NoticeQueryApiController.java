package com.ssafy.board_service.api.controller.notice;

import com.ssafy.board_service.api.ApiResponse;
import com.ssafy.board_service.api.PageResponse;
import com.ssafy.board_service.api.controller.notice.param.NoticeSearchParam;
import com.ssafy.board_service.api.service.notice.NoticeQueryService;
import com.ssafy.board_service.api.service.notice.response.FixedNoticeResponse;
import com.ssafy.board_service.domain.notice.repository.response.NoticeDetailResponse;
import com.ssafy.board_service.domain.notice.repository.response.NoticeResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static com.ssafy.board_service.common.util.PageUtils.parsePageNumber;
import static com.ssafy.board_service.common.util.TimeUtils.getCurrentDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board-service/notices")
public class NoticeQueryApiController {

    private final NoticeQueryService noticeQueryService;

    @GetMapping
    public ApiResponse<PageResponse<NoticeResponse>> searchNotFixedNotices(@Valid @ModelAttribute NoticeSearchParam param) {
        LocalDateTime currentDateTime = getCurrentDateTime();

        int pageNumber = parsePageNumber(param.getPage());

        PageResponse<NoticeResponse> response = noticeQueryService.searchNotFixedNotices(pageNumber, param.getKeyword(), currentDateTime);

        return ApiResponse.ok(response);
    }

    @GetMapping("/fixed")
    public ApiResponse<FixedNoticeResponse> searchFixedNotices() {
        LocalDateTime currentDateTime = getCurrentDateTime();

        FixedNoticeResponse response = noticeQueryService.searchFixedNotices(currentDateTime);

        return ApiResponse.ok(response);
    }

    @GetMapping("/{noticeId}")
    public ApiResponse<NoticeDetailResponse> searchNotice(@PathVariable Integer noticeId) {
        NoticeDetailResponse response = noticeQueryService.searchNotice(noticeId);

        return ApiResponse.ok(response);
    }
}
