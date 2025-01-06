package com.ssafy.user_service.api.service;

import com.ssafy.user_service.common.exception.AppException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SearchPeriodTest {

    @DisplayName("문자열 from의 일자를 LocalDateTime으로 변환한다.")
    @Test
    void getFrom() {
        //given
        SearchPeriod period = SearchPeriod.builder()
            .from("2024-01-01")
            .to("2024-02-01")
            .build();

        //when
        LocalDateTime from = period.getFrom();

        //then
        assertThat(from).isEqualTo(LocalDateTime.of(2024, 1, 1, 0, 0, 0));
    }

    @DisplayName("문자열 from이 비어있으면 null을 반환한다.")
    @NullAndEmptySource
    @ParameterizedTest
    void fromIsNull(String inputFrom) {
        //given
        SearchPeriod period = SearchPeriod.builder()
            .from(inputFrom)
            .to("2024-02-01")
            .build();

        //when
        LocalDateTime from = period.getFrom();

        //then
        assertThat(from).isNull();
    }

    @DisplayName("문자열 from의 포멧이 일치하지 않으면 예외가 발생한다.")
    @Test
    void fromFormatException() {
        //given
        SearchPeriod period = SearchPeriod.builder()
            .from("2024-01-1")
            .to("2024-02-01")
            .build();

        //when //then
        assertThatThrownBy(period::getFrom)
            .isInstanceOf(AppException.class)
            .hasMessage("날짜를 올바르게 입력해주세요.");
    }

    @DisplayName("문자열 to의 일자를 LocalDateTime으로 변환한다.")
    @Test
    void getTo() {
        //given
        SearchPeriod period = SearchPeriod.builder()
            .from("2024-01-01")
            .to("2024-02-01")
            .build();

        //when
        LocalDateTime to = period.getTo();

        //then
        assertThat(to).isEqualTo(LocalDateTime.of(2024, 2, 1, 23, 59, 59));
    }

    @DisplayName("문자열 to가 비어있으면 null을 반환한다.")
    @NullAndEmptySource
    @ParameterizedTest
    void toIsNull(String inputTo) {
        //given
        SearchPeriod period = SearchPeriod.builder()
            .from("2024-01-01")
            .to(inputTo)
            .build();

        //when
        LocalDateTime to = period.getTo();

        //then
        assertThat(to).isNull();
    }

    @DisplayName("문자열 from의 포멧이 일치하지 않으면 예외가 발생한다.")
    @Test
    void toFormatException() {
        //given
        SearchPeriod period = SearchPeriod.builder()
            .from("2024-01-01")
            .to("2024-02-1")
            .build();

        //when //then
        assertThatThrownBy(period::getTo)
            .isInstanceOf(AppException.class)
            .hasMessage("날짜를 올바르게 입력해주세요.");
    }

    @DisplayName("from이 to가 같은 일자일 수 있다.")
    @Test
    void valid() {
        //given
        SearchPeriod period = SearchPeriod.builder()
            .from("2024-01-01")
            .to("2024-01-01")
            .build();

        //when
        boolean result = period.valid();

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("from과 to 모두 null일 수 있다.")
    @Test
    void validAllNull() {
        //given
        SearchPeriod period = SearchPeriod.builder()
            .from(null)
            .to(null)
            .build();

        //when
        boolean result = period.valid();

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("from이 to보다 미래하면 예외가 발생한다.")
    @Test
    void fromIsFuture() {
        //given
        SearchPeriod period = SearchPeriod.builder()
            .from("2024-01-02")
            .to("2024-01-01")
            .build();

        //when //then
        assertThatThrownBy(period::valid)
            .isInstanceOf(AppException.class)
            .hasMessage("날짜를 올바르게 입력해주세요.");
    }

    @DisplayName("from만 null이면 예외가 발생한다.")
    @Test
    void onlyFromIsNull() {
        //given
        SearchPeriod period = SearchPeriod.builder()
            .from(null)
            .to("2024-02-01")
            .build();

        //when //then
        assertThatThrownBy(period::valid)
            .isInstanceOf(AppException.class)
            .hasMessage("시작 일자를 입력해주세요.");
    }

    @DisplayName("from만 null이면 예외가 발생한다.")
    @Test
    void onlyToIsNull() {
        //given
        SearchPeriod period = SearchPeriod.builder()
            .from("2024-01-01")
            .to(null)
            .build();

        //when //then
        assertThatThrownBy(period::valid)
            .isInstanceOf(AppException.class)
            .hasMessage("종료 일자를 입력해주세요.");
    }
}
