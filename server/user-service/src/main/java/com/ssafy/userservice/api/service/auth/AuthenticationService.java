package com.ssafy.userservice.api.service.auth;

import com.ssafy.common.global.exception.AuthenticationException;
import com.ssafy.userservice.api.service.auth.response.BankAccountAuthenticationResponse;
import com.ssafy.userservice.api.service.auth.vo.BankAccountAuthentication;
import com.ssafy.userservice.domain.member.vo.BankAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static com.ssafy.common.global.exception.code.ErrorCode.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthenticationService {

    private final BankClient bankClient;
    private final RedisTemplate<String, BankAccountAuthentication> bankAccountAuthenticationRedisTemplate;

    public BankAccountAuthenticationResponse sendAuthenticationNumberToBankAccount(String memberKey, BankAccount bankAccount, String issuedAuthenticationNumber, LocalDateTime currentDateTime) {
        boolean isSendSuccess = bankClient.sendAuthenticationNumber(bankAccount.getBankCode(), bankAccount.getAccountNumber(), issuedAuthenticationNumber);
        if (!isSendSuccess) {
            log.error("1원 계좌 인증 번호 전송 실패 [bankCode = {}, accountNumber = {}]", bankAccount.getBankCode(), bankAccount.getAccountNumber());
            throw new AuthenticationException(FAIL_SEND_AUTHENTICATION_NUMBER_TO_BANK_ACCOUNT);
        }

        BankAccountAuthentication bankAccountAuthentication = BankAccountAuthentication.of(issuedAuthenticationNumber, bankAccount);
        bankAccountAuthenticationRedisTemplate.opsForValue().set(memberKey, bankAccountAuthentication, 5, TimeUnit.MINUTES);

        log.debug("1원 은행 계좌 인증 번호 전송 [bankCode = {}, accountNumber = {}, issuedAuthenticationNumber = {}]", bankAccount.getBankCode(), bankAccount.getAccountNumber(), issuedAuthenticationNumber);
        log.info("1원 은행 계좌 인증 번호 전송 [bankCode = {}, accountNumber = {}]", bankAccount.getBankCode(), bankAccount.getAccountNumber());
        return BankAccountAuthenticationResponse.of(currentDateTime.plusMinutes(5));
    }

    public BankAccount checkAuthenticationNumberToBankAccount(String memberKey, String authenticationNumber) {
        BankAccountAuthentication bankAccountAuthentication = bankAccountAuthenticationRedisTemplate.opsForValue().get(memberKey);
        if (bankAccountAuthentication == null) {
            log.debug("1원 은행 계좌 인증 번호 만료");
            throw new AuthenticationException(AUTHENTICATION_NUMBER_EXPIRED);
        }

        if (!bankAccountAuthentication.getAuthenticationNumber().equals(authenticationNumber)) {
            log.debug("1원 은행 계좌 인증 번호 불일치 [authenticationNumber = {}]", authenticationNumber);
            throw new AuthenticationException(INVALID_AUTHENTICATION_NUMBER);
        }

        bankAccountAuthenticationRedisTemplate.delete(memberKey);
        BankAccount bankAccount = bankAccountAuthentication.getBankAccount();
        log.info("1원 은행 계좌 인증 성공 [bankCode = {}, accountNumber = {}]", bankAccount.getBankCode(), bankAccount.getAccountNumber());
        return bankAccount;
    }
}
