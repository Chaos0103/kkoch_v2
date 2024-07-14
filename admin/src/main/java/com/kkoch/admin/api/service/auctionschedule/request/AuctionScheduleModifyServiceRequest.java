package com.kkoch.admin.api.service.auctionschedule.request;

import com.kkoch.admin.domain.auctionschedule.PlantCode;
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

    public PlantCode getCode() {
        return PlantCode.valueOf(code);
    }

    public LocalDateTime getAuctionDateTime() {
        return auctionDateTime;
    }
}
