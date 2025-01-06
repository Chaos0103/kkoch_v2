package com.ssafy.auction_service.api.controller.auctionschedule.request;

import com.ssafy.auction_service.api.service.auctionschedule.request.AuctionScheduleModifyServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.ssafy.auction_service.api.controller.auctionschedule.message.AuctionScheduleBindingMessage.NOT_BLANK_AUCTION_START_DATE_TIME;

@Getter
@NoArgsConstructor
public class AuctionScheduleModifyRequest {

    @NotBlank(message = NOT_BLANK_AUCTION_START_DATE_TIME)
    private String auctionStartDateTime;

    private String auctionDescription;

    @Builder
    private AuctionScheduleModifyRequest(String auctionStartDateTime, String auctionDescription) {
        this.auctionStartDateTime = auctionStartDateTime;
        this.auctionDescription = auctionDescription;
    }

    public AuctionScheduleModifyServiceRequest toServiceRequest() {
        return AuctionScheduleModifyServiceRequest.of(auctionStartDateTime, auctionDescription);
    }
}
