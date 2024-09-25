package com.ssafy.trade_service.api.controller.auctionstatistics.param;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class AuctionStatisticsParam {

    private LocalDate from;
    private LocalDate to;
    private String plantGrade;

    @Builder
    private AuctionStatisticsParam(LocalDate from, LocalDate to, String plantGrade) {
        this.from = from;
        this.to = to;
        this.plantGrade = plantGrade;
    }
}
