package com.ssafy.user_service.domain.member;

import com.ssafy.user_service.common.exception.LengthOutOfRangeException;
import com.ssafy.user_service.common.exception.NotSupportedException;
import com.ssafy.user_service.domain.member.vo.BankAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BankAccountTest {

    @DisplayName("은행 코드의 길이가 3이 아니면 예외가 발생한다.")
    @ValueSource(strings = {"12", "1234"})
    @NullAndEmptySource
    @ParameterizedTest
    void bankCodeOutOfLength(String bankCode) {
        assertThatThrownBy(() -> createBankAccount(bankCode, "123123123456"))
            .isInstanceOf(LengthOutOfRangeException.class)
            .hasMessage("은행 코드를 올바르게 입력해주세요.");
    }

    @DisplayName("은행 코드를 지원하지 않으면 예외가 발생한다.")
    @Test
    void bankCodeNotSupported() {
        assertThatThrownBy(() -> createBankAccount("000", "123123123456"))
            .isInstanceOf(NotSupportedException.class)
            .hasMessage("지원하지 않는 은행 코드입니다.");
    }

    @DisplayName("은행 계좌의 길이가 14를 초과하면 예외가 발생한다.")
    @ValueSource(strings = {"012345678912345"})
    @NullAndEmptySource
    @ParameterizedTest
    void accountNumberOutOfLength(String accountNumber) {
        assertThatThrownBy(() -> createBankAccount("088", accountNumber))
            .isInstanceOf(LengthOutOfRangeException.class)
            .hasMessage("은행 계좌의 길이는 최대 14자리 입니다.");
    }

    @DisplayName("은행 계좌 객체를 생성한다.")
    @Test
    void bankCodeLength() {
        BankAccount bankAccount = createBankAccount("088", "01234567891234");

        assertThat(bankAccount).isNotNull()
            .hasFieldOrPropertyWithValue("bankCode", "088")
            .hasFieldOrPropertyWithValue("accountNumber", "01234567891234");
    }

    @DisplayName("은행 코드와 계좌가 동일하면 동일한 객체이다.")
    @Test
    void equals() {
        BankAccount bankAccount1 = createBankAccount("088", "123123123456");
        BankAccount bankAccount2 = createBankAccount("088", "123123123456");

        assertThat(bankAccount1.equals(bankAccount2)).isTrue();
    }

    private static BankAccount createBankAccount(String bankCode, String accountNumber) {
        return BankAccount.builder()
            .bankCode(bankCode)
            .accountNumber(accountNumber)
            .build();
    }

}