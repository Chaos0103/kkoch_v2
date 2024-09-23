package com.ssafy.trade_service.api.client.response;

import com.ssafy.trade_service.api.service.order.response.orderdetail.Variety;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuctionVarietyResponse {

    private Long auctionVarietyId;
    private String varietyCode;
    private String plantCategory;
    private String itemName;
    private String varietyName;
    private String plantGrade;
    private int plantCount;
    private String region;
    private String shipper;

    @Builder
    private AuctionVarietyResponse(Long auctionVarietyId, String varietyCode, String plantCategory, String itemName, String varietyName, String plantGrade, int plantCount, String region, String shipper) {
        this.auctionVarietyId = auctionVarietyId;
        this.varietyCode = varietyCode;
        this.plantCategory = plantCategory;
        this.itemName = itemName;
        this.varietyName = varietyName;
        this.plantGrade = plantGrade;
        this.plantCount = plantCount;
        this.region = region;
        this.shipper = shipper;
    }

    public Variety toVariety() {
        return Variety.of(varietyCode, plantCategory, itemName, varietyName, plantGrade, plantCount, region, shipper);
    }
}
