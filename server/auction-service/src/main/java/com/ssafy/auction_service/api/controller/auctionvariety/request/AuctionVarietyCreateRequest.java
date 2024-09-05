package com.ssafy.auction_service.api.controller.auctionvariety.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuctionVarietyCreateRequest {

    private String plantGrade;
    private String plantCount;
    private String auctionStartPrice;
    private String region;
    private String shipper;

    @Builder
    private AuctionVarietyCreateRequest(String plantGrade, String plantCount, String auctionStartPrice, String region, String shipper) {
        this.plantGrade = plantGrade;
        this.plantCount = plantCount;
        this.auctionStartPrice = auctionStartPrice;
        this.region = region;
        this.shipper = shipper;
    }
}
