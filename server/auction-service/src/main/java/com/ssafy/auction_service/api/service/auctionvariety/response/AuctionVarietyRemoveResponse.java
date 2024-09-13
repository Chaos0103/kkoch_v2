package com.ssafy.auction_service.api.service.auctionvariety.response;

import com.ssafy.auction_service.domain.auctionvariety.AuctionVariety;
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

    public static AuctionVarietyRemoveResponse of(AuctionVariety auctionVariety) {
        return new AuctionVarietyRemoveResponse(auctionVariety.getId(), auctionVariety.getListingNumber());
    }
}
