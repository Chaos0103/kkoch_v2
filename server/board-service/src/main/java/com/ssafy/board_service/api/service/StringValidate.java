package com.ssafy.board_service.api.service;

import static org.springframework.util.StringUtils.hasText;

public class StringValidate {

    public static final String NUMBER_REGEX = "^[0-9]*$";

    private final String value;

    private StringValidate(String value) {
        this.value = value;
    }

    public static StringValidate of(String value) {
        return new StringValidate(value);
    }

    public boolean isBlank() {
        return !hasText(value);
    }

    public boolean isLengthMoreThan(int maxLength) {
        return value.length() > maxLength;
    }

    public boolean isNumber() {
        return value.matches(NUMBER_REGEX);
    }

    public boolean isNotNumber() {
        return !isNumber();
    }
}
