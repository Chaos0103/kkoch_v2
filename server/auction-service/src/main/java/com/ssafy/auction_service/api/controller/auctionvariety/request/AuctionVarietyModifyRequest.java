package com.ssafy.auction_service.api.controller.auctionvariety.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuctionVarietyModifyRequest {

    private String plantGrade;
    private Integer plantCount;
    private Integer auctionStartPrice;

    @Builder
    private AuctionVarietyModifyRequest(String plantGrade, Integer plantCount, Integer auctionStartPrice) {
        this.plantGrade = plantGrade;
        this.plantCount = plantCount;
        this.auctionStartPrice = auctionStartPrice;
    }
}
