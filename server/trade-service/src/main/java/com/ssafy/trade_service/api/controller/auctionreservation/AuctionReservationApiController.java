package com.ssafy.trade_service.api.controller.auctionreservation;

import com.ssafy.trade_service.api.ApiResponse;
import com.ssafy.trade_service.api.controller.auctionreservation.request.AuctionReservationCreateRequest;
import com.ssafy.trade_service.api.service.auctionreservation.response.AuctionReservationCreateResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trade-service/auction-schedules/{auctionScheduleId}/auction-reservations")
public class AuctionReservationApiController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<AuctionReservationCreateResponse> createAuctionReservation(
        @PathVariable Integer auctionScheduleId,
        @Valid @RequestBody AuctionReservationCreateRequest request
    ) {
        return null;
    }
}
