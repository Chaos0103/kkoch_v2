package com.ssafy.board_service.common.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class PageUtilsTest {

    @DisplayName("페이지 번호가 빈 문자열이거나 숫자가 아닌 경우 기본값 1을 반환한다.")
    @ValueSource(strings = {"일", "one"})
    @NullAndEmptySource
    @ParameterizedTest
    void parsePageNumber(String pageNumber) {
        //given //when
        int result = PageUtils.parsePageNumber(pageNumber);

        //then
        assertThat(result).isOne();
    }

    @DisplayName("페이지 번호가 양수가 아닌 경우 기본값 1을 반환한다.")
    @Test
    void parsePageNumberIsZero() {
        //given
        String pageNumber = "0";

        //when
        int result = PageUtils.parsePageNumber(pageNumber);

        //then
        assertThat(result).isOne();
    }

    @DisplayName("페이지 번호를 반환한다.")
    @Test
    void parsePageNumber() {
        //given
        String pageNumber = "1";

        //when
        int result = PageUtils.parsePageNumber(pageNumber);

        //then
        assertThat(result).isOne();
    }
}