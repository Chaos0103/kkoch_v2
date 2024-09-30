package com.ssafy.live_service.api.client.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AuctionStatusModifyResponse {

    private int id;
    private String auctionStatus;
    private LocalDateTime modifiedDateTime;

    @Builder
    private AuctionStatusModifyResponse(int id, String auctionStatus, LocalDateTime modifiedDateTime) {
        this.id = id;
        this.auctionStatus = auctionStatus;
        this.modifiedDateTime = modifiedDateTime;
    }
}
