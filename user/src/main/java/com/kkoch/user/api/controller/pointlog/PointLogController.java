package com.kkoch.user.api.controller.pointlog;

import com.kkoch.user.api.ApiResponse;
import com.kkoch.user.api.PageResponse;
import com.kkoch.user.api.controller.pointlog.param.PointLogSearchParam;
import com.kkoch.user.api.controller.pointlog.request.PointLogCreateRequest;
import com.kkoch.user.api.service.pointlog.PointLogQueryService;
import com.kkoch.user.api.service.pointlog.PointLogService;
import com.kkoch.user.api.service.pointlog.response.PointLogCreateResponse;
import com.kkoch.user.domain.pointlog.repository.response.PointLogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{memberKey}/points")
public class PointLogController {

    private final PointLogService pointLogService;
    private final PointLogQueryService pointLogQueryService;

    @PostMapping
    public ApiResponse<PointLogCreateResponse> createPointLog(@PathVariable String memberKey, @Valid @RequestBody PointLogCreateRequest request) {
        PointLogCreateResponse response = pointLogService.createPointLog(memberKey, request.toServiceRequest());

        return ApiResponse.ok(response);
    }

    @GetMapping
    public ApiResponse<PageResponse<PointLogResponse>> searchPointLogs(@PathVariable String memberKey, @Valid @ModelAttribute PointLogSearchParam param) {
        Page<PointLogResponse> content = pointLogQueryService.getPointLogs(memberKey, param.getPage());

        PageResponse<PointLogResponse> response = PageResponse.of(content);

        return ApiResponse.ok(response);
    }
}
