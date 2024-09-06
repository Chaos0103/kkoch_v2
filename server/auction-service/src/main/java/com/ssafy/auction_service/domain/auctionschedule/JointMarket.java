package com.ssafy.auction_service.domain.auctionschedule;

import com.ssafy.auction_service.common.exception.NotSupportedException;
import lombok.Getter;

@Getter
public enum JointMarket {

    YANGJAE("양재");

    private final String korean;

    JointMarket(String korean) {
        this.korean = korean;
    }

    public static JointMarket of(String str) {
        try {
            return JointMarket.valueOf(str);
        } catch (IllegalArgumentException e) {
            throw new NotSupportedException("지원하지 않는 공판장입니다.", e);
        }
    }

    public static boolean isSupported(String str) {
        JointMarket jointMarket = of(str);
        return jointMarket != null;
    }

    public static boolean isNotSupported(String str) {
        return !isSupported(str);
    }
}
