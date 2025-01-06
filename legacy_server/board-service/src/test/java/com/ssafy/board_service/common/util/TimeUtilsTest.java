package com.ssafy.board_service.common.util;

import com.ssafy.board_service.common.exception.AppException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TimeUtilsTest {

    @DisplayName("문자열 일시의 형식이 맞지 않다면 예외가 발생한다.")
    @Test
    void notMatchPattern() {
        //given
        LocalDateTime defaultValue = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        String str = "2024-01-01 01:00";

        //when //then
        assertThatThrownBy(() -> TimeUtils.parseToFixedDateTime(str, defaultValue))
            .isInstanceOf(AppException.class)
            .hasMessage("일시를 올바르게 입력해주세요.");
    }

    @DisplayName("문자열이 빈 문자열이라면 기본값을 반환한다.")
    @NullAndEmptySource
    @ParameterizedTest
    void defaultValue(String str) {
        //given
        LocalDateTime defaultValue = LocalDateTime.of(2024, 1, 1, 0, 0, 0);

        //when
        LocalDateTime result = TimeUtils.parseToFixedDateTime(str, defaultValue);

        //then
        assertThat(result).isEqualTo(defaultValue);
    }

    @DisplayName("문자열 일시를 LocalDateTime으로 파싱한다.")
    @Test
    void parseToFixedDateTime() {
        //given
        LocalDateTime defaultValue = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        String str = "2024-01-01T01:00";

        //when
        LocalDateTime result = TimeUtils.parseToFixedDateTime(str, defaultValue);

        //then
        assertThat(result).isEqualTo(LocalDateTime.of(2024, 1, 1, 1, 0, 0));
    }

    @DisplayName("현재 시간보다 과거 여부를 확인한다.")
    @CsvSource({"9,true", "10,false", "11,false"})
    @ParameterizedTest
    void isPast(int second, boolean expected) {
        //given
        LocalDateTime target = LocalDateTime.of(2024, 1, 1, 0, 0, second);
        LocalDateTime current = LocalDateTime.of(2024, 1, 1, 0, 0, 10);

        //when
        boolean result = TimeUtils.isPast(target, current);

        //then
        assertThat(result).isEqualTo(expected);
    }
}