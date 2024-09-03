package com.ssafy.auction_service.api.service.auctionvariety.request;

import lombok.Builder;

public class AuctionVarietyCreateServiceRequest {

    private final String plantGrade;
    private final int plantCount;
    private final int auctionStartPrice;
    private final String region;
    private final String shipper;

    @Builder
    private AuctionVarietyCreateServiceRequest(String plantGrade, int plantCount, int auctionStartPrice, String region, String shipper) {
        this.plantGrade = plantGrade;
        this.plantCount = plantCount;
        this.auctionStartPrice = auctionStartPrice;
        this.region = region;
        this.shipper = shipper;
    }

    public static AuctionVarietyCreateServiceRequest of(String plantGrade, int plantCount, int auctionStartPrice, String region, String shipper) {
        return new AuctionVarietyCreateServiceRequest(plantGrade, plantCount, auctionStartPrice, region, shipper);
    }
}
