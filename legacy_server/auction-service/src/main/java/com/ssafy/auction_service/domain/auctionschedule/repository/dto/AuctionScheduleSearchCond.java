package com.ssafy.auction_service.domain.auctionschedule.repository.dto;

import com.ssafy.auction_service.domain.auctionschedule.JointMarket;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import lombok.Builder;
import lombok.Getter;

import static org.springframework.util.StringUtils.hasText;

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

    public static AuctionScheduleSearchCond of(String plantCategory, String jointMarket) {
        return new AuctionScheduleSearchCond(parsePlantCategory(plantCategory), parseJointMarket(jointMarket));
    }

    private static PlantCategory parsePlantCategory(String plantCategory) {
        return hasText(plantCategory) ? PlantCategory.of(plantCategory) : null;
    }

    private static JointMarket parseJointMarket(String jointMarket) {
        return hasText(jointMarket) ? JointMarket.of(jointMarket) : null;
    }
}
