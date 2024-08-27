package com.ssafy.auction_service.domain.variety;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PlantCategory {

    CUT_FLOWERS("절화", "1000"),
    ORCHID("난", "2000"),
    FOLIAGE("관엽", "3000"),
    VERNALIZATION("춘화", "4000");

    private final String description;
    private final String prefix;

    PlantCategory(String description, String prefix) {
        this.description = description;
        this.prefix = prefix;
    }

    public static PlantCategory of(String str) {
        return Arrays.stream(values())
            .filter(s -> s.name().equals(str))
            .findFirst()
            .orElse(null);
    }

    public static boolean isSupported(String str) {
        PlantCategory plantCategory = of(str);
        return plantCategory != null;
    }

    public static boolean isNotSupported(String str) {
        return !isSupported(str);
    }
}
