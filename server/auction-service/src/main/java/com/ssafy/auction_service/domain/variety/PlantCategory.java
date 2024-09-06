package com.ssafy.auction_service.domain.variety;

import com.ssafy.auction_service.common.exception.NotSupportedException;
import lombok.Getter;

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
        try {
            return PlantCategory.valueOf(str);
        } catch (IllegalArgumentException e) {
            throw new NotSupportedException("지원하지 않는 화훼부류입니다.", e);
        }
    }

    public static boolean isSupported(String str) {
        PlantCategory plantCategory = of(str);
        return plantCategory != null;
    }

    public static boolean isNotSupported(String str) {
        return !isSupported(str);
    }

    public String getNextCode(int count) {
        return String.format("%s%04d", prefix, count + 1);
    }
}
