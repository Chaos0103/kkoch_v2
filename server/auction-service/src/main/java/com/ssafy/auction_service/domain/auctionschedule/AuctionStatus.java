package com.ssafy.auction_service.domain.auctionschedule;

public enum AuctionStatus {

    INIT("생성"),
    READY("준비"),
    PROGRESS("진행"),
    COMPLETE("완료");

    private final String description;

    AuctionStatus(String description) {
        this.description = description;
    }
}
