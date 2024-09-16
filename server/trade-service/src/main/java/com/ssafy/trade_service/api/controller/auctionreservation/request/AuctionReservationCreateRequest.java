package com.ssafy.trade_service.api.controller.auctionreservation.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuctionReservationCreateRequest {

    private String varietyCode;
    private String plantGrade;
    private Integer plantCount;
    private Integer desiredPrice;

    @Builder
    private AuctionReservationCreateRequest(String varietyCode, String plantGrade, Integer plantCount, Integer desiredPrice) {
        this.varietyCode = varietyCode;
        this.plantGrade = plantGrade;
        this.plantCount = plantCount;
        this.desiredPrice = desiredPrice;
    }
}
