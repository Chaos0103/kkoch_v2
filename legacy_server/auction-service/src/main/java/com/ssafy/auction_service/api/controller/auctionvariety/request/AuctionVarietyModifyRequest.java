package com.ssafy.auction_service.api.controller.auctionvariety.request;

import com.ssafy.auction_service.api.service.auctionvariety.request.AuctionVarietyModifyServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.ssafy.auction_service.api.controller.auctionvariety.message.AuctionVarietyBindingMessage.*;

@Getter
@NoArgsConstructor
public class AuctionVarietyModifyRequest {

    @NotBlank(message = NOT_BLANK_PLANT_GRADE)
    private String plantGrade;

    @Positive(message = NOT_BLANK_PLANT_COUNT)
    private Integer plantCount;

    @Positive(message = NOT_BLANK_AUCTION_START_PRICE)
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
