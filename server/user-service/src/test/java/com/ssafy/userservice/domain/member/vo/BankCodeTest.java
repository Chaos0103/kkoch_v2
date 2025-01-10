package com.ssafy.userservice.domain.member.vo;

import com.ssafy.common.global.exception.MemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BankCodeTest {

    @DisplayName("입력 받은 은행 코드를 지원하지 않으면 예외가 발생한다.")
    @Test
    void invalidBankCode() {
        //given
        String bankCode = "000";

        //when //then
        assertThatThrownBy(() -> BankCode.of(bankCode))
            .isInstanceOf(MemberException.class)
            .hasMessage("유효하지 않은 은행 코드입니다.");
    }

    @DisplayName("입력 받은 은행 코드가 유효하면 은행 모드 객체를 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"088", "004", "081", "020", "011"})
    void validBankCode(String bankCode) {
        //given //when
        BankCode createdBankCode = BankCode.of(bankCode);

        //then
        assertThat(createdBankCode)
            .isNotNull()
            .satisfies(b ->
                assertThat(b.getBankCode()).isEqualTo(bankCode)
            );
    }
}