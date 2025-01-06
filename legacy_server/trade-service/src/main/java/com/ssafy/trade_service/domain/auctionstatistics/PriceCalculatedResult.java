package com.ssafy.trade_service.domain.auctionstatistics;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class PriceCalculatedResult {

    @Column(nullable = false, updatable = false)
    private final int avg;

    @Column(nullable = false, updatable = false)
    private final int max;

    @Column(nullable = false, updatable = false)
    private final int min;

    @Builder
    private PriceCalculatedResult(int avg, int max, int min) {
        this.avg = avg;
        this.max = max;
        this.min = min;
    }

    public static PriceCalculatedResult of(int avg, int max, int min) {
        return new PriceCalculatedResult(avg, max, min);
    }
}
