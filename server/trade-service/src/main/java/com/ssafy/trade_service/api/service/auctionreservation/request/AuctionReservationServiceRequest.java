package com.ssafy.trade_service.api.service.auctionreservation.request;

import com.ssafy.trade_service.api.service.auctionreservation.PlantCounts;
import com.ssafy.trade_service.domain.auctionreservation.AuctionReservation;
import com.ssafy.trade_service.domain.auctionreservation.PlantGrade;
import com.ssafy.trade_service.domain.auctionreservation.Price;
import com.ssafy.trade_service.domain.auctionreservation.ReservationInfo;
import lombok.Builder;

import java.util.List;

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

    public AuctionReservation toEntity(Long memberId, Integer auctionScheduleId) {
        ReservationInfo reservationInfo = ReservationInfo.of(varietyCode, plantGrade, plantCount, desiredPrice);
        return AuctionReservation.create(memberId, auctionScheduleId, reservationInfo);
    }

    public PlantCounts getPlantCounts(List<Integer> content) {
        content.add(plantCount);
        return PlantCounts.of(content);
    }
}
