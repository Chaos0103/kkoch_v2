package com.ssafy.trade_service.api.service.auctionstatistics.response;

import com.ssafy.trade_service.domain.auctionstatistics.AuctionStatistics;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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

    public static AuctionStatisticsCreateResponse of(List<AuctionStatistics> auctionStatistics, LocalDate calculatedDate) {
        return new AuctionStatisticsCreateResponse(auctionStatistics.size(), calculatedDate);
    }
}
