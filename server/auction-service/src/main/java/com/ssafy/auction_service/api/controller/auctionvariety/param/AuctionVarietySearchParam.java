package com.ssafy.auction_service.api.controller.auctionvariety.param;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuctionVarietySearchParam {

    private String page;

    @Builder
    private AuctionVarietySearchParam(String page) {
        this.page = page;
    }
}
