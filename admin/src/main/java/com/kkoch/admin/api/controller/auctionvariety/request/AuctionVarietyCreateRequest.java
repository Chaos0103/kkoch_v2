package com.kkoch.admin.api.controller.auctionvariety.request;

import com.kkoch.admin.api.service.auctionvariety.request.AuctionVarietyCreateServiceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
public class AuctionVarietyCreateRequest {

    @NotBlank(message = "품종코드를 입력해주세요.")
    private String varietyCode;

    @Positive(message = "경매 일정을 올바르게 입력해주세요.")
    private int auctionScheduleId;

    @NotBlank(message = "품종 등급을 입력해주세요.")
    private String grade;

    @Positive(message = "단수를 올바르게 입력해주세요.")
    private Integer plantCount;

    @Positive(message = "경매 시작가를 올바르게 입력해주세요.")
    private Integer startPrice;

    @NotBlank(message = "지역을 입력해주세요.")
    private String region;

    @NotBlank(message = "출하자를 입력해주세요.")
    private String shipper;

    @Builder
    private AuctionVarietyCreateRequest(String varietyCode, int auctionScheduleId, String grade, int plantCount, int startPrice, String region, String shipper) {
        this.varietyCode = varietyCode;
        this.auctionScheduleId = auctionScheduleId;
        this.grade = grade;
        this.plantCount = plantCount;
        this.startPrice = startPrice;
        this.region = region;
        this.shipper = shipper;
    }

    public AuctionVarietyCreateServiceRequest toServiceRequest() {
        return AuctionVarietyCreateServiceRequest.of(grade, plantCount, startPrice, region, shipper);
    }
}
