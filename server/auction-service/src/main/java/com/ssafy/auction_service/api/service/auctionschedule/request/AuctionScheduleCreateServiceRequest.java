package com.ssafy.auction_service.api.service.auctionschedule.request;

import com.ssafy.auction_service.common.util.TimeUtils;
import com.ssafy.auction_service.domain.auctionschedule.AuctionSchedule;
import com.ssafy.auction_service.domain.auctionschedule.JointMarket;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import lombok.Builder;

import java.time.LocalDateTime;

import static com.ssafy.auction_service.api.service.auctionschedule.AuctionScheduleUtils.*;

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
        validatePlantCategory(plantCategory);
        validateJointMarket(jointMarket);
        return new AuctionScheduleCreateServiceRequest(plantCategory, jointMarket, auctionDescription, auctionStartDateTime);

    }

    public AuctionSchedule toEntity(Long createdBy, LocalDateTime current) {
        validateAuctionStartDateTime(auctionStartDateTime, current);

        return AuctionSchedule.create(createdBy,
            PlantCategory.of(plantCategory),
            JointMarket.of(jointMarket),
            auctionDescription,
            TimeUtils.parse(auctionStartDateTime));
    }

    public PlantCategory getPlantCategory() {
        return PlantCategory.of(plantCategory);
    }

    public JointMarket getJointMarket() {
        return JointMarket.of(jointMarket);
    }

    public LocalDateTime getAuctionStartDateTime() {
        return TimeUtils.parse(auctionStartDateTime);
    }
}
