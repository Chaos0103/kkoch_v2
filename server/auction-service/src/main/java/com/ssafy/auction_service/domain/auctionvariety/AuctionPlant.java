package com.ssafy.auction_service.domain.auctionvariety;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class AuctionPlant {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private final PlantGrade plantGrade;

    @Column(nullable = false)
    private final int plantCount;

    @Column(nullable = false)
    private final Price auctionStartPrice;

    @Builder
    private AuctionPlant(PlantGrade plantGrade, int plantCount, Price auctionStartPrice) {
        this.plantGrade = plantGrade;
        this.plantCount = plantCount;
        this.auctionStartPrice = auctionStartPrice;
    }

    public static AuctionPlant of(PlantGrade plantGrade, int plantCount, Price auctionStartPrice) {
        return new AuctionPlant(plantGrade, plantCount, auctionStartPrice);
    }
}
