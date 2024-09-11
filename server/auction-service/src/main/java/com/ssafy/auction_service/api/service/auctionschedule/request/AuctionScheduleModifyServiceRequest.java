package com.ssafy.auction_service.api.service.auctionschedule.request;

import com.ssafy.auction_service.common.util.TimeUtils;
import com.ssafy.auction_service.domain.auctionschedule.AuctionInfo;
import com.ssafy.auction_service.domain.auctionschedule.AuctionSchedule;
import lombok.Builder;

import java.time.LocalDateTime;

import static com.ssafy.auction_service.api.service.auctionschedule.AuctionScheduleUtils.validateAuctionStartDateTime;

public class AuctionScheduleModifyServiceRequest {

    private final LocalDateTime auctionStartDateTime;
    private final String auctionDescription;

    @Builder
    private AuctionScheduleModifyServiceRequest(LocalDateTime auctionStartDateTime, String auctionDescription) {
        this.auctionStartDateTime = auctionStartDateTime;
        this.auctionDescription = auctionDescription;
    }

    public static AuctionScheduleModifyServiceRequest of(String auctionStartDateTime, String auctionDescription) {
        return new AuctionScheduleModifyServiceRequest(TimeUtils.parse(auctionStartDateTime), auctionDescription);
    }

    public void modify(AuctionSchedule auctionSchedule) {
        auctionSchedule.modify(auctionStartDateTime, auctionDescription);
    }

    public void checkAuctionStartDateTime(LocalDateTime current) {
        validateAuctionStartDateTime(auctionStartDateTime, current);
    }

    public AuctionInfo getAuctionInfo(AuctionSchedule auctionSchedule) {
        return auctionSchedule.getModifiedAuctionInfo(auctionStartDateTime);
    }
}
