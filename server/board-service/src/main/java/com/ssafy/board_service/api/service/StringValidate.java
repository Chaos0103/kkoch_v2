package com.ssafy.board_service.api.service;

public class StringValidate {

    private final String value;

    private StringValidate(String value) {
        this.value = value;
    }

    public static StringValidate of(String value) {
        return new StringValidate(value);
    }

    public boolean isLengthMoreThan(int maxLength) {
        return value.length() > maxLength;
    }
}
