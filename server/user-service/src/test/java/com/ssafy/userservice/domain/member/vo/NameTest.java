package com.ssafy.userservice.domain.member.vo;

import com.ssafy.common.global.exception.MemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NameTest {

    @DisplayName("입력 받은 이름의 길이가 20자를 초과하면 예외가 발생한다.")
    @Test
    void invalidNameLength() {
        //given
        String name = "김".repeat(21);

        //when //then
        assertThatThrownBy(() -> Name.of(name))
            .isInstanceOf(MemberException.class)
            .hasMessage("이름은 최대 20자 입니다.");
    }

    @DisplayName("입력 받은 이름의 형식이 유효하지 않으면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {
        "John",
        "1234",
        "김@#$",
        "김 싸피",
        "김123",
        "싸피!",
        "김-",
        "한글123abc",
        "😊",
        "김,",
        "싸피.",
        "김\t싸피",
    })
    void invalidName(String name) {
        //given //when //then
        assertThatThrownBy(() -> Name.of(name))
            .isInstanceOf(MemberException.class)
            .hasMessage("이름을 올바르게 입력해주세요.");
    }

    @DisplayName("입력 받은 이름이 유효하면 이름 객체를 생성한다.")
    @Test
    void validName() {
        //given
        String name = "김".repeat(20);

        //when
        Name createdName = Name.of(name);

        //then
        assertThat(createdName)
            .isNotNull()
            .satisfies(n ->
                assertThat(n.getName()).isEqualTo(name)
            );
    }
}