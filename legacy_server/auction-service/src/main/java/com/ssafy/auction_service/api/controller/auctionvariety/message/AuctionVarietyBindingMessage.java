package com.ssafy.auction_service.api.controller.auctionvariety.message;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class AuctionVarietyBindingMessage {

    public static final String NOT_BLANK_VARIETY_CODE = "품종 코드를 입력해주세요.";
    public static final String NOT_BLANK_PLANT_GRADE = "화훼등급을 입력해주세요.";
    public static final String NOT_BLANK_PLANT_COUNT = "화훼단수를 올바르게 입력해주세요.";
    public static final String NOT_BLANK_AUCTION_START_PRICE = "경매 시작가를 올바르게 입력해주세요.";
    public static final String NOT_BLANK_REGION = "출하 지역을 입력해주세요.";
    public static final String NOT_BLANK_SHIPPER = "출하자를 입력해주세요.";
}
