package com.ssafy.live_service.api.controller.auctionevent.request;

import com.ssafy.live_service.api.service.auctionevent.request.BidServiceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BidRequest {

    private Long auctionVarietyId;
    private String varietyCode;
    private String plantGrade;
    private int plantCount;
    private int bidPrice;

    @Builder
    private BidRequest(Long auctionVarietyId, String varietyCode, String plantGrade, int plantCount, int bidPrice) {
        this.auctionVarietyId = auctionVarietyId;
        this.varietyCode = varietyCode;
        this.plantGrade = plantGrade;
        this.plantCount = plantCount;
        this.bidPrice = bidPrice;
    }

    public BidServiceRequest toServiceRequest() {
        return BidServiceRequest.of(auctionVarietyId, varietyCode, plantGrade, plantCount, bidPrice);
    }
}
