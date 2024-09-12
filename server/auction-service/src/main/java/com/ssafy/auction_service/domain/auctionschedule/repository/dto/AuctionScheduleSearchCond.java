package com.ssafy.auction_service.domain.auctionschedule.repository.dto;

import com.ssafy.auction_service.domain.auctionschedule.JointMarket;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AuctionScheduleSearchCond {

    private final PlantCategory plantCategory;
    private final JointMarket jointMarket;

    @Builder
    private AuctionScheduleSearchCond(PlantCategory plantCategory, JointMarket jointMarket) {
        this.plantCategory = plantCategory;
        this.jointMarket = jointMarket;
    }

    public static AuctionScheduleSearchCond of(PlantCategory plantCategory, JointMarket jointMarket) {
        return new AuctionScheduleSearchCond(plantCategory, jointMarket);
    }
}
