package com.kkoch.admin.api.service.auctionschedule.request;

import com.kkoch.admin.domain.auctionschedule.AuctionSchedule;
import com.kkoch.admin.domain.auctionschedule.PlantCode;
import com.kkoch.admin.domain.admin.Admin;
import lombok.Builder;

import java.time.LocalDateTime;

public class AuctionScheduleCreateServiceRequest {

    private final String code;
    private final LocalDateTime auctionDateTime;

    @Builder
    private AuctionScheduleCreateServiceRequest(String code, LocalDateTime auctionDateTime) {
        this.code = code;
        this.auctionDateTime = auctionDateTime;
    }

    public static AuctionScheduleCreateServiceRequest of(String code, LocalDateTime actionDateTime) {
        return new AuctionScheduleCreateServiceRequest(code, actionDateTime);
    }

    public AuctionSchedule toEntity(Admin admin) {
        return AuctionSchedule.create(PlantCode.valueOf(code), auctionDateTime, admin);
    }
}
