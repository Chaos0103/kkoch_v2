package com.kkoch.admin.api.service.auctionvariety.request;

import com.kkoch.admin.domain.Grade;
import com.kkoch.admin.domain.admin.Admin;
import com.kkoch.admin.domain.auctionschedule.AuctionSchedule;
import com.kkoch.admin.domain.auctionvariety.AuctionVariety;
import com.kkoch.admin.domain.auctionvariety.AuctionVarietyInfo;
import com.kkoch.admin.domain.auctionvariety.ShippingInfo;
import com.kkoch.admin.domain.variety.Variety;
import lombok.Builder;

public class AuctionVarietyCreateServiceRequest {

    private final String grade;
    private final int plantCount;
    private final int startPrice;
    private final String region;
    private final String shipper;

    @Builder
    private AuctionVarietyCreateServiceRequest(String grade, int plantCount, int startPrice, String region, String shipper) {
        this.grade = grade;
        this.plantCount = plantCount;
        this.startPrice = startPrice;
        this.region = region;
        this.shipper = shipper;
    }

    public static AuctionVarietyCreateServiceRequest of(String grade, int plantCount, int startPrice, String region, String shipper) {
        return new AuctionVarietyCreateServiceRequest(grade, plantCount, startPrice, region, shipper);
    }

    public AuctionVariety toEntity(String auctionNumber, Admin admin, AuctionSchedule auctionSchedule, Variety variety) {
        AuctionVarietyInfo auctionVarietyInfo = AuctionVarietyInfo.of(Grade.valueOf(grade), plantCount, startPrice);
        ShippingInfo shippingInfo = ShippingInfo.of(region, shipper);
        return AuctionVariety.create(auctionNumber, auctionVarietyInfo, shippingInfo, variety, auctionSchedule, admin);
    }
}
