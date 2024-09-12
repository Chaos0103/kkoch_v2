package com.ssafy.auction_service.api.controller.auctionschedule;

import com.ssafy.auction_service.api.ApiResponse;
import com.ssafy.auction_service.api.ListResponse;
import com.ssafy.auction_service.api.controller.auctionschedule.param.AuctionScheduleSearchParam;
import com.ssafy.auction_service.api.service.auctionschedule.AuctionScheduleQueryService;
import com.ssafy.auction_service.domain.auctionschedule.repository.response.AuctionScheduleDetailResponse;
import com.ssafy.auction_service.domain.auctionschedule.repository.response.AuctionScheduleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auction-service/auction-schedules")
public class AuctionScheduleApiQueryController {

    private final AuctionScheduleQueryService auctionScheduleQueryService;

    @GetMapping
    public ApiResponse<ListResponse<AuctionScheduleResponse>> searchAuctionSchedules(@ModelAttribute AuctionScheduleSearchParam param) {
        ListResponse<AuctionScheduleResponse> response = auctionScheduleQueryService.searchAuctionSchedulesByCond(param.toCond());

        return ApiResponse.ok(response);
    }

    @GetMapping("/{auctionScheduleId}")
    public ApiResponse<AuctionScheduleDetailResponse> searchAuctionSchedule(@PathVariable Integer auctionScheduleId) {
        return null;
    }
}
