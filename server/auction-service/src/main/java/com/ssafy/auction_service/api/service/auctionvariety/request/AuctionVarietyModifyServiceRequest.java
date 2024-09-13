package com.ssafy.auction_service.api.service.auctionvariety.request;

import com.ssafy.auction_service.domain.auctionvariety.PlantGrade;
import com.ssafy.auction_service.domain.auctionvariety.Price;
import lombok.Builder;

public class AuctionVarietyModifyServiceRequest {

    private final PlantGrade plantGrade;
    private final int plantCount;
    private final Price auctionStartPrice;

    @Builder
    private AuctionVarietyModifyServiceRequest(PlantGrade plantGrade, int plantCount, Price auctionStartPrice) {
        this.plantGrade = plantGrade;
        this.plantCount = plantCount;
        this.auctionStartPrice = auctionStartPrice;
    }

    public static AuctionVarietyModifyServiceRequest of(String plantGrade, int plantCount, int auctionStartPrice) {
        return new AuctionVarietyModifyServiceRequest(PlantGrade.of(plantGrade), plantCount, Price.of(auctionStartPrice));
    }
}
