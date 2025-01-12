package com.ssafy.userservice.domain.member.vo;

import com.ssafy.common.global.exception.MemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PasswordTest {

    PasswordEncoder encoder = new BCryptPasswordEncoder();

    @DisplayName("입력 받은 비밀번호의 길이가 8자 미만이거나 20자를 초과하면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"ssafy1!", "ssafy1234567890!12345"})
    void invalidPasswordLength(String password) {
        //given //when //then
        assertThatThrownBy(() -> Password.of(password))
            .isInstanceOf(MemberException.class)
            .hasMessage("비밀번호는 최소 8자에서 최대 20자 입니다.");
    }

    @DisplayName("입력 받은 비밀번호의 형식이 유효하지 않으면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {
        "password",
        "12345678",
        "!@#$%^&*",
        "Passw0rd",
        "password1234",
        "NoSpecialChar123"
    })
    void invalidPassword(String password) {
        //given //when //then
        assertThatThrownBy(() -> Password.of(password))
            .isInstanceOf(MemberException.class)
            .hasMessage("비밀번호를 올바르게 입력해주세요.");
    }

    @DisplayName("입력 받은 비밀번호가 유효하면 비밀번호 객체를 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"ssafy12!", "ssafy1234567890!1234"})
    void validPassword(String password) {
        //given //when
        Password createdPassword = Password.of(password);

        //then
        assertThat(createdPassword)
            .isNotNull()
            .satisfies(p ->
                assertThat(encoder.matches(password, p.getPassword())).isTrue()
            );
    }

    @DisplayName("입력 받은 비밀번호와 암호화된 비밀번호가 일치하면 true를 반환한다.")
    @Test
    void doesMatches() {
        //given
        String matchesPassword = "ssafy1234!";
        Password password = Password.of(matchesPassword);

        //when
        boolean result = password.matches(matchesPassword);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("입력 받은 비밀번호와 암호화된 비밀번호가 일치하지 않으면 false를 반환한다.")
    @Test
    void doesNotMatches() {
        //given
        String notMatchesPassword = "ssafy5678@";
        Password password = Password.of("ssafy1234!");

        //when
        boolean result = password.matches(notMatchesPassword);

        //then
        assertThat(result).isFalse();
    }
}