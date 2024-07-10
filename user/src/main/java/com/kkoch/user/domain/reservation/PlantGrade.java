package com.kkoch.user.domain.reservation;

public enum PlantGrade {

    SUPER("특급"),
    ADVANCED("상급"),
    NORMAL("보통");

    private final String description;

    PlantGrade(String description) {
        this.description = description;
    }
}
