package com.ssafy.live_service.api.service.auctionevent.request;

import com.ssafy.live_service.api.service.auctionevent.vo.BidInfo;
import lombok.Builder;

import java.time.LocalDateTime;

public class BidServiceRequest {

    private final Long auctionVarietyId;
    private final String varietyCode;
    private final String plantGrade;
    private final int plantCount;
    private final int bidPrice;

    @Builder
    private BidServiceRequest(Long auctionVarietyId, String varietyCode, String plantGrade, int plantCount, int bidPrice) {
        this.auctionVarietyId = auctionVarietyId;
        this.varietyCode = varietyCode;
        this.plantGrade = plantGrade;
        this.plantCount = plantCount;
        this.bidPrice = bidPrice;
    }

    public static BidServiceRequest of(Long auctionVarietyId, String varietyCode, String plantGrade, int plantCount, int bidPrice) {
        return new BidServiceRequest(auctionVarietyId, varietyCode, plantGrade, plantCount, bidPrice);
    }

    public BidInfo toValue(String memberKey, LocalDateTime current) {
        return BidInfo.of(memberKey, auctionVarietyId, varietyCode, plantGrade, plantCount, bidPrice, current);
    }

    public String getKey() {
        return String.valueOf(auctionVarietyId);
    }
}
