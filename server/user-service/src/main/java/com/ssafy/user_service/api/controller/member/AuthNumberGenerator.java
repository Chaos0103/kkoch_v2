package com.ssafy.user_service.api.controller.member;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AuthNumberGenerator {

    private static final int RANDOM_NUMBER_BOUND = 10;
    private static final Random random = new Random();

    private static final int EMAIL_AUTH_NUMBER_SIZE = 6;
    private static final int BANK_ACCOUNT_AUTH_NUMBER_SIZE = 3;

    public static String generateEmailAuthNumber() {
        return generateAuthNumber(EMAIL_AUTH_NUMBER_SIZE);
    }

    public static String generateBackAccountAuthNumber() {
        return generateAuthNumber(BANK_ACCOUNT_AUTH_NUMBER_SIZE);
    }

    private static String generateAuthNumber(int size) {
        return IntStream.range(0, size)
            .map(index -> random.nextInt(RANDOM_NUMBER_BOUND))
            .mapToObj(String::valueOf)
            .collect(Collectors.joining());
    }
}
