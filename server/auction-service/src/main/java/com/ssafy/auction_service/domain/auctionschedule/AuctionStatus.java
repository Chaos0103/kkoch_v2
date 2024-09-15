package com.ssafy.auction_service.domain.auctionschedule;

import lombok.Getter;

import java.util.List;

@Getter
public enum AuctionStatus {

    INIT("생성"),
    READY("준비"),
    PROGRESS("진행"),
    COMPLETE("완료");

    private final String text;

    AuctionStatus(String text) {
        this.text = text;
    }

    public static List<AuctionStatus> forDisplay() {
        return List.of(INIT, READY, PROGRESS);
    }
}
