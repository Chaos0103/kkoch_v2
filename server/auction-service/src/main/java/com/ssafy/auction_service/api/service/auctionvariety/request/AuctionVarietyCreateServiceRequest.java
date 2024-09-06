package com.ssafy.auction_service.api.service.auctionvariety.request;

import com.ssafy.auction_service.domain.auctionschedule.AuctionSchedule;
import com.ssafy.auction_service.domain.auctionvariety.*;
import com.ssafy.auction_service.domain.variety.Variety;
import lombok.Builder;

import static com.ssafy.auction_service.api.service.auctionvariety.AuctionVarietyUtils.*;

public class AuctionVarietyCreateServiceRequest {

    private final PlantGrade plantGrade;
    private final int plantCount;
    private final Price auctionStartPrice;
    private final String region;
    private final String shipper;

    @Builder
    private AuctionVarietyCreateServiceRequest(PlantGrade plantGrade, int plantCount, Price auctionStartPrice, String region, String shipper) {
        this.plantGrade = plantGrade;
        this.plantCount = plantCount;
        this.auctionStartPrice = auctionStartPrice;
        this.region = region;
        this.shipper = shipper;
    }

    public static AuctionVarietyCreateServiceRequest of(String plantGrade, int plantCount, int auctionStartPrice, String region, String shipper) {
        validateRegion(region);
        validateShipper(shipper);
        return new AuctionVarietyCreateServiceRequest(parsePlantGrade(plantGrade), plantCount, Price.of(auctionStartPrice), region, shipper);
    }

    public AuctionVariety toEntity(Long memberId, AuctionSchedule auctionSchedule, Variety variety, String listingNumber) {
        AuctionPlant auctionPlant = AuctionPlant.of(plantGrade, plantCount, auctionStartPrice);
        Shipment shipment = Shipment.of(region, shipper);
        return AuctionVariety.create(memberId, auctionSchedule, variety, listingNumber, auctionPlant, shipment);
    }
}
