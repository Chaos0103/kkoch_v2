package com.ssafy.auction_service.api.service.auctionschedule.response;

import com.ssafy.auction_service.domain.auctionschedule.AuctionSchedule;
import com.ssafy.auction_service.domain.auctionschedule.AuctionStatue;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AuctionScheduleCreateResponse {

    private int id;
    private String plantCategory;
    private String jointMarket;
    private LocalDateTime auctionStartDateTime;
    private AuctionStatue auctionStatus;
    private LocalDateTime createdDateTime;

    private AuctionScheduleCreateResponse(int id, String plantCategory, String jointMarket, LocalDateTime auctionStartDateTime, AuctionStatue auctionStatus, LocalDateTime createdDateTime) {
        this.id = id;
        this.plantCategory = plantCategory;
        this.jointMarket = jointMarket;
        this.auctionStartDateTime = auctionStartDateTime;
        this.auctionStatus = auctionStatus;
        this.createdDateTime = createdDateTime;
    }

    public static AuctionScheduleCreateResponse of(AuctionSchedule auctionSchedule) {
        return new AuctionScheduleCreateResponse(auctionSchedule.getId(), auctionSchedule.getPlantCategoryDescription(), auctionSchedule.getKoreanJointMarket(), auctionSchedule.getAuctionStartDateTime(), auctionSchedule.getAuctionStatue(), auctionSchedule.getCreatedDateTime());
    }
}
