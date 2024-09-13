package com.ssafy.auction_service.api.controller.auctionvariety;

import com.ssafy.auction_service.api.ApiResponse;
import com.ssafy.auction_service.api.PageResponse;
import com.ssafy.auction_service.api.controller.auctionvariety.param.AuctionVarietySearchParam;
import com.ssafy.auction_service.api.service.auctionvariety.AuctionVarietyQueryService;
import com.ssafy.auction_service.domain.auctionvariety.repository.response.AuctionVarietyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auction-service/auction-schedules/{auctionScheduleId}/auction-varieties")
public class AuctionVarietyApiQueryController {

    private final AuctionVarietyQueryService auctionVarietyQueryService;

    @GetMapping
    public ApiResponse<PageResponse<AuctionVarietyResponse>> searchAuctionVarieties(
        @PathVariable Integer auctionScheduleId,
        @ModelAttribute AuctionVarietySearchParam param
    ) {
        return null;
    }
}
