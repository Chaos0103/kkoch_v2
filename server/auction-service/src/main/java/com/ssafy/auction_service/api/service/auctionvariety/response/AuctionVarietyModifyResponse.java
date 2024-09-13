package com.ssafy.auction_service.api.service.auctionvariety.response;

import com.ssafy.auction_service.domain.auctionvariety.AuctionVariety;
import com.ssafy.auction_service.domain.auctionvariety.PlantGrade;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuctionVarietyModifyResponse {

    private Long id;
    private String listingNumber;
    private PlantGrade plantGrade;
    private int plantCount;
    private int auctionStartPrice;

    @Builder
    private AuctionVarietyModifyResponse(Long id, String listingNumber, PlantGrade plantGrade, int plantCount, int auctionStartPrice) {
        this.id = id;
        this.listingNumber = listingNumber;
        this.plantGrade = plantGrade;
        this.plantCount = plantCount;
        this.auctionStartPrice = auctionStartPrice;
    }

    public static AuctionVarietyModifyResponse of(AuctionVariety auctionVariety) {
        return AuctionVarietyModifyResponse.builder()
            .id(auctionVariety.getId())
            .listingNumber(auctionVariety.getListingNumber())
            .plantGrade(auctionVariety.getAuctionPlant().getPlantGrade())
            .plantCount(auctionVariety.getAuctionPlant().getPlantCount())
            .auctionStartPrice(auctionVariety.getAuctionStartPrice())
            .build();
    }
}
