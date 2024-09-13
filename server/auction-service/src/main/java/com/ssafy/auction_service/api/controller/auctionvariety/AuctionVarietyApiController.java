package com.ssafy.auction_service.api.controller.auctionvariety;

import com.ssafy.auction_service.api.ApiResponse;
import com.ssafy.auction_service.api.controller.auctionvariety.request.AuctionVarietyCreateRequest;
import com.ssafy.auction_service.api.controller.auctionvariety.request.AuctionVarietyModifyRequest;
import com.ssafy.auction_service.api.service.auctionvariety.AuctionVarietyService;
import com.ssafy.auction_service.api.service.auctionvariety.response.AuctionVarietyCreateResponse;
import com.ssafy.auction_service.api.service.auctionvariety.response.AuctionVarietyModifyResponse;
import com.ssafy.auction_service.api.service.auctionvariety.response.AuctionVarietyRemoveResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auction-service/auction-varieties")
public class AuctionVarietyApiController {

    private final AuctionVarietyService auctionVarietyService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<AuctionVarietyCreateResponse> createAuctionVariety(@Valid @RequestBody AuctionVarietyCreateRequest request) {
        AuctionVarietyCreateResponse response = auctionVarietyService.createAuctionVariety(request.getVarietyCode(), request.getAuctionScheduleId(), request.toServiceRequest());

        return ApiResponse.created(response);
    }

    @PatchMapping("/{auctionVarietyId}")
    public ApiResponse<AuctionVarietyModifyResponse> modifyAuctionVariety(@PathVariable Integer auctionVarietyId, @Valid @RequestBody AuctionVarietyModifyRequest request) {
        AuctionVarietyModifyResponse response = auctionVarietyService.modifyAuctionVariety(auctionVarietyId, request.toServiceRequest());

        return ApiResponse.ok(response);
    }

    @DeleteMapping("/{auctionVarietyId}")
    public ApiResponse<AuctionVarietyRemoveResponse> removeAuctionVariety(@PathVariable Integer auctionVarietyId) {
        return null;
    }
}
