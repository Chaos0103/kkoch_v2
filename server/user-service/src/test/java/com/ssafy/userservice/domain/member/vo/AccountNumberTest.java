package com.ssafy.userservice.domain.member.vo;

import com.ssafy.common.global.exception.MemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccountNumberTest {

    @DisplayName("입력 받은 은행 계좌의 길이가 14자를 초과하면 예외가 발생한다.")
    @Test
    void invalidAccountNumberLength() {
        //given
        String accountNumber = "123412312345678";

        //when //then
        assertThatThrownBy(() -> AccountNumber.of(accountNumber))
            .isInstanceOf(MemberException.class)
            .hasMessage("은행 계좌는 최대 14자 입니다.");
    }

    @DisplayName("입력 받은 은행 계좌의 형식이 유효하지 않으면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {
        "123-12-12345",
        "123.12.12345",
        "abc1212345",
        "123 12 12345"
    })
    void invalidAccountNumber(String accountNumber) {
        //given //when //then
        assertThatThrownBy(() -> AccountNumber.of(accountNumber))
            .isInstanceOf(MemberException.class)
            .hasMessage("은행 계좌를 올바르게 입력해주세요.");
    }

    @DisplayName("입력 받은 은행 계좌가 유효하면 은행 계좌 객체를 생성한다.")
    @Test
    void validAccountNumber() {
        //given
        String accountNumber = "12341231234567";

        //when
        AccountNumber createdAccountNumber = AccountNumber.of(accountNumber);

        //then
        assertThat(createdAccountNumber)
            .isNotNull()
            .satisfies(a ->
                assertThat(a.getAccountNumber()).isEqualTo(accountNumber)
            );
    }

}