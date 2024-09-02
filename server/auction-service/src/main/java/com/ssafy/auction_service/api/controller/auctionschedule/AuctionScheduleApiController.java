package com.ssafy.auction_service.api.controller.auctionschedule;

import com.ssafy.auction_service.api.ApiResponse;
import com.ssafy.auction_service.api.controller.auctionschedule.request.AuctionScheduleCreateRequest;
import com.ssafy.auction_service.api.service.auctionschedule.AuctionScheduleService;
import com.ssafy.auction_service.api.service.auctionschedule.response.AuctionScheduleCreateResponse;
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
}
