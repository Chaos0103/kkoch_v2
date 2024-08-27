package com.ssafy.auction_service.api.controller.variety;

import com.ssafy.auction_service.api.ApiResponse;
import com.ssafy.auction_service.api.controller.variety.request.VarietyCreateRequest;
import com.ssafy.auction_service.api.service.variety.VarietyService;
import com.ssafy.auction_service.api.service.variety.response.VarietyCreateResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auction-service/varieties")
public class VarietyApiController {

    private final VarietyService varietyService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<VarietyCreateResponse> createVariety(@Valid @RequestBody VarietyCreateRequest request) {
        return null;
    }
}
