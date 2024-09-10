package com.ssafy.auction_service.api.service.auctionschedule.response;

import com.ssafy.auction_service.domain.auctionschedule.AuctionSchedule;
import com.ssafy.auction_service.domain.auctionschedule.AuctionStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AuctionScheduleModifyResponse {

    private int id;
    private String plantCategory;
    private String jointMarket;
    private LocalDateTime auctionStartDateTime;
    private AuctionStatus auctionStatus;
    private LocalDateTime modifiedDateTime;

    @Builder
    private AuctionScheduleModifyResponse(int id, String plantCategory, String jointMarket, LocalDateTime auctionStartDateTime, AuctionStatus auctionStatus, LocalDateTime modifiedDateTime) {
        this.id = id;
        this.plantCategory = plantCategory;
        this.jointMarket = jointMarket;
        this.auctionStartDateTime = auctionStartDateTime;
        this.auctionStatus = auctionStatus;
        this.modifiedDateTime = modifiedDateTime;
    }

    public static AuctionScheduleModifyResponse of(AuctionSchedule auctionSchedule, LocalDateTime current) {
        return new AuctionScheduleModifyResponse(
            auctionSchedule.getId(),
            auctionSchedule.getPlantCategoryDescription(),
            auctionSchedule.getKoreanJointMarket(),
            auctionSchedule.getAuctionInfo().getAuctionStartDateTime(),
            auctionSchedule.getAuctionStatus(),
            current
        );
    }
}
