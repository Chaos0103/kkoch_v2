package com.ssafy.live_service.api.controller.auctionevent;

import com.ssafy.live_service.api.ApiResponse;
import com.ssafy.live_service.api.controller.auctionevent.request.BidRequest;
import com.ssafy.live_service.api.service.auctionevent.AuctionEventCallable;
import com.ssafy.live_service.api.service.auctionevent.AuctionEventService;
import com.ssafy.live_service.api.service.auctionevent.response.AuctionEventResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.concurrent.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/live-service/auction-events")
public class AuctionEventApiController {

    private static final ExecutorService es = new ThreadPoolExecutor(10, 20, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
    private final AuctionEventService auctionEventService;

    @PostMapping("/{memberKey}")
    public ApiResponse<Boolean> bid(@PathVariable String memberKey, @Valid @RequestBody BidRequest request) throws ExecutionException, InterruptedException {
        LocalDateTime current = LocalDateTime.now();

        AuctionEventCallable auctionEventCallable = new AuctionEventCallable(auctionEventService, memberKey, request.toServiceRequest(), current);

        Future<Boolean> future = es.submit(auctionEventCallable);

        return ApiResponse.ok(future.get());
    }

    @GetMapping("/{auctionVarietyId}")
    public ApiResponse<AuctionEventResponse> endBid(@PathVariable Long auctionVarietyId) {
        AuctionEventResponse response = auctionEventService.publish(auctionVarietyId);

        return ApiResponse.ok(response);
    }
}
