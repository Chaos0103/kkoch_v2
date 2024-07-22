package com.kkoch.admin.api.service.auctionvariety.response;

import com.kkoch.admin.domain.auctionvariety.AuctionVariety;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AuctionVarietyCreateResponse {

    private final String auctionNumber;
    private final LocalDateTime createdDateTime;

    @Builder
    private AuctionVarietyCreateResponse(String auctionNumber, LocalDateTime createdDateTime) {
        this.auctionNumber = auctionNumber;
        this.createdDateTime = createdDateTime;
    }

    public static AuctionVarietyCreateResponse of(AuctionVariety auctionVariety) {
        return new AuctionVarietyCreateResponse(auctionVariety.getAuctionNumber(), auctionVariety.getCreatedDateTime());
    }
}
