package com.ssafy.board_service.api.controller.notice;

import com.ssafy.board_service.api.ApiResponse;
import com.ssafy.board_service.api.PageResponse;
import com.ssafy.board_service.api.controller.notice.param.NoticeSearchParam;
import com.ssafy.board_service.api.service.notice.FixedNoticeResponse;
import com.ssafy.board_service.api.service.notice.NoticeQueryService;
import com.ssafy.board_service.domain.notice.repository.response.NoticeResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static com.ssafy.board_service.common.util.PageUtils.parsePageNumber;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board-service/notices")
public class NoticeQueryApiController {

    private final NoticeQueryService noticeQueryService;

    @GetMapping
    public ApiResponse<PageResponse<NoticeResponse>> searchNotFixedNotices(@Valid @ModelAttribute NoticeSearchParam param) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        int pageNumber = parsePageNumber(param.getPage());

        PageResponse<NoticeResponse> response = noticeQueryService.searchNotFixedNotices(pageNumber, param.getKeyword(), currentDateTime);

        return ApiResponse.ok(response);
    }

    @GetMapping("/fixed")
    public ApiResponse<FixedNoticeResponse> searchFixedNotices() {
        return null;
    }
}
