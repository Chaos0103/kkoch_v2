package com.ssafy.trade_service.api.controller.auctionreservation;

import com.ssafy.trade_service.api.ApiResponse;
import com.ssafy.trade_service.api.controller.auctionreservation.request.AuctionReservationCreateRequest;
import com.ssafy.trade_service.api.controller.auctionreservation.request.AuctionReservationModifyRequest;
import com.ssafy.trade_service.api.service.auctionreservation.AuctionReservationService;
import com.ssafy.trade_service.api.service.auctionreservation.response.AuctionReservationCreateResponse;
import com.ssafy.trade_service.api.service.auctionreservation.response.AuctionReservationModifyResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trade-service/auction-schedules/{auctionScheduleId}/auction-reservations")
public class AuctionReservationApiController {

    private final AuctionReservationService auctionReservationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<AuctionReservationCreateResponse> createAuctionReservation(
        @PathVariable Integer auctionScheduleId,
        @Valid @RequestBody AuctionReservationCreateRequest request
    ) {
        AuctionReservationCreateResponse response = auctionReservationService.createAuctionReservation(auctionScheduleId, request.toServiceRequest());

        return ApiResponse.created(response);
    }

    @PatchMapping("/{auctionReservationId}")
    public ApiResponse<AuctionReservationModifyResponse> modifyAuctionReservation(
        @PathVariable Integer auctionScheduleId,
        @PathVariable Long auctionReservationId,
        @Valid @RequestBody AuctionReservationModifyRequest request
    ) {
        AuctionReservationModifyResponse response = auctionReservationService.modifyAuctionReservation(auctionReservationId, request.toServiceRequest());

        return ApiResponse.ok(response);
    }
}
