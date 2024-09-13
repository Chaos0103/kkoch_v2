package com.ssafy.auction_service.domain.auctionvariety.repository.response;

import com.ssafy.auction_service.domain.auctionvariety.PlantGrade;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuctionVarietyResponse {

    private long id;

    private String code;
    private PlantCategory plantCategory;
    private String itemName;
    private String varietyName;

    private String listingNumber;
    private PlantGrade plantGrade;
    private int plantCount;
    private int auctionStartPrice;
    private String region;
    private String shipper;

    @Builder
    private AuctionVarietyResponse(long id, String code, PlantCategory plantCategory, String itemName, String varietyName, String listingNumber, PlantGrade plantGrade, int plantCount, int auctionStartPrice, String region, String shipper) {
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
}
