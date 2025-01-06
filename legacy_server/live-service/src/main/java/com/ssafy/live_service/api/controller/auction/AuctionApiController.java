package com.ssafy.live_service.api.controller.auction;

import com.ssafy.live_service.api.ApiResponse;
import com.ssafy.live_service.api.controller.auction.response.AuctionParticipationSuccessResponse;
import com.ssafy.live_service.api.service.auction.AuctionRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/live-service/auction-rooms/{auctionScheduleId}")
public class AuctionApiController {

    private final AuctionRoomService auctionRoomService;

    @GetMapping("/participate")
    public ApiResponse<AuctionParticipationSuccessResponse> participateAuction(@PathVariable Integer auctionScheduleId) {
        int generatedParticipationNumber = auctionRoomService.generateParticipationNumber(auctionScheduleId);

        AuctionParticipationSuccessResponse response = AuctionParticipationSuccessResponse.of(generatedParticipationNumber);

        return ApiResponse.ok(response);
    }
}
