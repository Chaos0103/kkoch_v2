package com.kkoch.user.api.controller.reservation.request;

import com.kkoch.user.api.service.reservation.dto.ReservationCreateServiceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
public class ReservationCreateRequest {

    @NotBlank(message = "품목명을 입력해주세요.")
    private String plantType;

    @NotBlank(message = "품종명을 입력해주세요.")
    private String plantName;

    @Positive(message = "식물 단수를 올바르게 입력해주세요.")
    private int plantCount;

    @Positive(message = "거래 희망 가격을 올바르게 입력해주세요.")
    private int desiredPrice;

    @NotBlank(message = "식물 등급을 입력해주세요.")
    private String plantGrade;

    @Builder
    private ReservationCreateRequest(String plantType, String plantName, int plantCount, int desiredPrice, String plantGrade) {
        this.plantType = plantType;
        this.plantName = plantName;
        this.plantCount = plantCount;
        this.desiredPrice = desiredPrice;
        this.plantGrade = plantGrade;
    }

    public ReservationCreateServiceRequest toServiceRequest() {
        return ReservationCreateServiceRequest.of(plantType, plantName, plantCount, desiredPrice, plantGrade);
    }
}
