package com.ssafy.auction_service.api.controller.auctionvariety.request;

import com.ssafy.auction_service.api.service.auctionvariety.request.AuctionVarietyCreateServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.ssafy.auction_service.api.controller.auctionvariety.message.AuctionVarietyBindingMessage.*;

@Getter
@NoArgsConstructor
public class AuctionVarietyCreateRequest {

    @NotBlank(message = NOT_BLANK_VARIETY_CODE)
    private String varietyCode;

    @NotBlank(message = NOT_BLANK_PLANT_GRADE)
    private String plantGrade;

    @Positive(message = NOT_BLANK_PLANT_COUNT)
    private Integer plantCount;

    @Positive(message = NOT_BLANK_AUCTION_START_PRICE)
    private Integer auctionStartPrice;

    @NotBlank(message = NOT_BLANK_REGION)
    private String region;

    @NotBlank(message = NOT_BLANK_SHIPPER)
    private String shipper;

    @Builder
    private AuctionVarietyCreateRequest(String varietyCode, String plantGrade, Integer plantCount, Integer auctionStartPrice, String region, String shipper) {
        this.varietyCode = varietyCode;
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
