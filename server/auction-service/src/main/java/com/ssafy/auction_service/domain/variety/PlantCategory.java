package com.ssafy.auction_service.domain.variety;

import com.ssafy.auction_service.common.exception.NotSupportedException;
import lombok.Getter;

@Getter
public enum PlantCategory {

    CUT_FLOWERS("절화", "1000"),
    ORCHID("난", "2000"),
    FOLIAGE("관엽", "3000"),
    VERNALIZATION("춘화", "4000");

    private static final String NOT_SUPPORTED_VALUE = "지원하지 않는 화훼부류입니다.";
    private static final String CODE_FORMAT = "%s%04d";

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
            throw new NotSupportedException(NOT_SUPPORTED_VALUE, e);
        }
    }

    public String getNextCode(int count) {
        return String.format(CODE_FORMAT, prefix, count + 1);
    }
}
