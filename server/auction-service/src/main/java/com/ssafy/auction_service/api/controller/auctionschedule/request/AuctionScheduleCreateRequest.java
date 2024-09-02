package com.ssafy.auction_service.api.controller.auctionschedule.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuctionScheduleCreateRequest {

    private String plantCategory;
    private String jointMarket;
    private String auctionDescription;
    private String auctionStartDateTime;

    @Builder
    private AuctionScheduleCreateRequest(String plantCategory, String jointMarket, String auctionDescription, String auctionStartDateTime) {
        this.plantCategory = plantCategory;
        this.jointMarket = jointMarket;
        this.auctionDescription = auctionDescription;
        this.auctionStartDateTime = auctionStartDateTime;
    }
}
