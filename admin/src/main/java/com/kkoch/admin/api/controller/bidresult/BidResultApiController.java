package com.kkoch.admin.api.controller.bidresult;

import com.kkoch.admin.api.ApiResponse;
import com.kkoch.admin.api.PageResponse;
import com.kkoch.admin.api.controller.bidresult.param.BidResultSearchParam;
import com.kkoch.admin.api.controller.bidresult.request.BidResultCreateRequest;
import com.kkoch.admin.api.service.bidresult.BidResultQueryService;
import com.kkoch.admin.api.service.bidresult.BidResultService;
import com.kkoch.admin.domain.bidresult.repository.dto.BidResultSearchCond;
import com.kkoch.admin.domain.bidresult.repository.response.BidResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin-service/bid-results")
public class BidResultApiController {

    private final BidResultService bidResultService;
    private final BidResultQueryService bidResultQueryService;

    @PostMapping("/{memberKey}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Long> createBidResult(@PathVariable String memberKey, @Valid @RequestBody BidResultCreateRequest request) {
        long bidResultId = bidResultService.createBidResult(memberKey, request.toServiceRequest());

        return ApiResponse.created(bidResultId);
    }

    @GetMapping
    public ApiResponse<PageResponse<BidResultResponse>> searchBidResults(@Valid @ModelAttribute BidResultSearchParam param) {
        BidResultSearchCond cond = param.toSearchCond();
        Pageable pageable = param.toPageable();

        PageResponse<BidResultResponse> response = bidResultQueryService.searchBidResultsBy(cond, pageable);

        return ApiResponse.ok(response);
    }
}
