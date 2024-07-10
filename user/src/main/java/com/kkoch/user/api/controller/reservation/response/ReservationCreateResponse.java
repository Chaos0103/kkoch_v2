package com.kkoch.user.api.controller.reservation.response;

import com.kkoch.user.domain.reservation.PlantGrade;
import com.kkoch.user.domain.reservation.Reservation;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationCreateResponse {

    private final long reservationId;
    private final int plantCount;
    private final int desiredPrice;
    private final PlantGrade plantGrade;
    private final LocalDateTime createdDateTime;

    @Builder
    private ReservationCreateResponse(long reservationId, int plantCount, int desiredPrice, PlantGrade plantGrade, LocalDateTime createdDateTime) {
        this.reservationId = reservationId;
        this.plantCount = plantCount;
        this.desiredPrice = desiredPrice;
        this.plantGrade = plantGrade;
        this.createdDateTime = createdDateTime;
    }

    public static ReservationCreateResponse of(Reservation reservation) {
        return ReservationCreateResponse.builder()
            .reservationId(reservation.getId())
            .plantCount(reservation.getPlantCount())
            .desiredPrice(reservation.getDesiredPrice())
            .plantGrade(reservation.getPlantGrade())
            .createdDateTime(reservation.getCreatedDateTime())
            .build();
    }
}
