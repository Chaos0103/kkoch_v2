package com.kkoch.admin.api.controller.stats;

import com.kkoch.admin.api.ApiResponse;
import com.kkoch.admin.api.ListResponse;
import com.kkoch.admin.api.controller.stats.param.StatsSearchParam;
import com.kkoch.admin.api.service.stats.StatsQueryService;
import com.kkoch.admin.domain.stats.repository.response.StatsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin-service/stats")
public class StatsApiController {

    private final StatsQueryService statsQueryService;

    @GetMapping
    public ApiResponse<ListResponse<StatsResponse>> searchStats(@Valid @ModelAttribute StatsSearchParam param) {
        LocalDate currentDate = LocalDate.now();

        ListResponse<StatsResponse> response = statsQueryService.searchStats(param.toCond(), currentDate);

        return ApiResponse.ok(response);
    }
}
