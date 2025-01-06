package com.ssafy.auction_service.api.service.auctionschedule.request;

import com.ssafy.auction_service.common.util.TimeUtils;
import com.ssafy.auction_service.domain.auctionschedule.AuctionSchedule;
import com.ssafy.auction_service.domain.auctionschedule.JointMarket;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import lombok.Builder;

import java.time.LocalDateTime;

public class AuctionScheduleCreateServiceRequest {

    private final PlantCategory plantCategory;
    private final JointMarket jointMarket;
    private final LocalDateTime auctionStartDateTime;
    private final String auctionDescription;

    @Builder
    private AuctionScheduleCreateServiceRequest(PlantCategory plantCategory, JointMarket jointMarket, LocalDateTime auctionStartDateTime, String auctionDescription) {
        this.plantCategory = plantCategory;
        this.jointMarket = jointMarket;
        this.auctionStartDateTime = auctionStartDateTime;
        this.auctionDescription = auctionDescription;
    }

    public static AuctionScheduleCreateServiceRequest of(String plantCategory, String jointMarket, String auctionStartDateTime, String auctionDescription) {
        return new AuctionScheduleCreateServiceRequest(PlantCategory.of(plantCategory), JointMarket.of(jointMarket), TimeUtils.parse(auctionStartDateTime), auctionDescription);
    }

    public AuctionSchedule toEntity() {
        return AuctionSchedule.create(plantCategory, jointMarket, auctionStartDateTime, auctionDescription);
    }
}
