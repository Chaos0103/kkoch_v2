package com.ssafy.trade_service.api.client.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuctionScheduleStatusResponse {

    private Boolean result;

    @Builder
    private AuctionScheduleStatusResponse(Boolean result) {
        this.result = result;
    }
}
