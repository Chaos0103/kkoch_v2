package com.ssafy.user_service.api.service.member;

public abstract class Masking {

    public static String maskingEmail(String email) {
        String[] splitEmail = email.split("@");

        String id = splitEmail[0];
        String domain = splitEmail[1];

        String nonMaskingInfo = id.substring(0, 2);
        String masking = "*".repeat(id.length() - 2);

        return nonMaskingInfo + masking + "@" + domain;
    }
}
