package com.kkoch.admin.domain.variety;

import lombok.Getter;

@Getter
public enum PlantCategory {

    CUT_FLOWERS("절화", "1000"),
    ORCHID("난", "2000"),
    FOLIAGE("관엽", "3000"),
    VERNALIZATION("춘화", "4000");

    private final String description;
    private final String prefixCode;

    PlantCategory(String description, String prefixCode) {
        this.description = description;
        this.prefixCode = prefixCode;
    }
}
