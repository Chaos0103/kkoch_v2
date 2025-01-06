package com.ssafy.trade_service.api.service.auctionreservation.response;

import com.ssafy.trade_service.domain.auctionreservation.AuctionReservation;
import com.ssafy.trade_service.domain.auctionreservation.PlantGrade;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuctionReservationModifyResponse {

    private long id;
    private PlantGrade plantGrade;
    private int plantCount;
    private int desiredPrice;

    @Builder
    private AuctionReservationModifyResponse(long id, PlantGrade plantGrade, int plantCount, int desiredPrice) {
        this.id = id;
        this.plantGrade = plantGrade;
        this.plantCount = plantCount;
        this.desiredPrice = desiredPrice;
    }

    public static AuctionReservationModifyResponse of(AuctionReservation auctionReservation) {
        return new AuctionReservationModifyResponse(
            auctionReservation.getId(),
            auctionReservation.getReservationInfo().getPlantGrade(),
            auctionReservation.getReservationInfo().getPlantCount(),
            auctionReservation.getReservationInfo().getDesiredPrice().getValue()
        );
    }
}
