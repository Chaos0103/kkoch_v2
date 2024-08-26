package com.ssafy.board_service.common.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;

class StringUtilsTest {

    @DisplayName("문자열 빈 값 여부를 확인한다.")
    @NullAndEmptySource
    @ParameterizedTest
    void isBlank(String str) {
        //given //when
        boolean result = StringUtils.isBlank(str);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("문자열 길이가 최대 길이를 초과하는지 확인한다.")
    @CsvSource({"4,true", "5,false"})
    @ParameterizedTest
    void isLengthMoreThan(int maxLength, boolean expected) {
        //given
        String str = "ssafy";

        //when
        boolean result = StringUtils.isLengthMoreThan(str, maxLength);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("문자열이 숫자로 이루어져 있는지 확인한다.")
    @CsvSource({"123,true", "백이십삼,false", "one,false"})
    @ParameterizedTest
    void isNumber(String str, boolean expected) {
        //given //when
        boolean result = StringUtils.isNumber(str);

        //then
        assertThat(result).isEqualTo(expected);
    }
}