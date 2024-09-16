package com.ssafy.trade_service.domain.auctionreservation.repository.response;

import com.ssafy.trade_service.domain.auctionreservation.PlantGrade;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuctionReservationResponse {

    private long id;
    private String varietyCode;
    private PlantGrade plantGrade;
    private int plantCount;
    private int desiredPrice;

    @Builder
    private AuctionReservationResponse(long id, String varietyCode, PlantGrade plantGrade, int plantCount, int desiredPrice) {
        this.id = id;
        this.varietyCode = varietyCode;
        this.plantGrade = plantGrade;
        this.plantCount = plantCount;
        this.desiredPrice = desiredPrice;
    }
}
