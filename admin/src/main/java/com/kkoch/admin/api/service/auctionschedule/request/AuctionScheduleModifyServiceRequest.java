package com.kkoch.admin.api.service.auctionschedule.request;

import com.kkoch.admin.domain.variety.PlantCategory;
import lombok.Builder;

import java.time.LocalDateTime;

public class AuctionScheduleModifyServiceRequest {

    private final String code;
    private final LocalDateTime auctionDateTime;

    @Builder
    private AuctionScheduleModifyServiceRequest(String code, LocalDateTime auctionDateTime) {
        this.code = code;
        this.auctionDateTime = auctionDateTime;
    }

    public static AuctionScheduleModifyServiceRequest of(String code, LocalDateTime actionDateTime) {
        return new AuctionScheduleModifyServiceRequest(code, actionDateTime);
    }

    public PlantCategory getCode() {
        return PlantCategory.valueOf(code);
    }

    public LocalDateTime getAuctionDateTime() {
        return auctionDateTime;
    }
}
