package com.ssafy.trade_service.domain.auctionstatistics.repository.cond;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AuctionStatisticsSearchCond {

    private final LocalDate from;
    private final LocalDate to;
    private final String plantGrade;

    @Builder
    private AuctionStatisticsSearchCond(LocalDate from, LocalDate to, String plantGrade) {
        this.from = from;
        this.to = to;
        this.plantGrade = plantGrade;
    }

    public static AuctionStatisticsSearchCond of(LocalDate from, LocalDate to, String plantGrade) {
        return new AuctionStatisticsSearchCond(from, to, plantGrade);
    }
}
