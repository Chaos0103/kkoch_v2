package com.kkoch.admin.domain.auctionvariety;

import com.kkoch.admin.domain.Grade;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class AuctionVarietyInfo {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false, length = 20)
    private final Grade grade;

    @Column(nullable = false, updatable = false)
    private final int plantCount;

    @Column(nullable = false, updatable = false)
    private final int startPrice;

    @Builder
    private AuctionVarietyInfo(Grade grade, int plantCount, int startPrice) {
        this.grade = grade;
        this.plantCount = plantCount;
        this.startPrice = startPrice;
    }

    public static AuctionVarietyInfo of(Grade grade, int plantCount, int startPrice) {
        return new AuctionVarietyInfo(grade, plantCount, startPrice);
    }
}
