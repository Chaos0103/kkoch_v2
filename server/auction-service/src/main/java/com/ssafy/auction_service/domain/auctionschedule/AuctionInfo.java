package com.ssafy.auction_service.domain.auctionschedule;

import com.ssafy.auction_service.domain.variety.PlantCategory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class AuctionInfo {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false, length = 13)
    private final PlantCategory plantCategory;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false, length = 10)
    private final JointMarket jointMarket;

    @Lob
    @Column(columnDefinition = "text")
    private final String auctionDescription;

    @Builder
    private AuctionInfo(PlantCategory plantCategory, JointMarket jointMarket, String auctionDescription) {
        this.plantCategory = plantCategory;
        this.jointMarket = jointMarket;
        this.auctionDescription = auctionDescription;
    }

    public static AuctionInfo of(PlantCategory plantCategory, JointMarket jointMarket, String auctionDescription) {
        return new AuctionInfo(plantCategory, jointMarket, auctionDescription);
    }
}
