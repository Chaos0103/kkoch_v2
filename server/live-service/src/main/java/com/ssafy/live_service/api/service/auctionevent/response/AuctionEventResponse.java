package com.ssafy.live_service.api.service.auctionevent.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuctionEventResponse {

    private int bidPrice;

    private AuctionEventResponse(int bidPrice) {
        this.bidPrice = bidPrice;
    }
}
