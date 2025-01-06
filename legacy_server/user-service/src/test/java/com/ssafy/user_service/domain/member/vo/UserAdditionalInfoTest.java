package com.ssafy.user_service.domain.member.vo;

import com.ssafy.user_service.common.exception.LengthOutOfRangeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserAdditionalInfoTest {

    @DisplayName("사업자 번호의 길이가 12를 초과하면 예외가 발생한다.")
    @ValueSource(strings = {"0123456789012"})
    @NullAndEmptySource
    @ParameterizedTest
    void businessNumberOutOfLength(String businessNumber) {
        assertThatThrownBy(() -> createUserAdditionalInfo(businessNumber, null))
            .isInstanceOf(LengthOutOfRangeException.class)
            .hasMessage("사업자 번호의 길이는 최대 12자리 입니다.");
    }

    @DisplayName("회원 추가 정보 객체를 생성한다.")
    @Test
    void constructor() {
        UserAdditionalInfo userAdditionalInfo = createUserAdditionalInfo("012345678901", null);

        assertThat(userAdditionalInfo).isNotNull()
            .hasFieldOrPropertyWithValue("businessNumber", "012345678901")
            .hasFieldOrPropertyWithValue("bankAccount", null);
    }

    @DisplayName("회원 추가 정보를 생성시 은행 계좌는 null이다.")
    @Test
    void create() {
        UserAdditionalInfo userAdditionalInfo = UserAdditionalInfo.create("1231212345");

        assertThat(userAdditionalInfo).isNotNull()
            .hasFieldOrPropertyWithValue("businessNumber", "1231212345")
            .hasFieldOrPropertyWithValue("bankAccount", null);
    }

    @DisplayName("사업자 번호와 은행 계좌가 동일하면 동일한 객체이다.")
    @Test
    void equals() {
        BankAccount bankAccount = createBankAccount();
        UserAdditionalInfo userAdditionalInfo1 = createUserAdditionalInfo("1231212345", bankAccount);
        UserAdditionalInfo userAdditionalInfo2 = createUserAdditionalInfo("1231212345", bankAccount);

        assertThat(userAdditionalInfo1).isEqualTo(userAdditionalInfo2);
    }

    @DisplayName("은행 계좌가 변경된 객체를 생성한다.")
    @Test
    void withBankAccount() {
        UserAdditionalInfo userAdditionalInfo = createUserAdditionalInfo("1231212345", null);

        UserAdditionalInfo modifiedUserAdditionalInfo = userAdditionalInfo.withBankAccount("088", "123123123456");

        assertThat(modifiedUserAdditionalInfo)
            .isNotNull()
            .isNotEqualTo(userAdditionalInfo)
            .hasFieldOrPropertyWithValue("businessNumber", "1231212345")
            .hasFieldOrPropertyWithValue("bankAccount.bankCode", "088")
            .hasFieldOrPropertyWithValue("bankAccount.accountNumber", "123123123456");

    }

    private UserAdditionalInfo createUserAdditionalInfo(String businessNumber, BankAccount bankAccount) {
        return UserAdditionalInfo.builder()
            .businessNumber(businessNumber)
            .bankAccount(bankAccount)
            .build();
    }

    private BankAccount createBankAccount() {
        return BankAccount.builder()
            .bankCode("088")
            .accountNumber("123123123456")
            .build();
    }
}