package com.ssafy.auction_service.api.controller.auctionvariety.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuctionVarietyCreateRequest {

    private String varietyCode;
    private String auctionScheduleId;
    private String plantGrade;
    private String plantCount;
    private String auctionStartPrice;
    private String region;
    private String shipper;

    @Builder
    private AuctionVarietyCreateRequest(String varietyCode, String auctionScheduleId, String plantGrade, String plantCount, String auctionStartPrice, String region, String shipper) {
        this.varietyCode = varietyCode;
        this.auctionScheduleId = auctionScheduleId;
        this.plantGrade = plantGrade;
        this.plantCount = plantCount;
        this.auctionStartPrice = auctionStartPrice;
        this.region = region;
        this.shipper = shipper;
    }
}
