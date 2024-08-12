package com.ssafy.user_service.api.service.member;

import com.ssafy.user_service.IntegrationTestSupport;
import com.ssafy.user_service.api.service.member.request.BankAccountServiceRequest;
import com.ssafy.user_service.api.service.member.response.*;
import com.ssafy.user_service.common.exception.AppException;
import com.ssafy.user_service.common.redis.RedisRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AuthServiceTest extends IntegrationTestSupport {

    @Autowired
    private AuthService authService;

    @Autowired
    private RedisRepository<String, String> redisRepository;

    @AfterEach
    void tearDown() {
        redisRepository.remove("ssafy@ssafy.com");
        redisRepository.remove("123123123456");
    }

    @DisplayName("이메일로 5분간 유효한 랜덤으로 생성된 6자리 숫자로 만들어진 인증 번호를 발송한다.")
    @Test
    void sendAuthNumberToEmail() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0);

        //when
        EmailAuthResponse response = authService.sendAuthNumberToEmail("ssafy@ssafy.com", "123456", currentDateTime);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("expiredDateTime", LocalDateTime.of(2024, 1, 1, 0, 5, 0));

        String authNumber = redisRepository.findByKey("ssafy@ssafy.com");
        assertThat(authNumber).isEqualTo("123456");
    }

    @DisplayName("인증 번호 유효성 검사시 입력 받은 이메일로 발급된 인증 번호가 존재하지 않으면 예외가 발생한다.")
    @Test
    void validateAuthNumberToEmailWithoutAuthNumber() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        String email = "ssafy@ssafy.com";
        String authNumber = "123456";

        //when //then
        assertThatThrownBy(() -> authService.validateAuthNumberToEmail(email, authNumber, currentDateTime))
            .isInstanceOf(AppException.class)
            .hasMessage("인증 번호가 만료되었습니다.");
    }

    @DisplayName("인증 번호 유효성 검사시 입력 받은 인증 번호와 서버에 저장된 인증 번호가 일치하지 않으면 예외가 발생한다.")
    @Test
    void validateAuthNumberToEmailNotEqualsAuthNumber() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        String email = "ssafy@ssafy.com";
        String authNumber = "123456";

        redisRepository.save(email, authNumber);

        //when
        assertThatThrownBy(() -> authService.validateAuthNumberToEmail(email, "654321", currentDateTime))
            .isInstanceOf(AppException.class)
            .hasMessage("인증 번호가 일치하지 않습니다.");

        //then
        String findAuthNumber = redisRepository.findByKey(email);
        assertThat(findAuthNumber).isEqualTo(authNumber);
    }

    @DisplayName("이메일로 발송한 인증 번호의 유효성을 검사하고, 이메일 중복 여부를 확인한다.")
    @Test
    void validateAuthNumberToEmail() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        String email = "ssafy@ssafy.com";
        String authNumber = "123456";

        redisRepository.save(email, authNumber);

        //when
        EmailValidateResponse response = authService.validateAuthNumberToEmail(email, authNumber, currentDateTime);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("email", email)
            .hasFieldOrPropertyWithValue("isAvailable", true)
            .hasFieldOrPropertyWithValue("validatedDateTime", currentDateTime);
    }

    @DisplayName("연락처 사용 가능 여부를 확인한다.")
    @Test
    void validateTel() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        String tel = "01012341234";

        //when
        TelValidateResponse response = authService.validateTel(tel, currentDateTime);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("tel", tel)
            .hasFieldOrPropertyWithValue("isAvailable", true)
            .hasFieldOrPropertyWithValue("validatedDateTime", currentDateTime);
    }

    @DisplayName("사업자 번호 사용 가능 여부를 확인한다.")
    @Test
    void validateBusinessNumber() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        String businessNumber = "1231212345";

        //when
        BusinessNumberValidateResponse response = authService.validateBusinessNumber(businessNumber, currentDateTime);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("businessNumber", businessNumber)
            .hasFieldOrPropertyWithValue("isAvailable", true)
            .hasFieldOrPropertyWithValue("validatedDateTime", currentDateTime);
    }

    @DisplayName("은행 계좌로 5분간 유효한 랜덤으로 생성된 3자리 숫자로 만들어진 인증 번호를 발송한다.")
    @Test
    void sendAuthNumberToBankAccount() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0);

        BankAccountServiceRequest request = BankAccountServiceRequest.builder()
            .bankCode("088")
            .accountNumber("123123123456")
            .build();

        //when
        BankAccountAuthResponse response = authService.sendAuthNumberToBankAccount(request, "012", currentDateTime);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("expiredDateTime", LocalDateTime.of(2024, 1, 1, 0, 5, 0));

        String authNumber = redisRepository.findByKey("123123123456");
        assertThat(authNumber).isEqualTo("012");
    }

    @DisplayName("인증 번호 유효성 검사시 입력 받은 은행 계좌로 발급된 인증 번호가 존재하지 않으면 예외가 발생한다.")
    @Test
    void validateAuthNumberToBankAccountWithoutAuthNumber() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0);

        BankAccountServiceRequest request = BankAccountServiceRequest.builder()
            .bankCode("088")
            .accountNumber("123123123456")
            .build();

        //when //then
        assertThatThrownBy(() -> authService.validateAuthNumberToBankAccount(request, "012"))
            .isInstanceOf(AppException.class)
            .hasMessage("인증 번호가 만료되었습니다.");
    }

    @DisplayName("인증 번호 유효성 검사시 입력 받은 인증 번호와 서버에 저장된 인증 번호가 일치하지 않으면 예외가 발생한다.")
    @Test
    void validateAuthNumberToBankAccountNotEqualsAuthNumber() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0);

        BankAccountServiceRequest request = BankAccountServiceRequest.builder()
            .bankCode("088")
            .accountNumber("123123123456")
            .build();

        String authNumber = "012";
        redisRepository.save(request.getAccountNumber(), authNumber);

        //when
        assertThatThrownBy(() -> authService.validateAuthNumberToBankAccount(request, "111"))
            .isInstanceOf(AppException.class)
            .hasMessage("인증 번호가 일치하지 않습니다.");

        //then
        String findAuthNumber = redisRepository.findByKey(request.getAccountNumber());
        assertThat(findAuthNumber).isEqualTo(authNumber);
    }

    @DisplayName("은행 계좌로 발송한 인증 번호의 유효성을 검사한다.")
    @Test
    void validateAuthNumberToBankAccount() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0);

        BankAccountServiceRequest request = BankAccountServiceRequest.builder()
            .bankCode("088")
            .accountNumber("123123123456")
            .build();

        String authNumber = "012";
        redisRepository.save(request.getAccountNumber(), authNumber);

        //when
        boolean result = authService.validateAuthNumberToBankAccount(request, authNumber);

        //then
        assertThat(result).isTrue();

        String findAuthNumber = redisRepository.findByKey(request.getAccountNumber());
        assertThat(findAuthNumber).isNull();
    }
}