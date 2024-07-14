package com.kkoch.admin.domain.auctionschedule;

import lombok.Getter;

@Getter
public enum PlantCode {

    CUT_FLOWERS("절화"),
    ORCHID("난"),
    FOLIAGE("관엽"),
    VERNALIZATION("춘화");

    private final String description;

    PlantCode(String description) {
        this.description = description;
    }
}
