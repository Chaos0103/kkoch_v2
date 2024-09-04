package com.ssafy.auction_service.api.service.auctionvariety.request;

import com.ssafy.auction_service.domain.auctionschedule.AuctionSchedule;
import com.ssafy.auction_service.domain.auctionvariety.AuctionPlant;
import com.ssafy.auction_service.domain.auctionvariety.AuctionVariety;
import com.ssafy.auction_service.domain.auctionvariety.Shipment;
import com.ssafy.auction_service.domain.variety.Variety;
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

    public AuctionVariety toEntity(Long memberId, AuctionSchedule auctionSchedule, Variety variety, String listingNumber) {
        AuctionPlant auctionPlant = AuctionPlant.create(plantGrade, plantCount, auctionStartPrice);
        Shipment shipment = Shipment.of(region, shipper);
        return AuctionVariety.create(memberId, auctionSchedule, variety, listingNumber, auctionPlant, shipment);
    }
}
