package com.ssafy.userservice.domain.member.vo;

import com.ssafy.common.global.exception.MemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TelTest {

    @DisplayName("입력 받은 연락처의 길이가 11자가 아니라면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"0101234123", "010123412345"})
    void invalidTelLength(String tel) {
        //given //when //then
        assertThatThrownBy(() -> Tel.of(tel))
            .isInstanceOf(MemberException.class)
            .hasMessage("연락처를 올바르게 입력해주세요.");
    }

    @DisplayName("입력 받은 연락처의 형식이 유효하지 않으면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {
        "010-123-567",
        "(010)13-678",
        "010.1234.56",
        "abc12345678",
        "1234!567890",
        "010 123 578",
        "010@1234567",
        "0101234567a",
    })
    void invalidTel(String tel) {
        //given //when //then
        assertThatThrownBy(() -> Tel.of(tel))
            .isInstanceOf(MemberException.class)
            .hasMessage("연락처를 올바르게 입력해주세요.");
    }

    @DisplayName("입력 받은 연락처가 유효하면 연락처 객체를 생성한다.")
    @Test
    void validTel() {
        //given
        String tel = "01012341234";

        //when
        Tel createdTel = Tel.of(tel);

        //then
        assertThat(createdTel)
            .isNotNull()
            .satisfies(t ->
                assertThat(t.getTel()).isEqualTo(tel)
            );
    }
}