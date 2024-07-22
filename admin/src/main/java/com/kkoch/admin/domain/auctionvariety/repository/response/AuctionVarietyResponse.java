package com.kkoch.admin.domain.auctionvariety.repository.response;

import com.kkoch.admin.domain.Grade;
import com.kkoch.admin.domain.variety.PlantCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuctionVarietyResponse {

    private long auctionVarietyId;
    private String auctionNumber;
    private String varietyCode;
    private PlantCategory plantCategory;
    private String itemName;
    private String varietyName;
    private int plantCount;
    private int startPrice;
    private Grade grade;
    private String region;
    private String shipper;

    @Builder
    private AuctionVarietyResponse(long auctionVarietyId, String auctionNumber, String varietyCode, PlantCategory plantCategory, String itemName, String varietyName, int plantCount, int startPrice, Grade grade, String region, String shipper) {
        this.auctionVarietyId = auctionVarietyId;
        this.auctionNumber = auctionNumber;
        this.varietyCode = varietyCode;
        this.plantCategory = plantCategory;
        this.itemName = itemName;
        this.varietyName = varietyName;
        this.plantCount = plantCount;
        this.startPrice = startPrice;
        this.grade = grade;
        this.region = region;
        this.shipper = shipper;
    }
}
