package com.ssafy.auction_service.api.controller.auctionschedule;

import com.ssafy.auction_service.api.ApiResponse;
import com.ssafy.auction_service.api.controller.auctionschedule.request.AuctionScheduleCreateRequest;
import com.ssafy.auction_service.api.controller.auctionschedule.request.AuctionScheduleModifyRequest;
import com.ssafy.auction_service.api.service.auctionschedule.AuctionScheduleService;
import com.ssafy.auction_service.api.service.auctionschedule.response.AuctionScheduleCreateResponse;
import com.ssafy.auction_service.api.service.auctionschedule.response.AuctionScheduleModifyResponse;
import com.ssafy.auction_service.api.service.auctionschedule.response.AuctionScheduleRemoveResponse;
import com.ssafy.auction_service.api.service.auctionschedule.response.AuctionStatusModifyResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static com.ssafy.auction_service.common.util.TimeUtils.getCurrentDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auction-service/auction-schedules")
public class AuctionScheduleApiController {

    private final AuctionScheduleService auctionScheduleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<AuctionScheduleCreateResponse> createAuctionSchedule(@Valid @RequestBody AuctionScheduleCreateRequest request) {
        LocalDateTime current = getCurrentDateTime();

        AuctionScheduleCreateResponse response = auctionScheduleService.createAuctionSchedule(request.toServiceRequest(), current);

        return ApiResponse.created(response);
    }

    @PatchMapping("/{auctionScheduleId}")
    public ApiResponse<AuctionScheduleModifyResponse> modifyAuctionSchedule(
        @PathVariable Integer auctionScheduleId,
        @Valid @RequestBody AuctionScheduleModifyRequest request
    ) {
        LocalDateTime current = getCurrentDateTime();

        AuctionScheduleModifyResponse response = auctionScheduleService.modifyAuctionSchedule(auctionScheduleId, request.toServiceRequest(), current);

        return ApiResponse.ok(response);
    }

    @PostMapping("/{auctionScheduleId}/ready")
    public ApiResponse<AuctionStatusModifyResponse> modifyAuctionStatusToReady(@PathVariable Integer auctionScheduleId) {
        LocalDateTime current = getCurrentDateTime();

        AuctionStatusModifyResponse response = auctionScheduleService.modifyAuctionStatusToReady(auctionScheduleId, current);

        return ApiResponse.ok(response);
    }

    @PostMapping("/{auctionScheduleId}/progress")
    public ApiResponse<AuctionStatusModifyResponse> modifyAuctionStatusToProgress(@PathVariable Integer auctionScheduleId) {
        LocalDateTime current = getCurrentDateTime();

        AuctionStatusModifyResponse response = auctionScheduleService.modifyAuctionStatusToProgress(auctionScheduleId, current);

        return ApiResponse.ok(response);
    }

    @PostMapping("/{auctionScheduleId}/complete")
    public ApiResponse<AuctionStatusModifyResponse> modifyAuctionStatusToComplete(@PathVariable Integer auctionScheduleId) {
        LocalDateTime current = getCurrentDateTime();

        AuctionStatusModifyResponse response = auctionScheduleService.modifyAuctionStatusToComplete(auctionScheduleId, current);

        return ApiResponse.ok(response);
    }

    @DeleteMapping("/{auctionScheduleId}")
    public ApiResponse<AuctionScheduleRemoveResponse> removeAuctionSchedule(@PathVariable Integer auctionScheduleId) {
        return null;
    }
}
