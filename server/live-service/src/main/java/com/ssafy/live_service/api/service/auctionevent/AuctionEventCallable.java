package com.ssafy.live_service.api.service.auctionevent;

import com.ssafy.live_service.api.service.auctionevent.request.BidServiceRequest;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;

public class AuctionEventCallable implements Callable<Boolean> {

    private final AuctionEventService auctionEventService;
    private final String memberKey;
    private final BidServiceRequest bid;
    private final LocalDateTime current;

    public AuctionEventCallable(AuctionEventService auctionEventService, String memberKey, BidServiceRequest bid, LocalDateTime current) {
        this.auctionEventService = auctionEventService;
        this.memberKey = memberKey;
        this.bid = bid;
        this.current = current;
    }

    @Override
    public Boolean call() throws Exception {
        long millis = System.currentTimeMillis();
        return auctionEventService.addQueue(memberKey, bid, current, millis);
    }
}
