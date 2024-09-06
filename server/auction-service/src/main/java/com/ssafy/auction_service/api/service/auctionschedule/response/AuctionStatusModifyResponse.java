package com.ssafy.auction_service.api.service.auctionschedule.response;

import com.ssafy.auction_service.domain.auctionschedule.AuctionSchedule;
import com.ssafy.auction_service.domain.auctionschedule.AuctionStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AuctionStatusModifyResponse {

    private int id;
    private AuctionStatus auctionStatus;
    private LocalDateTime modifiedDateTime;

    @Builder
    private AuctionStatusModifyResponse(int id, AuctionStatus auctionStatus, LocalDateTime modifiedDateTime) {
        this.id = id;
        this.auctionStatus = auctionStatus;
        this.modifiedDateTime = modifiedDateTime;
    }

    public static AuctionStatusModifyResponse of(AuctionSchedule auctionSchedule, LocalDateTime current) {
        return new AuctionStatusModifyResponse(auctionSchedule.getId(), auctionSchedule.getAuctionStatus(), current);
    }
}
