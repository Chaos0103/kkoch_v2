package com.ssafy.live_service.api.service.live;

import lombok.Getter;

@Getter
public enum Command {

    OPEN(""),
    START(""),
    NEXT(""),
    CLOSE("");

    private final String text;

    Command(String text) {
        this.text = text;
    }

    public boolean isOpen() {
        return this == OPEN;
    }

    public boolean isStart() {
        return this == START;
    }

    public boolean isNext() {
        return this == NEXT;
    }

    public boolean isClose() {
        return this == CLOSE;
    }
}
