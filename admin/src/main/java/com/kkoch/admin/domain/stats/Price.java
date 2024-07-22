package com.kkoch.admin.domain.stats;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Price {

    @Column(nullable = false, updatable = false)
    private final int avg;

    @Column(nullable = false, updatable = false)
    private final int max;

    @Column(nullable = false, updatable = false)
    private final int min;

    @Builder
    private Price(int avg, int max, int min) {
        this.avg = avg;
        this.max = max;
        this.min = min;
    }

    public static Price of(int avg, int max, int min) {
        return new Price(avg, max, min);
    }
}
