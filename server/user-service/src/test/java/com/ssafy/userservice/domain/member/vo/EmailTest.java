package com.ssafy.userservice.domain.member.vo;

import com.ssafy.common.global.exception.MemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailTest {

    @DisplayName("입력 받은 이메일의 길이가 100자를 초과하면 예외가 발생한다.")
    @Test
    void invalidEmailLength() {
        //given
        String email = "a".repeat(91) + "@gmail.com";

        //when //then
        assertThatThrownBy(() -> Email.of(email))
            .isInstanceOf(MemberException.class)
            .hasMessage("이메일은 최대 100자 입니다.");
    }

    @DisplayName("입력 받은 이메일의 형식이 유효하지 않으면 예외가 발생한다.")
    @ValueSource(strings = {
        "ssafy",
        "@gmail.com",
        "ssafy@.com",
        "ssafy@gmail",
        "ssafy@gmail,com",
        "ssafy@gmail..com",
        "ssafy@gmail.c",
        "ssafy@.gmail.com",
        "ssafy@gmail.com.",
        "ssafy@gmail_com"
    })
    @ParameterizedTest
    void invalidEmail(String email) {
        //given //when //then
        assertThatThrownBy(() -> Email.of(email))
            .isInstanceOf(MemberException.class)
            .hasMessage("이메일을 올바르게 입력해주세요.");
    }

    @DisplayName("입력 받은 이메일이 유효하면 이메일 객체를 생성한다.")
    @Test
    void validEmail() {
        //given
        String email = "a".repeat(90) + "@gmail.com";

        //when
        Email createdEmail = Email.of(email);

        //then
        assertThat(createdEmail)
            .isNotNull()
            .satisfies(e ->
                assertThat(e.getEmail()).isEqualTo(email)
            );
    }
}