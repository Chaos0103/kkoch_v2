package com.ssafy.user_service.api.service.member.vo;

public class IssuedAuthNumber {

    private final String authNumber;

    private IssuedAuthNumber(String authNumber) {
        this.authNumber = authNumber;
    }

    public static IssuedAuthNumber of(String issuedAuthNumber) {
        return new IssuedAuthNumber(issuedAuthNumber);
    }

    public boolean isExpired() {
        return authNumber == null;
    }

    public boolean isEqualsTo(String authNumber) {
        return this.authNumber.equals(authNumber);
    }

    public boolean isNotEqualsTo(String authNumber) {
        return !isEqualsTo(authNumber);
    }
}
