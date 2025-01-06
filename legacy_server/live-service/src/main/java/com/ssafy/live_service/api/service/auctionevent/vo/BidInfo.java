package com.ssafy.live_service.api.service.auctionevent.vo;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public class BidInfo implements Serializable {

    private final String memberKey;
    private final Long auctionVarietyId;
    private final String varietyCode;
    private final String plantGrade;
    private final int plantCount;
    private final int bidPrice;
    private final LocalDateTime bidDateTime;

    @Builder
    private BidInfo(String memberKey, Long auctionVarietyId, String varietyCode, String plantGrade, int plantCount, int bidPrice, LocalDateTime bidDateTime) {
        this.memberKey = memberKey;
        this.auctionVarietyId = auctionVarietyId;
        this.varietyCode = varietyCode;
        this.plantGrade = plantGrade;
        this.plantCount = plantCount;
        this.bidPrice = bidPrice;
        this.bidDateTime = bidDateTime;
    }

    public static BidInfo of(String memberKey, Long auctionVarietyId, String varietyCode, String plantGrade, int plantCount, int bidPrice, LocalDateTime bidDateTime) {
        return new BidInfo(memberKey, auctionVarietyId, varietyCode, plantGrade, plantCount, bidPrice, bidDateTime);
    }
}
