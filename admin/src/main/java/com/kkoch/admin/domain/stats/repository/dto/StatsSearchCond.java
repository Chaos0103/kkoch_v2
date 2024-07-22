package com.kkoch.admin.domain.stats.repository.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StatsSearchCond {

    private final String varietyCode;
    private final int period;

    @Builder
    private StatsSearchCond(String varietyCode, int period) {
        this.varietyCode = varietyCode;
        this.period = period;
    }

    public static StatsSearchCond of(String varietyCoded, int period) {
        return new StatsSearchCond(varietyCoded, period);
    }
}
