package com.ssafy.user_service.api.service;

public class StringValidate {

    private final String value;

    private StringValidate(String value) {
        this.value = value;
    }

    public static StringValidate of(String value) {
        return new StringValidate(value);
    }

    public boolean isLengthLessThan(int minLength) {
        return value.length() < minLength;
    }

    public boolean isLengthMoreThan(int maxLength) {
        return value.length() > maxLength;
    }

    public boolean isLengthNotEquals(int length) {
        return isLengthLessThan(length) || isLengthMoreThan(length);
    }

    public boolean doesNotMatches(String regex) {
        return !value.matches(regex);
    }

    public boolean doseNotKorean() {
        return !value.matches("^[가-힣]*$");
    }

    public boolean isNotNumber() {
        return !value.matches("^[0-9]*$");
    }
}
