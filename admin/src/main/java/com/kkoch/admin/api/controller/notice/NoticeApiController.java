package com.kkoch.admin.api.controller.notice;

import com.kkoch.admin.api.ApiResponse;
import com.kkoch.admin.api.PageResponse;
import com.kkoch.admin.api.controller.notice.param.NoticeSearchParam;
import com.kkoch.admin.api.controller.notice.request.NoticeCreateRequest;
import com.kkoch.admin.api.controller.notice.request.NoticeModifyRequest;
import com.kkoch.admin.domain.notice.repository.response.NoticeResponse;
import com.kkoch.admin.api.service.notice.NoticeQueryService;
import com.kkoch.admin.api.service.notice.NoticeService;
import com.kkoch.admin.api.service.notice.response.NoticeCreateResponse;
import com.kkoch.admin.api.service.notice.response.NoticeModifyResponse;
import com.kkoch.admin.api.service.notice.response.NoticeRemoveResponse;
import com.kkoch.admin.domain.notice.repository.dto.NoticeSearchCond;
import com.kkoch.admin.domain.notice.repository.response.NoticeDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin-service/notices")
public class NoticeApiController {

    private final NoticeService noticeService;
    private final NoticeQueryService noticeQueryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<NoticeCreateResponse> createNotice(@Valid @RequestBody NoticeCreateRequest request) {
        int adminId = 1;

        NoticeCreateResponse response = noticeService.createNotice(adminId, request.toServiceRequest());

        return ApiResponse.created(response);
    }

    @GetMapping
    public ApiResponse<PageResponse<NoticeResponse>> searchNotices(@Valid @ModelAttribute NoticeSearchParam param) {
        NoticeSearchCond cond = param.getSearchCond();
        Pageable pageable = param.getPageable();

        PageResponse<NoticeResponse> response = noticeQueryService.searchNotices(cond, pageable);

        return ApiResponse.ok(response);
    }

    @GetMapping("/{noticeId}")
    public ApiResponse<NoticeDetailResponse> searchNotice(@PathVariable int noticeId) {
        NoticeDetailResponse response = noticeQueryService.searchNotice(noticeId);

        return ApiResponse.ok(response);
    }

    @PatchMapping("/{noticeId}")
    public ApiResponse<NoticeModifyResponse> modifyNotice(@PathVariable int noticeId, @Valid @RequestBody NoticeModifyRequest request) {
        int adminId = 1;

        NoticeModifyResponse response = noticeService.modifyNotice(adminId, noticeId, request.toServiceRequest());

        return ApiResponse.ok(response);
    }

    @DeleteMapping("/{noticeId}")
    public ApiResponse<NoticeRemoveResponse> removeNotice(@PathVariable int noticeId) {
        int adminId = 1;

        NoticeRemoveResponse response = noticeService.removeNotice(adminId, noticeId);

        return ApiResponse.ok(response);
    }
}
