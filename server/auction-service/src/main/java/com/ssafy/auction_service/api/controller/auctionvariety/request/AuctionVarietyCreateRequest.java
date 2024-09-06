package com.ssafy.auction_service.api.controller.auctionvariety.request;

import com.ssafy.auction_service.api.service.auctionvariety.request.AuctionVarietyCreateServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuctionVarietyCreateRequest {

    @NotBlank(message = "품종 코드를 입력해주세요.")
    private String varietyCode;

    @NotNull(message = "경매 일정 ID를 입력해주세요.")
    private Integer auctionScheduleId;

    @NotBlank(message = "화훼등급을 입력해주세요.")
    private String plantGrade;

    @Positive(message = "화훼단수를 올바르게 입력해주세요.")
    private Integer plantCount;

    @Positive(message = "경매 시작가를 올바르게 입력해주세요.")
    private Integer auctionStartPrice;

    @NotBlank(message = "출하 지역을 입력해주세요.")
    private String region;

    @NotBlank(message = "출하자을 입력해주세요.")
    private String shipper;

    @Builder
    private AuctionVarietyCreateRequest(String varietyCode, Integer auctionScheduleId, String plantGrade, Integer plantCount, Integer auctionStartPrice, String region, String shipper) {
        this.varietyCode = varietyCode;
        this.auctionScheduleId = auctionScheduleId;
        this.plantGrade = plantGrade;
        this.plantCount = plantCount;
        this.auctionStartPrice = auctionStartPrice;
        this.region = region;
        this.shipper = shipper;
    }

    public AuctionVarietyCreateServiceRequest toServiceRequest() {
        return AuctionVarietyCreateServiceRequest.of(plantGrade, plantCount, auctionStartPrice, region, shipper);
    }
}
