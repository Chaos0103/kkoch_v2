package com.ssafy.auction_service.api.controller.auctionschedule.request;

import com.ssafy.auction_service.api.service.auctionschedule.request.AuctionScheduleCreateServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.ssafy.auction_service.api.controller.auctionschedule.message.AuctionScheduleBindingMessage.*;

@Getter
@NoArgsConstructor
public class AuctionScheduleCreateRequest {

    @NotBlank(message = NOT_BLANK_PLANT_CATEGORY)
    private String plantCategory;

    @NotBlank(message = NOT_BLANK_JOINT_MARKET)
    private String jointMarket;

    @NotBlank(message = NOT_BLANK_AUCTION_START_DATE_TIME)
    private String auctionStartDateTime;

    private String auctionDescription;

    @Builder
    private AuctionScheduleCreateRequest(String plantCategory, String jointMarket, String auctionStartDateTime, String auctionDescription) {
        this.plantCategory = plantCategory;
        this.jointMarket = jointMarket;
        this.auctionStartDateTime = auctionStartDateTime;
        this.auctionDescription = auctionDescription;
    }

    public AuctionScheduleCreateServiceRequest toServiceRequest() {
        return AuctionScheduleCreateServiceRequest.of(plantCategory, jointMarket, auctionDescription, auctionStartDateTime);
    }
}
