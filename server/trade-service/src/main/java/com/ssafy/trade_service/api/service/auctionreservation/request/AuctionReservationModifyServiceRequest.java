package com.ssafy.trade_service.api.service.auctionreservation.request;

import com.ssafy.trade_service.domain.auctionreservation.PlantGrade;
import com.ssafy.trade_service.domain.auctionreservation.Price;
import lombok.Builder;

public class AuctionReservationModifyServiceRequest {

    private final PlantGrade plantGrade;
    private final int plantCount;
    private final Price desiredPrice;

    @Builder
    private AuctionReservationModifyServiceRequest(PlantGrade plantGrade, int plantCount, Price desiredPrice) {
        this.plantGrade = plantGrade;
        this.plantCount = plantCount;
        this.desiredPrice = desiredPrice;
    }
}
