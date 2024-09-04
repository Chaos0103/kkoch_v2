package com.ssafy.auction_service.api.service.auctionvariety.response;

import com.ssafy.auction_service.domain.auctionvariety.AuctionVariety;
import com.ssafy.auction_service.domain.auctionvariety.PlantGrade;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AuctionVarietyCreateResponse {

    private Long id;
    private PlantGrade plantGrade;
    private int plantCount;
    private int auctionStartPrice;
    private String region;
    private String shipper;
    private LocalDateTime createdDateTime;

    @Builder
    private AuctionVarietyCreateResponse(Long id, PlantGrade plantGrade, int plantCount, int auctionStartPrice, String region, String shipper, LocalDateTime createdDateTime) {
        this.id = id;
        this.plantGrade = plantGrade;
        this.plantCount = plantCount;
        this.auctionStartPrice = auctionStartPrice;
        this.region = region;
        this.shipper = shipper;
        this.createdDateTime = createdDateTime;
    }

    public static AuctionVarietyCreateResponse of(AuctionVariety auctionVariety) {
        return new AuctionVarietyCreateResponse(
            auctionVariety.getId(),
            auctionVariety.getAuctionPlant().getPlantGrade(),
            auctionVariety.getAuctionPlant().getPlantCount(),
            auctionVariety.getAuctionPlant().getAuctionStartPrice(),
            auctionVariety.getShipment().getRegion(),
            auctionVariety.getShipment().getShipper(),
            auctionVariety.getCreatedDateTime()
        );
    }
}
