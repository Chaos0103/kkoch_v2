package com.ssafy.auction_service.api.service.auctionvariety;

import com.ssafy.auction_service.common.exception.LengthOutOfRangeException;
import com.ssafy.auction_service.common.exception.NotSupportedException;
import com.ssafy.auction_service.common.exception.StringFormatException;
import com.ssafy.auction_service.domain.auctionvariety.PlantGrade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AuctionVarietyUtilsTest {

    @DisplayName("입력 받은 화훼등급이 지원하지 않는 항목이라면 예외가 발생한다.")
    @Test
    void plantGradeIsNotSupported() {
        //given
        String plantGrade = "ABCD";

        //when //then
        assertThatThrownBy(() -> AuctionVarietyUtils.parsePlantGrade(plantGrade))
            .isInstanceOf(NotSupportedException.class)
            .hasMessage("지원하지 않는 화훼등급입니다.");
    }

    @DisplayName("화훼등급 타입을 변환한다.")
    @Test
    void parsePlantGrade() {
        //given
        String plantGrade = "SUPER";

        //when
        PlantGrade result = AuctionVarietyUtils.parsePlantGrade(plantGrade);

        //then
        assertThat(result).isEqualByComparingTo(PlantGrade.SUPER);
    }

    @DisplayName("입력 받은 출하 지역의 길이가 20자를 초과하면 예외가 발생한다.")
    @Test
    void regionIsMoreThanMaxLength() {
        //given
        String region = "가".repeat(21);

        //when //then
        assertThatThrownBy(() -> AuctionVarietyUtils.validateRegion(region))
            .isInstanceOf(LengthOutOfRangeException.class)
            .hasMessage("출하 지역의 길이는 최대 20자리 입니다.");
    }

    @DisplayName("입력 받은 출하 지역이 한국어가 아니라면 예외가 발생한다.")
    @Test
    void regionIsNotKorean() {
        //given
        String region = "abc";

        //when //then
        assertThatThrownBy(() -> AuctionVarietyUtils.validateRegion(region))
            .isInstanceOf(StringFormatException.class)
            .hasMessage("출하 지역을 올바르게 입력해주세요.");
    }

    @DisplayName("출하 지역 유효성 검증을 한다.")
    @Test
    void validateRegion() {
        //given
        String region = "가".repeat(20);

        //when
        boolean result = AuctionVarietyUtils.validateRegion(region);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("입력 받은 출하자의 길이가 20자를 초과하면 예외가 발생한다.")
    @Test
    void shipperIsMoreThanMaxLength() {
        //given
        String shipper = "가".repeat(21);

        //when //then
        assertThatThrownBy(() -> AuctionVarietyUtils.validateShipper(shipper))
            .isInstanceOf(LengthOutOfRangeException.class)
            .hasMessage("출하자의 길이는 최대 20자리 입니다.");
    }

    @DisplayName("입력 받은 출하자가 한국어가 아니라면 예외가 발생한다.")
    @Test
    void shipperIsNotKorean() {
        //given
        String shipper = "abc";

        //when //then
        assertThatThrownBy(() -> AuctionVarietyUtils.validateShipper(shipper))
            .isInstanceOf(StringFormatException.class)
            .hasMessage("출하자를 올바르게 입력해주세요.");
    }

    @DisplayName("출하자 유효성 검증을 한다.")
    @Test
    void validateShipper() {
        //given
        String shipper = "가".repeat(20);

        //when
        boolean result = AuctionVarietyUtils.validateShipper(shipper);

        //then
        assertThat(result).isTrue();
    }
}