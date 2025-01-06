package com.ssafy.auction_service.api.controller.variety.message;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class VarietyBindingMessage {

    public static final String NOT_BLANK_PLANT_CATEGORY = "화훼부류를 입력해주세요.";
    public static final String NOT_BLANK_ITEM_NAME = "품목명을 입력해주세요.";
    public static final String NOT_BLANK_VARIETY_NAME = "품종명을 입력해주세요.";

}
