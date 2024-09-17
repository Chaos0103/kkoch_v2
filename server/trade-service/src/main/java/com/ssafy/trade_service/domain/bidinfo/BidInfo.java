package com.ssafy.trade_service.domain.bidinfo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BidInfo {

    private Long auctionVarietyId;
    private int bidPrice;

    @Builder
    private BidInfo(Long auctionVarietyId, int bidPrice) {
        this.auctionVarietyId = auctionVarietyId;
        this.bidPrice = bidPrice;
    }

    public static BidInfo of(Long auctionVarietyId, int bidPrice) {
        return new BidInfo(auctionVarietyId, bidPrice);
    }
}
