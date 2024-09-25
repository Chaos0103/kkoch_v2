package com.ssafy.trade_service.api.controller.auctionstatistics;

import com.ssafy.trade_service.api.ApiResponse;
import com.ssafy.trade_service.api.ListResponse;
import com.ssafy.trade_service.api.controller.auctionstatistics.param.AuctionStatisticsParam;
import com.ssafy.trade_service.api.service.auctionstatistics.AuctionStatisticsQueryService;
import com.ssafy.trade_service.domain.auctionstatistics.repository.response.AuctionStatisticsResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trade-service/auction-statistics/{varietyCode}")
public class AuctionStatisticsApiQueryController {

    private final AuctionStatisticsQueryService auctionStatisticsQueryService;

    @GetMapping
    public ApiResponse<ListResponse<AuctionStatisticsResponse>> searchAuctionStatistics(@PathVariable String varietyCode, @Valid @ModelAttribute AuctionStatisticsParam param) {
        ListResponse<AuctionStatisticsResponse> response = auctionStatisticsQueryService.searchAuctionStatistics(varietyCode, param.toCond());

        return ApiResponse.ok(response);
    }
}
