package com.ssafy.trade_service.api.client;

import com.ssafy.trade_service.api.ApiResponse;
import com.ssafy.trade_service.api.client.response.AuctionScheduleStatusResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auction-service")
public interface AuctionServiceClient {

    @GetMapping("/auction-service/auction-schedules/{auctionScheduleId}/client/auction-reservation")
    ApiResponse<AuctionScheduleStatusResponse> isAfterProgress(@PathVariable Integer auctionScheduleId);
}
