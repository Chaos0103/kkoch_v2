package com.kkoch.user.api.service.reservation.response;

import com.kkoch.user.client.response.PlantResponse;
import com.kkoch.user.domain.reservation.PlantGrade;
import com.kkoch.user.domain.reservation.Reservation;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationResponse {

    private final long reservationId;
    private final int plantId;
    private final String plantType;
    private final String plantName;
    private final int plantCount;
    private final int desiredPrice;
    private final PlantGrade plantGrade;
    private final LocalDateTime reservedDateTime;

    @Builder
    private ReservationResponse(long reservationId, int plantId, String plantType, String plantName, int plantCount, int desiredPrice, PlantGrade plantGrade, LocalDateTime reservedDateTime) {
        this.reservationId = reservationId;
        this.plantId = plantId;
        this.plantType = plantType;
        this.plantName = plantName;
        this.plantCount = plantCount;
        this.desiredPrice = desiredPrice;
        this.plantGrade = plantGrade;
        this.reservedDateTime = reservedDateTime;
    }

    public static ReservationResponse of(Reservation reservation, PlantResponse plant) {
        return ReservationResponse.builder()
            .reservationId(reservation.getId())
            .plantId(plant.getPlantId())
            .plantType(plant.getPlantType())
            .plantName(plant.getPlantName())
            .plantCount(reservation.getPlantCount())
            .desiredPrice(reservation.getDesiredPrice())
            .plantGrade(reservation.getPlantGrade())
            .reservedDateTime(reservation.getCreatedDateTime())
            .build();
    }
}
