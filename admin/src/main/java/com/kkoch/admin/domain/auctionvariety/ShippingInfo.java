package com.kkoch.admin.domain.auctionvariety;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class ShippingInfo {

    @Column(nullable = false, updatable = false, length = 20)
    private final String region;

    @Column(nullable = false, updatable = false, length = 20)
    private final String shipper;

    @Builder
    private ShippingInfo(String region, String shipper) {
        this.region = region;
        this.shipper = shipper;
    }

    public static ShippingInfo of(String region, String shipper) {
        return new ShippingInfo(region, shipper);
    }
}
