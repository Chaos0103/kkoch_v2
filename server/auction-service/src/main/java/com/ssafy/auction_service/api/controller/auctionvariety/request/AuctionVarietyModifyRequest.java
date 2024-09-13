package com.ssafy.auction_service.api.controller.auctionvariety.request;

import com.ssafy.auction_service.api.service.auctionvariety.request.AuctionVarietyModifyServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuctionVarietyModifyRequest {

    @NotBlank(message = "화훼등급을 입력해주세요.")
    private String plantGrade;

    @Positive(message = "화훼단수를 올바르게 입력해주세요.")
    private Integer plantCount;

    @Positive(message = "경매 시작가를 올바르게 입력해주세요.")
    private Integer auctionStartPrice;

    @Builder
    private AuctionVarietyModifyRequest(String plantGrade, Integer plantCount, Integer auctionStartPrice) {
        this.plantGrade = plantGrade;
        this.plantCount = plantCount;
        this.auctionStartPrice = auctionStartPrice;
    }

    public AuctionVarietyModifyServiceRequest toServiceRequest() {
        return AuctionVarietyModifyServiceRequest.of(plantGrade, plantCount, auctionStartPrice);
    }
}
