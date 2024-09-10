package com.ssafy.auction_service.api.controller.auctionschedule.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuctionScheduleModifyRequest {

    private String auctionStartDateTime;
    private String auctionDescription;

    @Builder
    private AuctionScheduleModifyRequest(String auctionStartDateTime, String auctionDescription) {
        this.auctionStartDateTime = auctionStartDateTime;
        this.auctionDescription = auctionDescription;
    }
}
