package com.kkoch.admin.api.controller.bidresult.request;

import com.kkoch.admin.api.service.bidresult.request.BidResultCreateServiceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BidResultCreateRequest {

    @Positive(message = "경매품종 ID를 올바르게 입력해주세요.")
    private long auctionVarietyId;

    @Positive(message = "낙찰가를 올바르게 입력해주세요.")
    private int bidPrice;

    @NotNull(message = "낙찰일시를 입력해주세요.")
    private LocalDateTime bidDateTime;

    @Builder
    private BidResultCreateRequest(long auctionVarietyId, int bidPrice, LocalDateTime bidDateTime) {
        this.auctionVarietyId = auctionVarietyId;
        this.bidPrice = bidPrice;
        this.bidDateTime = bidDateTime;
    }

    public BidResultCreateServiceRequest toServiceRequest() {
        return BidResultCreateServiceRequest.of(auctionVarietyId, bidPrice, bidDateTime);
    }
}
