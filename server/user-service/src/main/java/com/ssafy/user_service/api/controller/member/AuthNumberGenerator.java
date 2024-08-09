package com.ssafy.user_service.api.controller.member;

import java.util.Random;

public abstract class AuthNumberGenerator {

    public static String generateAuthNumber(int size) {
        StringBuilder sb = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < size; i++) {
            int num = random.nextInt(10);
            sb.append(num);
        }

        return sb.toString();
    }
}
