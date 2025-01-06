package com.ssafy.trade_service.api.client;

import com.ssafy.trade_service.api.ApiResponse;
import com.ssafy.trade_service.api.client.response.AuctionScheduleStatusResponse;
import com.ssafy.trade_service.api.client.response.AuctionVarietyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "auction-service")
public interface AuctionServiceClient {

    @GetMapping("/auction-service/auction-schedules/{auctionScheduleId}/client/auction-reservation")
    ApiResponse<AuctionScheduleStatusResponse> isAfterProgress(@PathVariable Integer auctionScheduleId);

    @GetMapping("/auction-service/auction-varieties/client")
    ApiResponse<List<AuctionVarietyResponse>> findAllByIdIn(@RequestParam List<Long> ids);
}
