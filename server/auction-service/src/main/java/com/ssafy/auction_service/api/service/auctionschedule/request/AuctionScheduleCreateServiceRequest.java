package com.ssafy.auction_service.api.service.auctionschedule.request;

import lombok.Builder;

public class AuctionScheduleCreateServiceRequest {

    private final String plantCategory;
    private final String jointMarket;
    private final String auctionDescription;
    private final String auctionStartDateTime;

    @Builder
    private AuctionScheduleCreateServiceRequest(String plantCategory, String jointMarket, String auctionDescription, String auctionStartDateTime) {
        this.plantCategory = plantCategory;
        this.jointMarket = jointMarket;
        this.auctionDescription = auctionDescription;
        this.auctionStartDateTime = auctionStartDateTime;
    }

    public static AuctionScheduleCreateServiceRequest of(String plantCategory, String jointMarket, String auctionDescription, String auctionStartDateTime) {
        return new AuctionScheduleCreateServiceRequest(plantCategory, jointMarket, auctionDescription, auctionStartDateTime);
    }
}
