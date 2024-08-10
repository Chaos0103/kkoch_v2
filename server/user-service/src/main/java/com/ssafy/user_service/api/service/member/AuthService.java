package com.ssafy.user_service.api.service.member;

import com.ssafy.user_service.api.service.member.response.EmailAuthResponse;
import com.ssafy.user_service.api.service.member.response.EmailValidateResponse;
import com.ssafy.user_service.common.exception.AppException;
import com.ssafy.user_service.common.redis.RedisRepository;
import com.ssafy.user_service.domain.member.repository.MemberRepository;
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

    private final MemberRepository memberRepository;
    private final RedisRepository<String, String> redisRepository;

    public EmailAuthResponse sendAuthNumberToEmail(String email, String authNumber, LocalDateTime currentDateTime) {
        validateEmail(email);

        redisRepository.save(email, authNumber, 5, TimeUnit.MINUTES);

        LocalDateTime expiredDateTime = currentDateTime.plusMinutes(5);

        return EmailAuthResponse.of(expiredDateTime);
    }

    public EmailValidateResponse validateAuthNumberToEmail(String email, String authNumber, LocalDateTime currentDateTime) {
        String findAuthNumber = redisRepository.findByKey(email);

        if (findAuthNumber == null) {
            throw new AppException("인증 번호가 만료되었습니다.");
        }

        if (!findAuthNumber.equals(authNumber)) {
            throw new AppException("인증 번호가 일치하지 않습니다.");
        }

        redisRepository.remove(email);
        boolean isAvailable = memberRepository.existsByEmail(email);

        return EmailValidateResponse.of(email, !isAvailable, currentDateTime);
    }
}
