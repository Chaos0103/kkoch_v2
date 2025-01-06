package com.ssafy.trade_service.api.service.auctionreservation.response;

import com.ssafy.trade_service.domain.auctionreservation.AuctionReservation;
import com.ssafy.trade_service.domain.auctionreservation.PlantGrade;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuctionReservationRemoveResponse {

    private long id;
    private PlantGrade plantGrade;
    private int plantCount;
    private int desiredPrice;

    @Builder
    private AuctionReservationRemoveResponse(long id, PlantGrade plantGrade, int plantCount, int desiredPrice) {
        this.id = id;
        this.plantGrade = plantGrade;
        this.plantCount = plantCount;
        this.desiredPrice = desiredPrice;
    }

    public static AuctionReservationRemoveResponse of(AuctionReservation auctionReservation) {
        return new AuctionReservationRemoveResponse(
            auctionReservation.getId(),
            auctionReservation.getReservationInfo().getPlantGrade(),
            auctionReservation.getReservationInfo().getPlantCount(),
            auctionReservation.getReservationInfo().getDesiredPrice().getValue()
        );
    }
}
