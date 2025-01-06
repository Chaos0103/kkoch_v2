package com.ssafy.live_service.api.service.auction.vo;

import lombok.Builder;

import java.io.Serializable;

public class AuctionVariety implements Serializable {

    private final long id;
    private final String code;
    private final String plantCategory;
    private final String itemName;
    private final String varietyName;
    private final String listingNumber;
    private final String plantGrade;
    private final int plantCount;
    private final int auctionStartPrice;
    private final String region;
    private final String shipper;

    @Builder
    private AuctionVariety(long id, String code, String plantCategory, String itemName, String varietyName, String listingNumber, String plantGrade, int plantCount, int auctionStartPrice, String region, String shipper) {
        this.id = id;
        this.code = code;
        this.plantCategory = plantCategory;
        this.itemName = itemName;
        this.varietyName = varietyName;
        this.listingNumber = listingNumber;
        this.plantGrade = plantGrade;
        this.plantCount = plantCount;
        this.auctionStartPrice = auctionStartPrice;
        this.region = region;
        this.shipper = shipper;
    }

    public static AuctionVariety of(long id, String code, String plantCategory, String itemName, String varietyName, String listingNumber, String plantGrade, int plantCount, int auctionStartPrice, String region, String shipper) {
        return new AuctionVariety(id, code, plantCategory, itemName, varietyName, listingNumber, plantGrade, plantCount, auctionStartPrice, region, shipper);
    }

    public String getVarietyCode() {
        return code;
    }
}
