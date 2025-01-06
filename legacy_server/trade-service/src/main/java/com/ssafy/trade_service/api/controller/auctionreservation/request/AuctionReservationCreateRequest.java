package com.ssafy.trade_service.api.controller.auctionreservation.request;

import com.ssafy.trade_service.api.service.auctionreservation.request.AuctionReservationCreateServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuctionReservationCreateRequest {

    @NotBlank(message = "품종코드를 입력해주세요.")
    private String varietyCode;

    @NotBlank(message = "화훼등급을 입력해주세요.")
    private String plantGrade;

    @NotNull(message = "화훼단수를 입력해주세요.")
    @Positive(message = "화훼단수를 올바르게 입력해주세요.")
    private Integer plantCount;

    @NotNull(message = "희망가격을 입력해주세요.")
    @Positive(message = "희망가격을 올바르게 입력해주세요.")
    private Integer desiredPrice;

    @Builder
    private AuctionReservationCreateRequest(String varietyCode, String plantGrade, Integer plantCount, Integer desiredPrice) {
        this.varietyCode = varietyCode;
        this.plantGrade = plantGrade;
        this.plantCount = plantCount;
        this.desiredPrice = desiredPrice;
    }

    public AuctionReservationCreateServiceRequest toServiceRequest() {
        return AuctionReservationCreateServiceRequest.of(varietyCode, plantGrade, plantCount, desiredPrice);
    }
}
