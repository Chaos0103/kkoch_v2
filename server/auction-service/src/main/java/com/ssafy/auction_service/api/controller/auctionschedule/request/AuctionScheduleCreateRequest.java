package com.ssafy.auction_service.api.controller.auctionschedule.request;

import com.ssafy.auction_service.api.service.auctionschedule.request.AuctionScheduleCreateServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuctionScheduleCreateRequest {

    @NotBlank(message = "화훼부류를 입력해주세요.")
    private String plantCategory;

    @NotBlank(message = "공판장을 입력해주세요.")
    private String jointMarket;

    private String auctionDescription;

    @NotBlank(message = "경매 시작일시를 입력해주세요.")
    private String auctionStartDateTime;

    @Builder
    private AuctionScheduleCreateRequest(String plantCategory, String jointMarket, String auctionDescription, String auctionStartDateTime) {
        this.plantCategory = plantCategory;
        this.jointMarket = jointMarket;
        this.auctionDescription = auctionDescription;
        this.auctionStartDateTime = auctionStartDateTime;
    }

    public AuctionScheduleCreateServiceRequest toServiceRequest() {
        return AuctionScheduleCreateServiceRequest.of(plantCategory, jointMarket, auctionDescription, auctionStartDateTime);
    }
}
