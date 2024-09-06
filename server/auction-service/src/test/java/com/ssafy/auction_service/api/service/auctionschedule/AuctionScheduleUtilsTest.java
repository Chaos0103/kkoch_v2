package com.ssafy.auction_service.api.service.auctionschedule;

import com.ssafy.auction_service.common.exception.AppException;
import com.ssafy.auction_service.common.util.TimeUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AuctionScheduleUtilsTest {

    @DisplayName("입력 받은 경매 시작 일시가 현재 시간보다 미래가 아니라면 예외가 발생한다.")
    @ValueSource(strings = {"2024-01-01T00:00:09", "2024-01-01T00:00:10"})
    @ParameterizedTest
    void auctionStartDateTimeIsNotFuture(String dateTime) {
        //given
        LocalDateTime auctionStartDateTime = TimeUtils.parse(dateTime);
        LocalDateTime current = LocalDateTime.of(2024, 1, 1, 0, 0, 10);

        //when //then
        assertThatThrownBy(() -> AuctionScheduleUtils.validateAuctionStartDateTime(auctionStartDateTime, current))
            .isInstanceOf(AppException.class)
            .hasMessage("경매 시작 일시를 올바르게 입력해주세요.");
    }

    @DisplayName("경매 시작 일시 유효성 검증을 한다.")
    @Test
    void validateAuctionStartDateTime() {
        //given
        LocalDateTime current = LocalDateTime.of(2024, 1, 1, 0, 0, 10);
        LocalDateTime auctionStartDateTime = LocalDateTime.of(2024, 1, 1, 0, 0, 11);

        //when
        boolean result = AuctionScheduleUtils.validateAuctionStartDateTime(auctionStartDateTime, current);

        //then
        assertThat(result).isTrue();
    }

}