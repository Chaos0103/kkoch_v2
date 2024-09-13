package com.ssafy.auction_service.api.controller.variety;

import com.ssafy.auction_service.api.ApiResponse;
import com.ssafy.auction_service.api.PageResponse;
import com.ssafy.auction_service.api.controller.variety.param.VarietySearchParam;
import com.ssafy.auction_service.api.service.variety.VarietyQueryService;
import com.ssafy.auction_service.common.util.PageUtils;
import com.ssafy.auction_service.domain.variety.repository.response.VarietyResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auction-service/varieties")
public class VarietyApiQueryController {

    private final VarietyQueryService varietyQueryService;

    @GetMapping
    public ApiResponse<PageResponse<VarietyResponse>> searchVarieties(@Valid @ModelAttribute VarietySearchParam param) {
        int pageNumber = PageUtils.parsePageNumber(param.getPage());

        PageResponse<VarietyResponse> response = varietyQueryService.searchVarieties(param.toCond(), pageNumber);

        return ApiResponse.ok(response);
    }
}
