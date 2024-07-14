package com.kkoch.admin.api.controller.auctionschedule;

import com.kkoch.admin.api.ApiResponse;
import com.kkoch.admin.api.controller.auctionschedule.request.AuctionScheduleCreateRequest;
import com.kkoch.admin.api.controller.auctionschedule.request.AuctionScheduleModifyRequest;
import com.kkoch.admin.api.service.auctionschedule.AuctionScheduleQueryService;
import com.kkoch.admin.api.service.auctionschedule.AuctionScheduleService;
import com.kkoch.admin.api.service.auctionschedule.response.AuctionScheduleCreateResponse;
import com.kkoch.admin.api.service.auctionschedule.response.AuctionScheduleModifyResponse;
import com.kkoch.admin.api.service.auctionschedule.response.AuctionScheduleRemoveResponse;
import com.kkoch.admin.api.service.auctionschedule.response.AuctionScheduleStatusResponse;
import com.kkoch.admin.domain.auctionschedule.AuctionRoomStatus;
import com.kkoch.admin.domain.auctionschedule.repository.response.OpenedAuctionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin-service/auction-schedules")
public class AuctionScheduleApiController {

    private final AuctionScheduleService auctionScheduleService;
    private final AuctionScheduleQueryService auctionScheduleQueryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<AuctionScheduleCreateResponse> createAuctionSchedule(@Valid @RequestBody AuctionScheduleCreateRequest request) {
        int adminId = 0;

        AuctionScheduleCreateResponse response = auctionScheduleService.createAuctionSchedule(adminId, request.toServiceRequest());

        return ApiResponse.created(response);
    }

    @PatchMapping("/{auctionScheduleId}")
    public ApiResponse<AuctionScheduleModifyResponse> modifyAuctionSchedule(@PathVariable int auctionScheduleId, @Valid @RequestBody AuctionScheduleModifyRequest request) {
        int adminId = 0;

        AuctionScheduleModifyResponse response = auctionScheduleService.modifyAuctionSchedule(adminId, auctionScheduleId, request.toServiceRequest());

        return ApiResponse.ok(response);
    }

    @PostMapping("/{auctionScheduleId}/ready")
    public ApiResponse<AuctionScheduleStatusResponse> readyAuctionSchedule(@PathVariable int auctionScheduleId) {
        int adminId = 0;

        AuctionScheduleStatusResponse response = auctionScheduleService.modifyAuctionRoomStatus(adminId, auctionScheduleId, AuctionRoomStatus.READY);

        return ApiResponse.ok(response);
    }

    @PostMapping("/{auctionScheduleId}/open")
    public ApiResponse<AuctionScheduleStatusResponse> openAuctionSchedule(@PathVariable int auctionScheduleId) {
        int adminId = 0;

        AuctionScheduleStatusResponse response = auctionScheduleService.modifyAuctionRoomStatus(adminId, auctionScheduleId, AuctionRoomStatus.OPEN);

        return ApiResponse.ok(response);
    }

    @PostMapping("/{auctionScheduleId}/close")
    public ApiResponse<AuctionScheduleStatusResponse> closeAuctionSchedule(@PathVariable int auctionScheduleId) {
        int adminId = 0;

        AuctionScheduleStatusResponse response = auctionScheduleService.modifyAuctionRoomStatus(adminId, auctionScheduleId, AuctionRoomStatus.CLOSE);

        return ApiResponse.ok(response);
    }

    @DeleteMapping("/{auctionSchedule}")
    public ApiResponse<AuctionScheduleRemoveResponse> removeAuctionSchedule(@PathVariable int auctionSchedule) {
        int adminId = 0;

        AuctionScheduleRemoveResponse response = auctionScheduleService.removeAuctionSchedule(adminId, auctionSchedule);

        return ApiResponse.ok(response);
    }

    @GetMapping("/open")
    public ApiResponse<OpenedAuctionResponse> searchOpenedAuction() {
        OpenedAuctionResponse response = auctionScheduleQueryService.searchOpenedAuction();
        if (response == null) {
            return ApiResponse.of(HttpStatus.OK, "현재 진행중인 경매가 없습니다.", null);
        }
        return ApiResponse.ok(response);
    }
}
