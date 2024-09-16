package com.ssafy.trade_service.api.controller.auctionreservation;

import com.ssafy.trade_service.api.ApiResponse;
import com.ssafy.trade_service.api.service.auctionreservation.AuctionReservationQueryService;
import com.ssafy.trade_service.domain.auctionreservation.repository.response.AuctionReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trade-service/auction-schedules/{auctionScheduleId}/auction-reservations")
public class AuctionReservationApiQueryController {

    private final AuctionReservationQueryService auctionReservationQueryService;

    @GetMapping
    public ApiResponse<AuctionReservationResponse> searchAuctionReservations(@PathVariable Integer auctionScheduleId) {
        return null;
    }
}
