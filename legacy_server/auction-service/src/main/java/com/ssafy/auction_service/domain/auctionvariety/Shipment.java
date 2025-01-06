package com.ssafy.auction_service.domain.auctionvariety;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Shipment {

    @Column(nullable = false, updatable = false, length = 20)
    private final String region;

    @Column(nullable = false, updatable = false, length = 20)
    private final String shipper;

    @Builder
    private Shipment(String region, String shipper) {
        this.region = region;
        this.shipper = shipper;
    }

    public static Shipment of(String region, String shipper) {
        return new Shipment(region, shipper);
    }
}
