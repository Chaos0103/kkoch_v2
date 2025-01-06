package com.ssafy.live_service.api.client;

import com.ssafy.live_service.api.client.response.ReservationVarietyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "trade-service")
public interface TradeServiceClient {

    @GetMapping("/trade-service/auction-schedules/{auctionScheduleId}/auction-reservations/{varietyCode}")
    ReservationVarietyResponse searchReservationVariety(@PathVariable Integer auctionScheduleId, @PathVariable String varietyCode);
}
