package com.ssafy.auction_service.domain.auctionschedule.repository.response;

import com.ssafy.auction_service.domain.auctionschedule.AuctionStatus;
import com.ssafy.auction_service.domain.auctionschedule.JointMarket;
import com.ssafy.auction_service.domain.variety.PlantCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AuctionScheduleDetailResponse {

    private int id;
    private PlantCategory plantCategory;
    private JointMarket jointMarket;
    private LocalDateTime auctionStartDateTime;
    private AuctionStatus auctionStatus;
    private String auctionDescription;
    private int auctionVarietyCount;

    @Builder
    private AuctionScheduleDetailResponse(int id, PlantCategory plantCategory, JointMarket jointMarket, LocalDateTime auctionStartDateTime, AuctionStatus auctionStatus, String auctionDescription, int auctionVarietyCount) {
        this.id = id;
        this.plantCategory = plantCategory;
        this.jointMarket = jointMarket;
        this.auctionStartDateTime = auctionStartDateTime;
        this.auctionStatus = auctionStatus;
        this.auctionDescription = auctionDescription;
        this.auctionVarietyCount = auctionVarietyCount;
    }
}
