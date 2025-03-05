package com.ssafy.boardservice.api.controller.notice;

import com.ssafy.boardservice.api.controller.notice.request.param.NoticeSearchParam;
import com.ssafy.boardservice.api.service.notice.NoticeQueryService;
import com.ssafy.boardservice.domain.notice.repository.response.NoticeDisplayResponse;
import com.ssafy.boardservice.domain.notice.repository.response.NoticeListDisplayResponse;
import com.ssafy.common.api.ApiResponse;
import com.ssafy.common.api.ListResponse;
import com.ssafy.common.api.PageResponse;
import com.ssafy.common.global.utils.PageUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static com.ssafy.common.global.utils.PageUtils.parsePageNumber;
import static com.ssafy.common.global.utils.TimeUtils.getCurrentDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/board-service/v1/notices")
public class NoticeQueryApiController {

    private final NoticeQueryService noticeQueryService;

    @GetMapping
    public ApiResponse<PageResponse<NoticeListDisplayResponse>> searchNotFixedNotices(@Valid @ModelAttribute NoticeSearchParam param) {
        LocalDateTime currentDateTime = getCurrentDateTime();

        int pageNumber = parsePageNumber(param.getPage());
        Pageable pageable = PageUtils.of(pageNumber);

        PageResponse<NoticeListDisplayResponse> response = noticeQueryService.searchNotFixedNotices(param.getKeyword(), currentDateTime, pageable);

        return ApiResponse.ok(response);
    }

    @GetMapping("/fixed")
    public ApiResponse<ListResponse<NoticeListDisplayResponse>> searchFixedNotices() {
        LocalDateTime currentDateTime = getCurrentDateTime();

        ListResponse<NoticeListDisplayResponse> response = noticeQueryService.searchFixedNotices(currentDateTime);

        return ApiResponse.ok(response);
    }

    @GetMapping("/{noticeId}")
    public ApiResponse<NoticeDisplayResponse> searchNotice(@PathVariable Integer noticeId) {
        NoticeDisplayResponse response = noticeQueryService.searchNotice(noticeId);

        return ApiResponse.ok(response);
    }
}
