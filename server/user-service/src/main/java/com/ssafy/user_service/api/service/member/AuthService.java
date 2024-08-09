package com.ssafy.user_service.api.service.member;

import com.ssafy.user_service.api.service.member.response.EmailAuthResponse;
import com.ssafy.user_service.common.redis.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static com.ssafy.user_service.api.service.member.MemberValidate.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final RedisRepository<String, String> redisRepository;

    public EmailAuthResponse sendAuthNumberToEmail(String email, String authNumber, LocalDateTime currentDateTime) {
        validateEmail(email);

        redisRepository.save(email, authNumber, 5, TimeUnit.MINUTES);

        LocalDateTime expiredDateTime = currentDateTime.plusMinutes(5);

        return EmailAuthResponse.of(expiredDateTime);
    }
}
