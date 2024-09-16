package com.ssafy.trade_service.api.controller.auctionreservation.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuctionReservationModifyRequest {

    private String plantGrade;
    private Integer plantCount;
    private Integer desiredPrice;

    @Builder
    private AuctionReservationModifyRequest(String plantGrade, Integer plantCount, Integer desiredPrice) {
        this.plantGrade = plantGrade;
        this.plantCount = plantCount;
        this.desiredPrice = desiredPrice;
    }
}
