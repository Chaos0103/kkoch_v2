package com.ssafy.userservice.api.service.auth;

import com.ssafy.common.global.exception.AuthenticationException;
import com.ssafy.userservice.api.service.auth.response.*;
import com.ssafy.userservice.api.service.auth.vo.BankAccountAuthentication;
import com.ssafy.userservice.domain.member.vo.BankAccount;
import com.ssafy.userservice.domain.member.vo.BusinessNumber;
import com.ssafy.userservice.domain.member.vo.Email;
import com.ssafy.userservice.domain.member.vo.Tel;
import common.IntegrationTestSupport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

class AuthenticationServiceTest extends IntegrationTestSupport {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    RedisTemplate<String, BankAccountAuthentication> bankAccountRedisTemplate;

    @Autowired
    RedisTemplate<String, String> emailRedisTemplate;

    @MockitoBean
    BankClient bankClient;

    @MockitoBean
    EmailClient emailClient;

    @AfterEach
    void tearDown() {
        clearBankAccountRedisTemplate();
        clearEmailRedisTemplate();
    }

    @DisplayName("이메일로 인증 번호 전송 실패 시 예외가 발생한다.")
    @Test
    void sendAuthenticationNumberToEmailWhenFailSend() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2025, 1, 1, 0, 0, 0);
        Email email = Email.of("ssafy@gmail.com");
        String issuedAuthenticationNumber = "123456";

        given(emailClient.sendAuthenticationNumber(anyString(), anyString()))
            .willReturn(false);

        //when
        assertThatThrownBy(() -> authenticationService.sendAuthenticationNumberToEmail(email, issuedAuthenticationNumber, currentDateTime))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("인증 번호 전송을 실패했습니다.");

        //then
        String findAuthenticationNumber = emailRedisTemplate.opsForValue().get(email.getEmail());
        assertThat(findAuthenticationNumber).isNull();
    }

    @DisplayName("이메일 인증을 위해 입력 받은 이메일로 인증 번호를 전송한다.")
    @Test
    void sendAuthenticationNumberToEmail() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2025, 1, 1, 0, 0, 0);
        Email email = Email.of("ssafy@gmail.com");
        String issuedAuthenticationNumber = "123456";

        given(emailClient.sendAuthenticationNumber(anyString(), anyString()))
            .willReturn(true);

        //when
        EmailAuthenticationResponse emailAuthenticationResponse = authenticationService.sendAuthenticationNumberToEmail(email, issuedAuthenticationNumber, currentDateTime);

        //then
        assertThat(emailAuthenticationResponse)
            .isNotNull()
            .satisfies(response ->
                assertThat(response.getExpiredDateTime()).isEqualTo(LocalDateTime.of(2025, 1, 1, 0, 5, 0))
            );

        String findAuthenticationNumber = emailRedisTemplate.opsForValue().get(email.getEmail());
        assertThat(findAuthenticationNumber)
            .isNotNull()
            .isEqualTo(issuedAuthenticationNumber);
    }

    @DisplayName("이메일 인증 번호 확인 시 발급된 인증 번호가 만료되었다면 예외가 발생한다.")
    @Test
    void checkAuthenticationNumberToEmailWhenExpiredAuthenticationNumber() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2025, 1, 1, 0, 0, 0);
        Email email = Email.of("ssafy@gmail.com");
        String authenticationNumber = "123456";

        //when
        assertThatThrownBy(() -> authenticationService.checkAuthenticationNumberToEmail(email, authenticationNumber, currentDateTime))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("인증 번호가 만료되었습니다.");

        //then
        String findAuthenticationNumber = emailRedisTemplate.opsForValue().get(email.getEmail());
        assertThat(findAuthenticationNumber).isNull();
    }

    @DisplayName("이메일 인증 번호 확인 시 인증 번호가 일치하지 않다면 예외가 발생한다.")
    @Test
    void checkAuthenticationNumberToEmailWhenNotMatchAuthenticationNumber() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2025, 1, 1, 0, 0, 0);
        Email email = Email.of("ssafy@gmail.com");
        String authenticationNumber = "123456";

        emailRedisTemplate.opsForValue().set(email.getEmail(), "654321");

        //when
        assertThatThrownBy(() -> authenticationService.checkAuthenticationNumberToEmail(email, authenticationNumber, currentDateTime))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("인증 번호가 일치하지 않습니다.");

        //then
        String findAuthenticationNumber = emailRedisTemplate.opsForValue().get(email.getEmail());
        assertThat(findAuthenticationNumber)
            .isNotNull()
            .isEqualTo("654321");
    }

    @DisplayName("이메일 인증 번호가 일치하면 이메일 중복 여부를 조회하여 반환한다.")
    @Test
    void checkAuthenticationNumberToEmail() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2025, 1, 1, 0, 0, 0);
        Email email = Email.of("ssafy@gmail.com");
        String authenticationNumber = "123456";

        emailRedisTemplate.opsForValue().set(email.getEmail(), authenticationNumber);

        //when
        EmailAuthenticationResultResponse emailAuthenticationResultResponse = authenticationService.checkAuthenticationNumberToEmail(email, authenticationNumber, currentDateTime);

        //then
        assertThat(emailAuthenticationResultResponse)
            .isNotNull()
            .satisfies(response -> {
                assertThat(response.getEmail()).isEqualTo(email.getEmail());
                assertThat(response.getIsAvailable()).isTrue();
                assertThat(response.getValidatedDateTime()).isEqualTo(currentDateTime);
            });

        String findAuthenticationNumber = emailRedisTemplate.opsForValue().get(email.getEmail());
        assertThat(findAuthenticationNumber).isNull();
    }

    @DisplayName("은행 계좌로 인증 번호 전송 실패 시 예외가 발생한다.")
    @Test
    void sendAuthenticationNumberToBankAccountWhenFailSend() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2025, 1, 1, 0, 0, 0);
        String memberKey = "validMemberKey";
        String authenticationNumber = "123";
        BankAccount bankAccount = BankAccount.builder()
            .bankCode("088")
            .accountNumber("123123123456")
            .build();

        given(bankClient.sendAuthenticationNumber(anyString(), anyString(), anyString()))
            .willReturn(false);

        //when
        assertThatThrownBy(() -> authenticationService.sendAuthenticationNumberToBankAccount(memberKey, bankAccount, authenticationNumber, currentDateTime))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("인증 번호 전송을 실패했습니다.");

        //then
        BankAccountAuthentication findBankAccountAuthentication = bankAccountRedisTemplate.opsForValue().get(memberKey);
        assertThat(findBankAccountAuthentication).isNull();
    }

    @DisplayName("은행 계좌 인증을 위해 입력 받은 은행 계좌로 인증 번호를 전송한다.")
    @Test
    void sendAuthenticationNumberToBankAccount() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2025, 1, 1, 0, 0, 0);
        String memberKey = "validMemberKey";
        String authenticationNumber = "123";
        BankAccount bankAccount = BankAccount.builder()
            .bankCode("088")
            .accountNumber("123123123456")
            .build();

        given(bankClient.sendAuthenticationNumber(anyString(), anyString(), anyString()))
            .willReturn(true);

        //when
        BankAccountAuthenticationResponse bankAccountAuthenticationResponse = authenticationService.sendAuthenticationNumberToBankAccount(memberKey, bankAccount, authenticationNumber, currentDateTime);

        //then
        assertThat(bankAccountAuthenticationResponse)
            .isNotNull()
            .satisfies(response ->
                assertThat(response.getExpiredDateTime()).isEqualTo(LocalDateTime.of(2025, 1, 1, 0, 5, 0))
            );

        BankAccountAuthentication findBankAccountAuthentication = bankAccountRedisTemplate.opsForValue().get(memberKey);
        assertThat(findBankAccountAuthentication)
            .isNotNull()
            .satisfies(bankAccountAuthentication -> {
                assertThat(bankAccountAuthentication.getAuthenticationNumber()).isEqualTo(authenticationNumber);
                assertThat(bankAccountAuthentication.getBankCode()).isEqualTo("088");
                assertThat(bankAccountAuthentication.getAccountNumber()).isEqualTo("123123123456");
            });
    }

    @DisplayName("은행 계좌 인증 번호 확인 시 발급된 인증 번호가 만료되었다면 예외가 발생한다.")
    @Test
    void checkAuthenticationNumberToBankAccountWhenExpiredAuthenticationNumber() {
        //given
        String memberKey = "validMemberKey";
        String authenticationNumber = "123";

        //when
        assertThatThrownBy(() -> authenticationService.checkAuthenticationNumberToBankAccount(memberKey, authenticationNumber))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("인증 번호가 만료되었습니다.");

        //then
        BankAccountAuthentication findBankAccountAuthentication = bankAccountRedisTemplate.opsForValue().get(memberKey);
        assertThat(findBankAccountAuthentication).isNull();
    }

    @DisplayName("은행 계좌 인증 번호 확인 시 인증 번호가 일치하지 않다면 예외가 발생한다.")
    @Test
    void checkAuthenticationNumberToBankAccountWhenNotMatchAuthenticationNumber() {
        //given
        String memberKey = "validMemberKey";
        String authenticationNumber = "123";

        BankAccountAuthentication bankAccountAuthentication = BankAccountAuthentication.builder()
            .authenticationNumber("111")
            .bankCode("088")
            .accountNumber("123123123456")
            .build();
        bankAccountRedisTemplate.opsForValue().set(memberKey, bankAccountAuthentication);

        //when
        assertThatThrownBy(() -> authenticationService.checkAuthenticationNumberToBankAccount(memberKey, authenticationNumber))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("인증 번호가 일치하지 않습니다.");

        //then
        BankAccountAuthentication findBankAccountAuthentication = bankAccountRedisTemplate.opsForValue().get(memberKey);
        assertThat(findBankAccountAuthentication)
            .isNotNull()
            .satisfies(data -> {
                assertThat(data.getAuthenticationNumber()).isEqualTo("111");
                assertThat(data.getBankCode()).isEqualTo("088");
                assertThat(data.getAccountNumber()).isEqualTo("123123123456");
            });
    }

    @DisplayName("은행 계좌 인증 번호가 일치하면 인증이 완료된 은행 계좌를 반환한다.")
    @Test
    void checkAuthenticationNumberToBankAccount() {
        //given
        String memberKey = "validMemberKey";
        String authenticationNumber = "123";

        BankAccountAuthentication bankAccountAuthentication = BankAccountAuthentication.builder()
            .authenticationNumber(authenticationNumber)
            .bankCode("088")
            .accountNumber("123123123456")
            .build();
        bankAccountRedisTemplate.opsForValue().set(memberKey, bankAccountAuthentication);

        //when
        BankAccount bankAccount = authenticationService.checkAuthenticationNumberToBankAccount(memberKey, authenticationNumber);

        //then
        assertThat(bankAccount)
            .isNotNull()
            .satisfies(account -> {
                assertThat(bankAccount.getBankCode()).isEqualTo("088");
                assertThat(bankAccount.getAccountNumber()).isEqualTo("123123123456");
            });

        BankAccountAuthentication findBankAccountAuthentication = bankAccountRedisTemplate.opsForValue().get(memberKey);
        assertThat(findBankAccountAuthentication).isNull();
    }

    @DisplayName("연락처 중복 여부를 조회하여 반환한다.")
    @Test
    void checkTel() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2025, 1, 1, 0, 0, 0);
        Tel tel = Tel.of("01012341234");

        //when
        TelAuthenticationResultResponse telAuthenticationResultResponse = authenticationService.checkTel(tel, currentDateTime);

        //then
        assertThat(telAuthenticationResultResponse)
            .isNotNull()
            .satisfies(response -> {
                assertThat(response.getTel()).isEqualTo(tel.getTel());
                assertThat(response.getIsAvailable()).isTrue();
                assertThat(response.getValidatedDateTime()).isEqualTo(currentDateTime);
            });
    }

    @DisplayName("사업자 번호 중복 여부를 조회하여 반환한다.")
    @Test
    void checkBusinessNumber() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2025, 1, 1, 0, 0, 0);
        BusinessNumber businessNumber = BusinessNumber.of("1231212345");

        //when
        BusinessNumberAuthenticationResultResponse businessNumberAuthenticationResultResponse = authenticationService.checkBusinessNumber(businessNumber, currentDateTime);

        //then
        assertThat(businessNumberAuthenticationResultResponse)
            .isNotNull()
            .satisfies(response -> {
                assertThat(response.getBusinessNumber()).isEqualTo(businessNumber.getBusinessNumber());
                assertThat(response.getIsAvailable()).isTrue();
                assertThat(response.getValidatedDateTime()).isEqualTo(currentDateTime);
            });
    }

    private void clearBankAccountRedisTemplate() {
        Set<String> keys = bankAccountRedisTemplate.keys("*");
        if (!CollectionUtils.isEmpty(keys)) {
            bankAccountRedisTemplate.delete(keys);
        }
    }

    private void clearEmailRedisTemplate() {
        Set<String> keys = emailRedisTemplate.keys("*");
        if (!CollectionUtils.isEmpty(keys)) {
            emailRedisTemplate.delete(keys);
        }
    }
}