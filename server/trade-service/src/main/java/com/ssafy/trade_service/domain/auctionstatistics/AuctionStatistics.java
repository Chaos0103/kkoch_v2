package com.ssafy.trade_service.domain.auctionstatistics;

import com.ssafy.trade_service.domain.TimeBaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuctionStatistics extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auction_statistics_id")
    private Long id;

    @Column(nullable = false, updatable = false, columnDefinition = "char(8)", length = 8)
    private String varietyCode;

    @Column(nullable = false, updatable = false, length = 20)
    private String plantGrade;

    @Column(nullable = false, updatable = false)
    private int plantCount;

    @Embedded
    private PriceCalculatedResult calculatedResult;

    @Builder
    private AuctionStatistics(boolean isDeleted, String varietyCode, String plantGrade, int plantCount, PriceCalculatedResult calculatedResult) {
        super(isDeleted);
        this.varietyCode = varietyCode;
        this.plantGrade = plantGrade;
        this.plantCount = plantCount;
        this.calculatedResult = calculatedResult;
    }

    public static AuctionStatistics of(boolean isDeleted, String varietyCode, String plantGrade, int plantCount, PriceCalculatedResult calculatedResult) {
        return new AuctionStatistics(isDeleted, varietyCode, plantGrade, plantCount, calculatedResult);
    }
}
