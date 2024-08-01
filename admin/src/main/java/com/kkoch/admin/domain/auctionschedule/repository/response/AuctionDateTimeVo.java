package com.kkoch.admin.domain.auctionschedule.repository.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(force = true)
public class AuctionDateTimeVo {

    private final int id;
    private final LocalDateTime auctionDateTime;

    @Builder
    private AuctionDateTimeVo(int id, LocalDateTime auctionDateTime) {
        this.id = id;
        this.auctionDateTime = auctionDateTime;
    }
}
