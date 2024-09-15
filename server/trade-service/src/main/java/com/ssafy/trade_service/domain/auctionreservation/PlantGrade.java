package com.ssafy.trade_service.domain.auctionreservation;

import com.ssafy.trade_service.common.exception.NotSupportedException;
import lombok.Getter;

@Getter
public enum PlantGrade {

    SUPER("특급"),
    ADVANCED("상급"),
    NORMAL("보통");

    private final String text;

    PlantGrade(String text) {
        this.text = text;
    }

    public static PlantGrade of(String str) {
        try {
            return PlantGrade.valueOf(str);
        } catch (IllegalArgumentException e) {
            throw new NotSupportedException("지원하지 않는 화훼등급입니다.", e);
        }
    }
}
