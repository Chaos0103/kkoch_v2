package com.ssafy.trade_service.domain.auctionreservation;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class ReservationInfo {

    @Column(nullable = false, updatable = false, columnDefinition = "char(8)", length = 8)
    private final String varietyCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 8)
    private final PlantGrade plantGrade;

    @Column(nullable = false)
    private final int plantCount;

    @Column(nullable = false)
    private final Price desiredPrice;

    @Builder
    private ReservationInfo(String varietyCode, PlantGrade plantGrade, int plantCount, Price desiredPrice) {
        this.varietyCode = varietyCode;
        this.plantGrade = plantGrade;
        this.plantCount = plantCount;
        this.desiredPrice = desiredPrice;
    }

    public static ReservationInfo of(String varietyCode, PlantGrade plantGrade, int plantCount, Price desiredPrice) {
        return new ReservationInfo(varietyCode, plantGrade, plantCount, desiredPrice);
    }

    public ReservationInfo withPlantGrade(PlantGrade plantGrade) {
        return of(varietyCode, plantGrade, plantCount, desiredPrice);
    }

    public ReservationInfo withPlantCount(int plantCount) {
        return of(varietyCode, plantGrade, plantCount, desiredPrice);
    }

    public ReservationInfo withDesiredPrice(Price desiredPrice) {
        return of(varietyCode, plantGrade, plantCount, desiredPrice);
    }
}
