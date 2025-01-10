package com.ssafy.userservice.domain.member.vo;

import com.ssafy.common.global.exception.MemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BusinessNumberTest {

    @DisplayName("입력 받은 사업자 번호의 길이가 12자를 초과하면 예외가 발생한다.")
    @Test
    void invalidBusinessNumberLength() {
        //given
        String businessNumber = "0123456789012";

        //when //then
        assertThatThrownBy(() -> BusinessNumber.of(businessNumber))
            .isInstanceOf(MemberException.class)
            .hasMessage("사업자 번호는 최대 12자 입니다.");
    }

    @DisplayName("입력 받은 사업자 번호의 형식이 유효하지 않으면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {
        "123-12-12345",
        "123.12.12345",
        "abc1212345",
        "123 12 12345"
    })
    void invalidBusinessNumber(String businessNumber) {
        //given //when //then
        assertThatThrownBy(() -> BusinessNumber.of(businessNumber))
            .isInstanceOf(MemberException.class)
            .hasMessage("사업자 번호를 올바르게 입력해주세요.");
    }

    @DisplayName("입력 받은 사업자 번호가 유효하면 사업자 번호 객체를 생성한다.")
    @Test
    void validBusinessNumber() {
        //given
        String businessNumber = "012345678901";

        //when
        BusinessNumber createdBusinessNumber = BusinessNumber.of(businessNumber);

        //then
        assertThat(createdBusinessNumber)
            .isNotNull()
            .satisfies(b ->
                assertThat(b.getBusinessNumber()).isEqualTo(businessNumber)
            );
    }
}