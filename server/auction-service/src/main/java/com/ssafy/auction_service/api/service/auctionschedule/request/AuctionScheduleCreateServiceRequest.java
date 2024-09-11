package com.ssafy.auction_service.api.service.auctionschedule.request;

import com.ssafy.auction_service.common.util.TimeUtils;
import com.ssafy.auction_service.domain.auctionschedule.AuctionInfo;
import com.ssafy.auction_service.domain.auctionschedule.AuctionSchedule;
import com.ssafy.auction_service.domain.auctionschedule.JointMarket;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import lombok.Builder;

import java.time.LocalDateTime;

import static com.ssafy.auction_service.api.service.auctionschedule.AuctionScheduleUtils.validateAuctionStartDateTime;

public class AuctionScheduleCreateServiceRequest {

    private final PlantCategory plantCategory;
    private final JointMarket jointMarket;
    private final String auctionDescription;
    private final LocalDateTime auctionStartDateTime;

    @Builder
    private AuctionScheduleCreateServiceRequest(PlantCategory plantCategory, JointMarket jointMarket, String auctionDescription, LocalDateTime auctionStartDateTime) {
        this.plantCategory = plantCategory;
        this.jointMarket = jointMarket;
        this.auctionDescription = auctionDescription;
        this.auctionStartDateTime = auctionStartDateTime;
    }

    public static AuctionScheduleCreateServiceRequest of(String plantCategory, String jointMarket, String auctionDescription, String auctionStartDateTime) {
        return new AuctionScheduleCreateServiceRequest(PlantCategory.of(plantCategory), JointMarket.of(jointMarket), auctionDescription, TimeUtils.parse(auctionStartDateTime));
    }

    public AuctionSchedule toEntity() {
        return AuctionSchedule.create(plantCategory, jointMarket, auctionStartDateTime, auctionDescription);
    }

    public void checkAuctionStartDateTime(LocalDateTime current) {
        validateAuctionStartDateTime(auctionStartDateTime, current);
    }

    public AuctionInfo getAuctionInfo() {
        return AuctionInfo.of(plantCategory, jointMarket, auctionStartDateTime);
    }
}
