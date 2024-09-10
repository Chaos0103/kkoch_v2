package com.ssafy.auction_service.api.service.auctionschedule.response;

import com.ssafy.auction_service.domain.auctionschedule.AuctionStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AuctionScheduleRemoveResponse {

    private int id;
    private String plantCategory;
    private String jointMarket;
    private LocalDateTime auctionStartDateTime;
    private AuctionStatus auctionStatus;
    private LocalDateTime removedDateTime;

    @Builder
    private AuctionScheduleRemoveResponse(int id, String plantCategory, String jointMarket, LocalDateTime auctionStartDateTime, AuctionStatus auctionStatus, LocalDateTime removedDateTime) {
        this.id = id;
        this.plantCategory = plantCategory;
        this.jointMarket = jointMarket;
        this.auctionStartDateTime = auctionStartDateTime;
        this.auctionStatus = auctionStatus;
        this.removedDateTime = removedDateTime;
    }
}
