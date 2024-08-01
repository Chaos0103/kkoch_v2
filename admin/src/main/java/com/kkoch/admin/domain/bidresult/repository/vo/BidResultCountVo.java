package com.kkoch.admin.domain.bidresult.repository.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class BidResultCountVo {

    private final long tradeId;
    private final long count;

    @Builder
    private BidResultCountVo(long tradeId, long count) {
        this.tradeId = tradeId;
        this.count = count;
    }
}
