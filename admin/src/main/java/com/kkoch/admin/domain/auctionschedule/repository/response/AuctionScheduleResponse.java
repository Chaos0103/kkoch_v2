package com.kkoch.admin.domain.auctionschedule.repository.response;

import com.kkoch.admin.domain.auctionschedule.AuctionRoomStatus;
import com.kkoch.admin.domain.variety.PlantCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AuctionScheduleResponse {

    private int auctionScheduleId;
    private PlantCategory plantCategory;
    private AuctionRoomStatus roomStatus;
    private LocalDateTime auctionDateTime;
    private LocalDateTime createdDateTime;

    @Builder
    private AuctionScheduleResponse(int auctionScheduleId, PlantCategory plantCategory, AuctionRoomStatus roomStatus, LocalDateTime auctionDateTime, LocalDateTime createdDateTime) {
        this.auctionScheduleId = auctionScheduleId;
        this.plantCategory = plantCategory;
        this.roomStatus = roomStatus;
        this.auctionDateTime = auctionDateTime;
        this.createdDateTime = createdDateTime;
    }
}
