package com.ssafy.trade_service.api.controller.auctionstatistics.param;

import com.ssafy.trade_service.domain.auctionstatistics.repository.cond.AuctionStatisticsSearchCond;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class AuctionStatisticsParam {

    @NotNull(message = "조회 시작일을 입력해주세요.")
    private LocalDate from;

    @NotNull(message = "조회 종료일을 입력해주세요.")
    private LocalDate to;

    private String plantGrade;

    @Builder
    private AuctionStatisticsParam(LocalDate from, LocalDate to, String plantGrade) {
        this.from = from;
        this.to = to;
        this.plantGrade = plantGrade;
    }

    public AuctionStatisticsSearchCond toCond() {
        return AuctionStatisticsSearchCond.of(from, to, plantGrade);
    }
}
