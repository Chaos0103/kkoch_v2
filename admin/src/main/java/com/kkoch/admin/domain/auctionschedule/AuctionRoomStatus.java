package com.kkoch.admin.domain.auctionschedule;

public enum AuctionRoomStatus {

    INIT("생성"),
    READY("준비"),
    OPEN("진행"),
    CLOSE("마감");

    private final String description;

    AuctionRoomStatus(String description) {
        this.description = description;
    }
}
