package com.ssafy.auction_service.api.controller.auctionvariety.param;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.ssafy.auction_service.common.util.PageUtils.PARAM_DEFAULT_PAGE_SIZE;

@Getter
@Setter
@NoArgsConstructor
public class AuctionVarietySearchParam {

    private String page = PARAM_DEFAULT_PAGE_SIZE;

    @Builder
    private AuctionVarietySearchParam(String page) {
        this.page = page;
    }
}
