package com.ssafy.auction_service.api.controller.auctionschedule.request;

import com.ssafy.auction_service.api.service.auctionschedule.request.AuctionScheduleModifyServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuctionScheduleModifyRequest {

    @NotBlank(message = "경매 시작일시를 입력해주세요.")
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
