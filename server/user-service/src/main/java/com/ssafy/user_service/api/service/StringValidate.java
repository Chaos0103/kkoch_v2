package com.ssafy.user_service.api.service;

public class StringValidate {

    public static final String KOREAN_REGEX = "^[가-힣]*$";
    public static final String NUMBER_REGEX = "^[0-9]*$";

    private final String value;

    private StringValidate(String value) {
        this.value = value;
    }

    public static StringValidate of(String value) {
        return new StringValidate(value);
    }

    public boolean isBlank() {
        return value == null || value.isBlank();
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

    public boolean isMatches(String regex) {
        return value.matches(regex);
    }

    public boolean isNotMatches(String regex) {
        return !isMatches(regex);
    }

    public boolean isKorean() {
        return value.matches(KOREAN_REGEX);
    }

    public boolean isNotKorean() {
        return !isKorean();
    }

    public boolean isNumber() {
        return value.matches(NUMBER_REGEX);
    }

    public boolean isNotNumber() {
        return !isNumber();
    }
}
