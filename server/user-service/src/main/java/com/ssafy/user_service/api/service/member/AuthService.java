package com.ssafy.user_service.api.service.member;

import com.ssafy.user_service.api.service.member.request.BankAccountServiceRequest;
import com.ssafy.user_service.api.service.member.response.*;
import com.ssafy.user_service.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.ssafy.user_service.api.service.member.MemberValidate.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final AuthNumberManager authNumberManager;

    public EmailAuthResponse sendAuthNumberToEmail(String email, String issuedAuthNumber, LocalDateTime currentDateTime) {
        validateEmail(email);

        LocalDateTime expiredDateTime = authNumberManager.saveEmailAuthNumber(email, issuedAuthNumber, currentDateTime);

        log.info("{}로 이메일 인증 번호 전송", email);

        return EmailAuthResponse.of(expiredDateTime);
    }

    public EmailValidateResponse validateAuthNumberToEmail(String email, String authNumber, LocalDateTime currentDateTime) {
        authNumberManager.checkAuthNumber(email, authNumber);

        boolean isAvailable = isAvailableEmail(email);

        return EmailValidateResponse.of(email, isAvailable, currentDateTime);
    }

    public TelValidateResponse validateTel(String tel, LocalDateTime currentDateTime) {
        MemberValidate.validateTel(tel);

        boolean isAvailable = isAvailableTel(tel);

        return TelValidateResponse.of(tel, isAvailable, currentDateTime);
    }

    public BusinessNumberValidateResponse validateBusinessNumber(String businessNumber, LocalDateTime currentDateTime) {
        MemberValidate.validateBusinessNumber(businessNumber);

        boolean isAvailable = isAvailableBusinessNumber(businessNumber);

        return BusinessNumberValidateResponse.of(businessNumber, isAvailable, currentDateTime);
    }

    public BankAccountAuthResponse sendAuthNumberToBankAccount(BankAccountServiceRequest request, String issuedAuthNumber, LocalDateTime currentDateTime) {
        validateBankCode(request.getBankCode());
        validateAccountNumber(request.getAccountNumber());

        LocalDateTime expiredDateTime = authNumberManager.saveBankAccountAuthNumber(request.getAccountNumber(), issuedAuthNumber, currentDateTime);

        log.info("{} 계좌로 1원 인증 번호 전송", request.getAccountNumber());

        return BankAccountAuthResponse.of(expiredDateTime);
    }

    public boolean validateAuthNumberToBankAccount(BankAccountServiceRequest request, String authNumber) {
        authNumberManager.checkAuthNumber(request.getAccountNumber(), authNumber);

        return true;
    }

    private boolean isAvailableEmail(String email) {
        return !memberRepository.existsByEmail(email);
    }

    private boolean isAvailableTel(String tel) {
        return !memberRepository.existsByTel(tel);
    }

    private boolean isAvailableBusinessNumber(String businessNumber) {
        return !memberRepository.existsByUserAdditionalInfoBusinessNumber(businessNumber);
    }
}
