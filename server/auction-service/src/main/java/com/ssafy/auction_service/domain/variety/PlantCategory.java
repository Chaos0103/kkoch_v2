package com.ssafy.auction_service.domain.variety;

public enum PlantCategory {

    CUT_FLOWERS("절화"),
    ORCHID("난"),
    FOLIAGE("관엽"),
    VERNALIZATION("춘화");

    private final String description;

    PlantCategory(String description) {
        this.description = description;
    }
}
