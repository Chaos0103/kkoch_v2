package com.ssafy.userservice.api.service.auth;

import com.ssafy.common.global.exception.AuthenticationException;
import com.ssafy.userservice.api.service.auth.menager.BankAccountAuthenticationManager;
import com.ssafy.userservice.api.service.auth.menager.EmailAuthenticationManager;
import com.ssafy.userservice.api.service.auth.response.*;
import com.ssafy.userservice.api.service.auth.vo.BankAccountAuthentication;
import com.ssafy.userservice.domain.member.repository.MemberRepository;
import com.ssafy.userservice.domain.member.vo.BankAccount;
import com.ssafy.userservice.domain.member.vo.BusinessNumber;
import com.ssafy.userservice.domain.member.vo.Email;
import com.ssafy.userservice.domain.member.vo.Tel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.ssafy.common.global.exception.code.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final BankClient bankClient;
    private final EmailClient emailClient;
    private final EmailAuthenticationManager emailAuthenticationManager;
    private final BankAccountAuthenticationManager bankAccountAuthenticationManager;
    private final MemberRepository memberRepository;

    public EmailAuthenticationResponse sendAuthenticationNumberToEmail(Email email, String issuedAuthenticationNumber, LocalDateTime currentDateTime) {
        boolean isSendSuccess = emailClient.sendAuthenticationNumber(email.getEmail(), issuedAuthenticationNumber);
        if (!isSendSuccess) {
            log.error("이메일 인증 번호 전송 실패 [email = {}]", email);
            throw new AuthenticationException(FAIL_SEND_AUTHENTICATION_NUMBER_TO_EMAIL);
        }

        emailAuthenticationManager.save(email.getEmail(), issuedAuthenticationNumber);

        log.info("이메일 인증 번호 전송 [email = {}]", email);
        log.debug("발급된 인증 번호 [issuedAuthenticationNumber = {}]", issuedAuthenticationNumber);
        return EmailAuthenticationResponse.of(currentDateTime.plusMinutes(5));
    }

    public EmailAuthenticationResultResponse checkAuthenticationNumberToEmail(Email email, String authenticationNumber, LocalDateTime currentDateTime) {
        Optional<String> findAuthenticationNumber = emailAuthenticationManager.findByKey(email.getEmail());
        if (findAuthenticationNumber.isEmpty()) {
            log.debug("이메일 인증 번호 만료");
            throw new AuthenticationException(AUTHENTICATION_NUMBER_EXPIRED);
        }

        String issuedAuthenticationNumber = findAuthenticationNumber.get();
        if (!issuedAuthenticationNumber.equals(authenticationNumber)) {
            log.debug("이메일 인증 번호 불일치 [authenticationNumber = {}]", authenticationNumber);
            throw new AuthenticationException(INVALID_AUTHENTICATION_NUMBER);
        }

        boolean isExist = memberRepository.existsByEmail(email);
        emailAuthenticationManager.delete(email.getEmail());
        log.info("이메일 인증 성공 [email = {}, isAvailable = {}]", email, !isExist);
        return EmailAuthenticationResultResponse.of(email, isExist, currentDateTime);
    }

    public BankAccountAuthenticationResponse sendAuthenticationNumberToBankAccount(String memberKey, BankAccount bankAccount, String issuedAuthenticationNumber, LocalDateTime currentDateTime) {
        boolean isSendSuccess = bankClient.sendAuthenticationNumber(bankAccount.getBankCode(), bankAccount.getAccountNumber(), issuedAuthenticationNumber);
        if (!isSendSuccess) {
            log.error("1원 계좌 인증 번호 전송 실패 [bankCode = {}, accountNumber = {}]", bankAccount.getBankCode(), bankAccount.getAccountNumber());
            throw new AuthenticationException(FAIL_SEND_AUTHENTICATION_NUMBER_TO_BANK_ACCOUNT);
        }

        bankAccountAuthenticationManager.save(memberKey, bankAccount, issuedAuthenticationNumber);

        log.info("1원 은행 계좌 인증 번호 전송 [bankCode = {}, accountNumber = {}]", bankAccount.getBankCode(), bankAccount.getAccountNumber());
        log.debug("발급된 인증 번호 [issuedAuthenticationNumber = {}]", issuedAuthenticationNumber);
        return BankAccountAuthenticationResponse.of(currentDateTime.plusMinutes(5));
    }

    public BankAccount checkAuthenticationNumberToBankAccount(String memberKey, String authenticationNumber) {
        Optional<BankAccountAuthentication> findBankAccountAuthentication = bankAccountAuthenticationManager.findByKey(memberKey);
        if (findBankAccountAuthentication.isEmpty()) {
            log.debug("1원 은행 계좌 인증 번호 만료");
            throw new AuthenticationException(AUTHENTICATION_NUMBER_EXPIRED);
        }

        BankAccountAuthentication bankAccountAuthentication = findBankAccountAuthentication.get();
        if (bankAccountAuthentication.isNotEquals(authenticationNumber)) {
            log.debug("1원 은행 계좌 인증 번호 불일치 [authenticationNumber = {}]", authenticationNumber);
            throw new AuthenticationException(INVALID_AUTHENTICATION_NUMBER);
        }

        bankAccountAuthenticationManager.delete(memberKey);
        BankAccount bankAccount = bankAccountAuthentication.getBankAccount();
        log.info("1원 은행 계좌 인증 성공 [bankCode = {}, accountNumber = {}]", bankAccount.getBankCode(), bankAccount.getAccountNumber());
        return bankAccount;
    }

    public TelAuthenticationResultResponse checkTel(Tel tel, LocalDateTime currentDateTime) {
        boolean isExist = memberRepository.existsByTel(tel);

        return TelAuthenticationResultResponse.of(tel, isExist, currentDateTime);
    }

    public BusinessNumberAuthenticationResultResponse checkBusinessNumber(BusinessNumber businessNumber, LocalDateTime currentDateTime) {
        boolean isExist = memberRepository.existsByUserAdditionalInfoBusinessNumber(businessNumber);

        return BusinessNumberAuthenticationResultResponse.of(businessNumber, isExist, currentDateTime);
    }
}
