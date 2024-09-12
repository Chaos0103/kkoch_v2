package com.ssafy.auction_service.api.controller.auctionschedule.param;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuctionScheduleSearchParam {

    private String plantCategory;
    private String jointMarket;

    @Builder
    private AuctionScheduleSearchParam(String plantCategory, String jointMarket) {
        this.plantCategory = plantCategory;
        this.jointMarket = jointMarket;
    }
}
