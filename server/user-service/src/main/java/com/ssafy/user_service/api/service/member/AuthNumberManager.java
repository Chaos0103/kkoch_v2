package com.ssafy.user_service.api.service.member;

import com.ssafy.user_service.common.exception.AppException;
import com.ssafy.user_service.common.redis.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static java.util.concurrent.TimeUnit.MINUTES;

@Component
@RequiredArgsConstructor
public class AuthNumberManager {

    private static final int EMAIL_AUTH_NUMBER_TIMEOUT = 5;
    private static final int BANK_ACCOUNT_AUTH_NUMBER_TIMEOUT = 5;

    private final RedisRepository<String, String> redisRepository;

    public LocalDateTime saveEmailAuthNumber(String email, String issuedAuthNumber, LocalDateTime currentDateTime) {
        redisRepository.save(email, issuedAuthNumber, EMAIL_AUTH_NUMBER_TIMEOUT, MINUTES);
        return currentDateTime.plusMinutes(EMAIL_AUTH_NUMBER_TIMEOUT);
    }

    public LocalDateTime saveBankAccountAuthNumber(String accountNumber, String issuedAuthNumber, LocalDateTime currentDateTime) {
        redisRepository.save(accountNumber, issuedAuthNumber, BANK_ACCOUNT_AUTH_NUMBER_TIMEOUT, MINUTES);
        return currentDateTime.plusMinutes(BANK_ACCOUNT_AUTH_NUMBER_TIMEOUT);
    }

    public void checkAuthNumber(String key, String authNumber) {
        IssuedAuthNumber issuedAuthNumber = findAuthNumberByKey(key);

        if (issuedAuthNumber.isExpired()) {
            throw new AppException("인증 번호가 만료되었습니다.");
        }

        if (issuedAuthNumber.isNotEqualsTo(authNumber)) {
            throw new AppException("인증 번호가 일치하지 않습니다.");
        }

        redisRepository.remove(key);
    }

    private IssuedAuthNumber findAuthNumberByKey(String key) {
        String issuedAuthNumber = redisRepository.findByKey(key);
        return IssuedAuthNumber.of(issuedAuthNumber);
    }
}