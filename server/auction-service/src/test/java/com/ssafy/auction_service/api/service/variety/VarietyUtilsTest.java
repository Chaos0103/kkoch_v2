package com.ssafy.auction_service.api.service.variety;

import com.ssafy.auction_service.common.exception.LengthOutOfRangeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VarietyUtilsTest {

    @DisplayName("입력 받은 품목명의 길이가 20자를 초과하면 예외가 발생한다.")
    @Test
    void itemNameIsMoreThanMaxLength() {
        //given
        String itemName = "꽃".repeat(21);

        //when //then
        assertThatThrownBy(() -> VarietyUtils.validateItemName(itemName))
            .isInstanceOf(LengthOutOfRangeException.class)
            .hasMessage("품목명의 길이는 최대 20자리 입니다.");
    }

    @DisplayName("품목명 유효성 검증을 한다.")
    @Test
    void validateItemName() {
        //given
        String itemName = "꽃".repeat(20);

        //when
        boolean result = VarietyUtils.validateItemName(itemName);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("입력 받은 품종명의 길이가 20자를 초과하면 예외가 발생한다.")
    @Test
    void varietyNameIsMoreThanMaxLength() {
        //given
        String varietyName = "꽃".repeat(21);

        //when //then
        assertThatThrownBy(() -> VarietyUtils.validateVarietyName(varietyName))
            .isInstanceOf(LengthOutOfRangeException.class)
            .hasMessage("품종명의 길이는 최대 20자리 입니다.");
    }

    @DisplayName("품종명 유효성 검증을 한다.")
    @Test
    void validateVarietyName() {
        //given
        String varietyName = "꽃".repeat(20);

        //when
        boolean result = VarietyUtils.validateVarietyName(varietyName);

        //then
        assertThat(result).isTrue();
    }
}