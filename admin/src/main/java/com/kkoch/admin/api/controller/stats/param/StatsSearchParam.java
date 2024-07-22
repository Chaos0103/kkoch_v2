package com.kkoch.admin.api.controller.stats.param;

import com.kkoch.admin.domain.stats.repository.dto.StatsSearchCond;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
public class StatsSearchParam {

    @NotBlank(message = "품좀코드를 입력해주세요.")
    private String varietyCode;

    @Positive(message = "조회 기간을 올바르게 입력해주세요.")
    private int period = 1;

    @Builder
    private StatsSearchParam(String varietyCode, int period) {
        this.varietyCode = varietyCode;
        this.period = period;
    }

    public StatsSearchCond toCond() {
        return StatsSearchCond.of(varietyCode, period);
    }
}
