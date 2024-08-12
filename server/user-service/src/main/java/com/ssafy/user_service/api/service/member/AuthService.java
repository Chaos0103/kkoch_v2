package com.ssafy.user_service.api.service.member;

import com.ssafy.user_service.api.service.member.request.BankAccountServiceRequest;
import com.ssafy.user_service.api.service.member.response.*;
import com.ssafy.user_service.common.exception.AppException;
import com.ssafy.user_service.common.redis.RedisRepository;
import com.ssafy.user_service.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.ssafy.user_service.api.service.member.MemberValidate.*;
import static java.util.concurrent.TimeUnit.MINUTES;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private static final int EMAIL_AUTH_NUMBER_TIMEOUT = 5;
    private static final int ACCOUNT_AUTH_NUMBER_TIMEOUT = 5;

    private final MemberRepository memberRepository;
    private final RedisRepository<String, String> redisRepository;

    public EmailAuthResponse sendAuthNumberToEmail(String email, String authNumber, LocalDateTime currentDateTime) {
        validateEmail(email);

        LocalDateTime expiredDateTime = saveAuthNumber(email, authNumber, currentDateTime);

        log.info("{}로 이메일 인증 번호 전송", email);

        return EmailAuthResponse.of(expiredDateTime);
    }

    public EmailValidateResponse validateAuthNumberToEmail(String email, String authNumber, LocalDateTime currentDateTime) {
        boolean isAvailable = validateAuthNumber(email, authNumber);

        return EmailValidateResponse.of(email, !isAvailable, currentDateTime);
    }

    public TelValidateResponse validateTel(String tel, LocalDateTime currentDateTime) {
        MemberValidate.validateTel(tel);

        boolean isAvailable = memberRepository.existsByTel(tel);

        return TelValidateResponse.of(tel, !isAvailable, currentDateTime);
    }

    public BusinessNumberValidateResponse validateBusinessNumber(String businessNumber, LocalDateTime currentDateTime) {
        MemberValidate.validateBusinessNumber(businessNumber);

        boolean isAvailable = memberRepository.existsByUserAdditionalInfoBusinessNumber(businessNumber);

        return BusinessNumberValidateResponse.of(businessNumber, !isAvailable, currentDateTime);
    }

    public BankAccountAuthResponse sendAuthNumberToBankAccount(BankAccountServiceRequest request, String authNumber, LocalDateTime currentDateTime) {
        validateBankCode(request.getBankCode());
        validateAccountNumber(request.getAccountNumber());

        redisRepository.save(request.getAccountNumber(), authNumber, ACCOUNT_AUTH_NUMBER_TIMEOUT, MINUTES);

        LocalDateTime expiredDateTime = currentDateTime.plusMinutes(ACCOUNT_AUTH_NUMBER_TIMEOUT);

        log.info("{} 계좌로 1원 인증 번호 전송", request.getAccountNumber());

        return BankAccountAuthResponse.of(expiredDateTime);
    }

    public boolean validateAuthNumberToBankAccount(BankAccountServiceRequest request, String authNumber, LocalDateTime currentDateTime) {
        return false;
    }

    private LocalDateTime saveAuthNumber(String email, String authNumber, LocalDateTime currentDateTime) {
        redisRepository.save(email, authNumber, EMAIL_AUTH_NUMBER_TIMEOUT, MINUTES);

        return currentDateTime.plusMinutes(EMAIL_AUTH_NUMBER_TIMEOUT);
    }

    private boolean validateAuthNumber(String email, String authNumber) {
        String issuedAuthNumber = redisRepository.findByKey(email);

        checkExpiredOn(issuedAuthNumber);
        checkEqualTo(issuedAuthNumber, authNumber);

        redisRepository.remove(email);
        return memberRepository.existsByEmail(email);
    }

    private void checkExpiredOn(String authNumber) {
        if (authNumber == null) {
            throw new AppException("인증 번호가 만료되었습니다.");
        }
    }

    private void checkEqualTo(String issuedAuthNumber, String requestedAuthNumber) {
        if (isNotEquals(issuedAuthNumber, requestedAuthNumber)) {
            throw new AppException("인증 번호가 일치하지 않습니다.");
        }
    }

    private static boolean isNotEquals(String str1, String str2) {
        return !str1.equals(str2);
    }
}
