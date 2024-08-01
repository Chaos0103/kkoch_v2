package com.kkoch.admin.api.controller.trade;

import com.kkoch.admin.api.ApiResponse;
import com.kkoch.admin.api.PageResponse;
import com.kkoch.admin.api.controller.trade.param.TradeSearchParam;
import com.kkoch.admin.api.service.trade.TradeQueryService;
import com.kkoch.admin.api.service.trade.response.TradeDetailResponse;
import com.kkoch.admin.domain.trade.repository.response.TradeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin-service/trades")
public class TradeApiController {

    private final TradeQueryService tradeQueryService;

    @GetMapping("/{memberKey}")
    public ApiResponse<PageResponse<TradeResponse>> searchTrades(
        @PathVariable String memberKey,
        @Valid @ModelAttribute TradeSearchParam param
    ) {
        PageResponse<TradeResponse> response = tradeQueryService.searchTrades(memberKey, param.getPageable());

        return ApiResponse.ok(response);
    }

    @GetMapping("/{tradeId}/results")
    public ApiResponse<TradeDetailResponse> searchTrade(@PathVariable long tradeId) {
        TradeDetailResponse response = tradeQueryService.searchTrade(tradeId);

        return ApiResponse.ok(response);
    }
}
