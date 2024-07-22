package com.kkoch.admin.api.controller.auctionvariety;

import com.kkoch.admin.api.ApiResponse;
import com.kkoch.admin.api.controller.auctionvariety.request.AuctionVarietyCreateRequest;
import com.kkoch.admin.api.service.auctionvariety.AuctionVarietyQueryService;
import com.kkoch.admin.api.service.auctionvariety.AuctionVarietyService;
import com.kkoch.admin.api.service.auctionvariety.response.AuctionVarietyCreateResponse;
import com.kkoch.admin.domain.auctionvariety.repository.response.AuctionVarietyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin-service/auction-varieties")
public class AuctionVarietyApiController {

    private final AuctionVarietyService auctionVarietyService;
    private final AuctionVarietyQueryService auctionVarietyQueryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<AuctionVarietyCreateResponse> createAuctionVariety(@Valid @RequestBody AuctionVarietyCreateRequest request) {
        int adminId = 1;

        AuctionVarietyCreateResponse response = auctionVarietyService.createAuctionVariety(adminId, request.getVarietyCode(), request.getAuctionScheduleId(), request.toServiceRequest());

        return ApiResponse.created(response);
    }

    @GetMapping
    public ApiResponse<List<AuctionVarietyResponse>> searchAuctionVarieties(@RequestParam int auctionScheduleId) {
        List<AuctionVarietyResponse> response = auctionVarietyQueryService.searchAuctionVarietiesBy(auctionScheduleId);

        return ApiResponse.ok(response);
    }
}
