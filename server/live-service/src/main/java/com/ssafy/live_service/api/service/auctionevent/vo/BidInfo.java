package com.ssafy.live_service.api.service.auctionevent.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BidInfo {

    private Long auctionVarietyId;
    private String varietyCode;
    private String plantGrade;
    private int plantCount;
    private int bidPrice;
    private LocalDateTime bidDateTime;

    private BidInfo(Long auctionVarietyId, String varietyCode, String plantGrade, int plantCount, int bidPrice, LocalDateTime bidDateTime) {
        this.auctionVarietyId = auctionVarietyId;
        this.varietyCode = varietyCode;
        this.plantGrade = plantGrade;
        this.plantCount = plantCount;
        this.bidPrice = bidPrice;
        this.bidDateTime = bidDateTime;
    }

    public static BidInfo of(Long auctionVarietyId, String varietyCode, String plantGrade, int plantCount, int bidPrice, LocalDateTime bidDateTime) {
        return new BidInfo(auctionVarietyId, varietyCode, plantGrade, plantCount, bidPrice, bidDateTime);
    }
}
