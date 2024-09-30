package com.ssafy.live_service.api.client.response;

import com.ssafy.live_service.api.service.auction.vo.AuctionVariety;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuctionVarietiesResponse {

    private long id;
    private String code;
    private String plantCategory;
    private String itemName;
    private String varietyName;

    private String listingNumber;
    private String plantGrade;
    private int plantCount;
    private int auctionStartPrice;
    private String region;
    private String shipper;

    @Builder
    private AuctionVarietiesResponse(long id, String code, String plantCategory, String itemName, String varietyName, String listingNumber, String plantGrade, int plantCount, int auctionStartPrice, String region, String shipper) {
        this.id = id;
        this.code = code;
        this.plantCategory = plantCategory;
        this.itemName = itemName;
        this.varietyName = varietyName;
        this.listingNumber = listingNumber;
        this.plantGrade = plantGrade;
        this.plantCount = plantCount;
        this.auctionStartPrice = auctionStartPrice;
        this.region = region;
        this.shipper = shipper;
    }

    public AuctionVariety toVo() {
        return AuctionVariety.of(id, code, plantCategory, itemName, varietyName, listingNumber, plantGrade, plantCount, auctionStartPrice, region, shipper);
    }
}
