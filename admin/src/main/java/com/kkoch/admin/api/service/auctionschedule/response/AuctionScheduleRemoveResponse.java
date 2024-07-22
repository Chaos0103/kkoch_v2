package com.kkoch.admin.api.service.auctionschedule.response;

import com.kkoch.admin.domain.auctionschedule.AuctionSchedule;
import com.kkoch.admin.domain.auctionschedule.AuctionRoomStatus;
import com.kkoch.admin.domain.variety.PlantCategory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AuctionScheduleRemoveResponse {

    private final int auctionScheduleId;
    private final PlantCategory code;
    private final AuctionRoomStatus roomStatus;
    private final LocalDateTime auctionDateTime;
    private final LocalDateTime removedDateTime;

    @Builder
    private AuctionScheduleRemoveResponse(int auctionScheduleId, PlantCategory code, AuctionRoomStatus roomStatus, LocalDateTime auctionDateTime, LocalDateTime removedDateTime) {
        this.auctionScheduleId = auctionScheduleId;
        this.code = code;
        this.roomStatus = roomStatus;
        this.auctionDateTime = auctionDateTime;
        this.removedDateTime = removedDateTime;
    }

    public static AuctionScheduleRemoveResponse of(AuctionSchedule auctionSchedule) {
        return AuctionScheduleRemoveResponse.builder()
            .auctionScheduleId(auctionSchedule.getId())
            .code(auctionSchedule.getPlantCategory())
            .roomStatus(auctionSchedule.getRoomStatus())
            .auctionDateTime(auctionSchedule.getAuctionDateTime())
            .removedDateTime(auctionSchedule.getLastModifiedDateTime())
            .build();
    }
}
