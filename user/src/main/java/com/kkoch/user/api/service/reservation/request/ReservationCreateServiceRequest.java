package com.kkoch.user.api.service.reservation.request;

import com.kkoch.user.domain.reservation.PlantGrade;
import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.reservation.Reservation;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class ReservationCreateServiceRequest {

    private final String plantType;
    private final String plantName;
    private final int plantCount;
    private final int desiredPrice;
    private final String plantGrade;

    @Builder
    private ReservationCreateServiceRequest(String plantType, String plantName, int plantCount, int desiredPrice, String plantGrade) {
        this.plantType = plantType;
        this.plantName = plantName;
        this.plantCount = plantCount;
        this.desiredPrice = desiredPrice;
        this.plantGrade = plantGrade;
    }

    public static ReservationCreateServiceRequest of(String plantType, String plantName, int plantCount, int desiredPrice, String plantGrade) {
        return new ReservationCreateServiceRequest(plantType, plantName, plantCount, desiredPrice, plantGrade);
    }

    public Reservation toEntity(Member member, Integer plantId) {
        PlantGrade plantGrade = PlantGrade.valueOf(this.plantGrade);
        return Reservation.create(plantCount, desiredPrice, plantGrade, member, plantId);
    }

    public Map<String, String> createPlantMap() {
        return Map.of(
            "type", plantGrade,
            "name", plantName
        );
    }

}

