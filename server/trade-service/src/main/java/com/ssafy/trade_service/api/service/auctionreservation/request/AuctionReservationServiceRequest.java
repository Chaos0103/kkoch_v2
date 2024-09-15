package com.ssafy.trade_service.api.service.auctionreservation.request;

import com.ssafy.trade_service.domain.auctionreservation.PlantGrade;
import com.ssafy.trade_service.domain.auctionreservation.Price;
import lombok.Builder;

public class AuctionReservationServiceRequest {

    private final String varietyCode;
    private final PlantGrade plantGrade;
    private final int plantCount;
    private final Price desiredPrice;

    @Builder
    private AuctionReservationServiceRequest(String varietyCode, PlantGrade plantGrade, int plantCount, Price desiredPrice) {
        this.varietyCode = varietyCode;
        this.plantGrade = plantGrade;
        this.plantCount = plantCount;
        this.desiredPrice = desiredPrice;
    }
}
