package com.ssafy.trade_service.api.service.order.response.orderdetail;

import lombok.Builder;

import java.time.LocalDateTime;

public class BidResult {

    private Long id;
    private Variety variety;
    private int bidPrice;
    private LocalDateTime bidDateTime;

    @Builder
    private BidResult(Long id, Variety variety, int bidPrice, LocalDateTime bidDateTime) {
        this.id = id;
        this.variety = variety;
        this.bidPrice = bidPrice;
        this.bidDateTime = bidDateTime;
    }

    public static BidResult of(Long id, Variety variety, int bidPrice, LocalDateTime bidDateTime) {
        return new BidResult(id, variety, bidPrice, bidDateTime);
    }
}
