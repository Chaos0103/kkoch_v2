package com.ssafy.trade_service.api.service.auctionstatistics.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class AuctionStatisticsCreateResponse {

    private int calculatedVarietyCount;
    private LocalDate calculatedDate;

    @Builder
    private AuctionStatisticsCreateResponse(int calculatedVarietyCount, LocalDate calculatedDate) {
        this.calculatedVarietyCount = calculatedVarietyCount;
        this.calculatedDate = calculatedDate;
    }
}
