package com.ssafy.trade_service.domain.auctionstatistics.repository.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class AuctionStatisticsResponse {

    private String varietyCode;
    private String plantGrade;
    private int plantCount;
    private int avg;
    private int max;
    private int min;
    private LocalDate calculatedDate;

    @Builder
    private AuctionStatisticsResponse(String varietyCode, String plantGrade, int plantCount, int avg, int max, int min, LocalDate calculatedDate) {
        this.varietyCode = varietyCode;
        this.plantGrade = plantGrade;
        this.plantCount = plantCount;
        this.avg = avg;
        this.max = max;
        this.min = min;
        this.calculatedDate = calculatedDate;
    }
}
