package com.kkoch.user.api.controller.pointlog;

import com.kkoch.user.api.ApiResponse;
import com.kkoch.user.api.controller.pointlog.request.PointLogCreateRequest;
import com.kkoch.user.api.service.pointlog.PointLogService;
import com.kkoch.user.api.service.pointlog.response.PointLogCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{memberKey}/points")
public class PointLogController {

    private final PointLogService pointLogService;

    @PostMapping
    public ApiResponse<PointLogCreateResponse> createPointLog(@PathVariable String memberKey, @Valid @RequestBody PointLogCreateRequest request) {
        PointLogCreateResponse response = pointLogService.createPointLog(memberKey, request.toServiceRequest());

        return ApiResponse.ok(response);
    }
}
