package com.ssafy.auction_service.api.controller.auctionschedule.message;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class AuctionScheduleBindingMessage {

    public static final String NOT_BLANK_PLANT_CATEGORY = "화훼부류를 입력해주세요.";
    public static final String NOT_BLANK_JOINT_MARKET = "공판장을 입력해주세요.";
    public static final String NOT_BLANK_AUCTION_START_DATE_TIME = "경매 시작일시를 입력해주세요.";
}
