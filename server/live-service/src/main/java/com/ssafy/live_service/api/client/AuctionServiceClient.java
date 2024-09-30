package com.ssafy.live_service.api.client;

import com.ssafy.live_service.api.ApiResponse;
import com.ssafy.live_service.api.client.response.AuctionStatusModifyResponse;
import com.ssafy.live_service.api.client.response.AuctionVarietiesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "auction-service")
public interface AuctionServiceClient {

    @PostMapping("/{auctionScheduleId}/progress")
    ApiResponse<AuctionStatusModifyResponse> modifyAuctionStatusToProgress(@PathVariable Integer auctionScheduleId);

    @PostMapping("/{auctionScheduleId}/complete")
    ApiResponse<AuctionStatusModifyResponse> modifyAuctionStatusToComplete(@PathVariable Integer auctionScheduleId);

    @GetMapping("/auction-service/auction-schedules/{auctionScheduleId}/auction-varieties/progress")
    ApiResponse<List<AuctionVarietiesResponse>> searchAuctionVarieties(@PathVariable Integer auctionScheduleId);
}
