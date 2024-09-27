package com.ssafy.live_service.api.service.auctionevent;

import com.ssafy.live_service.api.service.auctionevent.request.BidServiceRequest;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;

public class AuctionEventCallable implements Callable<Boolean> {

    private final String memberKey;
    private final BidServiceRequest bid;
    private final LocalDateTime current;

    private AuctionEventCallable(String memberKey, BidServiceRequest bid, LocalDateTime current) {
        this.memberKey = memberKey;
        this.bid = bid;
        this.current = current;
    }

    public static AuctionEventCallable of(String memberKey, BidServiceRequest bid, LocalDateTime current) {
        return new AuctionEventCallable(memberKey, bid, current);
    }

    @Override
    public Boolean call() throws Exception {
        return null;
    }
}
