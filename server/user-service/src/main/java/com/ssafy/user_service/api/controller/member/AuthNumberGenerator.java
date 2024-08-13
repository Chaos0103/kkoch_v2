package com.ssafy.user_service.api.controller.member;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AuthNumberGenerator {

    private static final int RANDOM_NUMBER_BOUND = 10;
    private static final Random random = new Random();

    public static String generateAuthNumber(int size) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < size; i++) {
            int num = random.nextInt(RANDOM_NUMBER_BOUND);
            sb.append(num);
        }

        return sb.toString();
    }
}
