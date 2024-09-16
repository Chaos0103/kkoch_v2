package com.ssafy.trade_service.api.service.auctionreservation.request;

import com.ssafy.trade_service.api.service.auctionreservation.PlantCounts;
import com.ssafy.trade_service.domain.auctionreservation.AuctionReservation;
import com.ssafy.trade_service.domain.auctionreservation.PlantGrade;
import com.ssafy.trade_service.domain.auctionreservation.Price;
import lombok.Builder;

import java.util.List;

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

    public PlantCounts getPlantCounts(List<Integer> content, int plantCount) {
        PlantCounts plantCounts = PlantCounts.of(content);
        plantCounts.modifyTheFirstValueFound(this.plantCount, plantCount);
        return plantCounts;
    }

    public void modify(AuctionReservation auctionReservation) {
        auctionReservation.modify(plantGrade, plantCount, desiredPrice);
    }
}
