package com.ssafy.trade_service.domain.bidinfo;

import com.ssafy.trade_service.domain.bidresult.BidResult;
import com.ssafy.trade_service.domain.order.Order;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BidInfo {

    private Long auctionVarietyId;
    private int bidPrice;
    private LocalDateTime bidDateTime;

    @Builder
    private BidInfo(Long auctionVarietyId, int bidPrice, LocalDateTime bidDateTime) {
        this.auctionVarietyId = auctionVarietyId;
        this.bidPrice = bidPrice;
        this.bidDateTime = bidDateTime;
    }

    public static BidInfo of(Long auctionVarietyId, int bidPrice, LocalDateTime bidDateTime) {
        return new BidInfo(auctionVarietyId, bidPrice, bidDateTime);
    }

    public BidResult toBidResult(Order order) {
        return BidResult.create(order, auctionVarietyId, bidPrice, bidDateTime);
    }
}
