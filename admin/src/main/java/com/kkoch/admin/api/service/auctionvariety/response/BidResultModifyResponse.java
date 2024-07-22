package com.kkoch.admin.api.service.auctionvariety.response;

import com.kkoch.admin.domain.auctionvariety.AuctionVariety;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BidResultModifyResponse {

    private final String auctionNumber;
    private final int bidPrice;
    private final LocalDateTime bidDateTime;

    @Builder
    private BidResultModifyResponse(String auctionNumber, int bidPrice, LocalDateTime bidDateTime) {
        this.auctionNumber = auctionNumber;
        this.bidPrice = bidPrice;
        this.bidDateTime = bidDateTime;
    }

    public static BidResultModifyResponse of(AuctionVariety auctionVariety) {
//        return BidResultModifyResponse.builder()
//            .auctionNumber(auctionVariety.getAuctionNumber())
//            .bidPrice(auctionVariety.getBidResult().getBidPrice())
//            .bidDateTime(auctionVariety.getBidResult().getBidDateTime())
//            .build();
        return null;
    }
}
