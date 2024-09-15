package com.ssafy.auction_service.domain.auctionschedule;

import com.ssafy.auction_service.domain.variety.PlantCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.ssafy.auction_service.common.util.TimeUtils.comparePastOrPresent;

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

    @Column(nullable = false)
    private final LocalDateTime auctionStartDateTime;

    @Builder
    private AuctionInfo(PlantCategory plantCategory, JointMarket jointMarket, LocalDateTime auctionStartDateTime) {
        this.plantCategory = plantCategory;
        this.jointMarket = jointMarket;
        this.auctionStartDateTime = auctionStartDateTime;
    }

    public static AuctionInfo of(PlantCategory plantCategory, JointMarket jointMarket, LocalDateTime auctionStartDateTime) {
        return new AuctionInfo(plantCategory, jointMarket, auctionStartDateTime);
    }

    public AuctionInfo withAuctionStartDateTime(LocalDateTime auctionStartDateTime) {
        return of(plantCategory, jointMarket, auctionStartDateTime);
    }

    public boolean isAuctionStartDateTimePastOrPresent(LocalDateTime current) {
        return comparePastOrPresent(auctionStartDateTime, current);
    }
}
