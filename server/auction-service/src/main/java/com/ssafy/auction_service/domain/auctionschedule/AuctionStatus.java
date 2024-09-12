package com.ssafy.auction_service.domain.auctionschedule;

import java.util.List;

public enum AuctionStatus {

    INIT("생성"),
    READY("준비"),
    PROGRESS("진행"),
    COMPLETE("완료");

    private final String description;

    AuctionStatus(String description) {
        this.description = description;
    }

    public static List<AuctionStatus> getSearchableStatus() {
        return List.of(INIT, READY, PROGRESS);
    }
}
