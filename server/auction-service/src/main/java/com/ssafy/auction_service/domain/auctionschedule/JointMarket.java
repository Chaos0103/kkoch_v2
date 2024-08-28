package com.ssafy.auction_service.domain.auctionschedule;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum JointMarket {

    YANGJAE("양재");

    private final String korean;

    JointMarket(String korean) {
        this.korean = korean;
    }

    public static JointMarket of(String str) {
        return Arrays.stream(values())
            .filter(s -> s.name().equals(str))
            .findFirst()
            .orElse(null);
    }

    public static boolean isSupported(String str) {
        JointMarket jointMarket = of(str);
        return jointMarket != null;
    }

    public static boolean isNotSupported(String str) {
        return !isSupported(str);
    }
}
