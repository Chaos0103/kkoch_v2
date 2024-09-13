package com.ssafy.auction_service.api.service.auctionvariety.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuctionVarietyRemoveResponse {

    private Long id;
    private String listingNumber;

    @Builder
    private AuctionVarietyRemoveResponse(Long id, String listingNumber) {
        this.id = id;
        this.listingNumber = listingNumber;
    }
}
