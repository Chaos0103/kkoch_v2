package com.ssafy.auction_service.domain.auctionvariety;

import lombok.Getter;

@Getter
public enum PlantGrade {

    SUPER("특급");

    private final String description;

    PlantGrade(String description) {
        this.description = description;
    }
}
