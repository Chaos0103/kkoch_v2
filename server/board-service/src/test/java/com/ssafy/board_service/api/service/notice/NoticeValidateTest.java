package com.ssafy.board_service.api.service.notice;

import com.ssafy.board_service.common.exception.AppException;
import com.ssafy.board_service.common.exception.LengthOutOfRangeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NoticeValidateTest {

    @DisplayName("공지사항 제목의 길이가 최대 범위를 벗어나면 예외가 발생한다.")
    @Test
    void noticeTitleLengthMoreThanMaxLength() {
        //given
        String noticeTitle = "가".repeat(51);

        //when //then
        assertThatThrownBy(() -> NoticeValidate.validateNoticeTitle(noticeTitle))
            .isInstanceOf(LengthOutOfRangeException.class)
            .hasMessage("공지사항 제목의 길이는 최대 50자리 입니다.");
    }

    @DisplayName("공지사항 제목의 유효성을 검증한다.")
    @Test
    void validateNoticeTitle() {
        //given
        String noticeTitle = "가".repeat(50);

        //when
        boolean result = NoticeValidate.validateNoticeTitle(noticeTitle);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("상단 고정 종료일시가 현재 시간보다 과거라면 예외가 발생한다.")
    @Test
    void toFixedDateTimeIsPast() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 8, 15, 0, 0, 0);
        LocalDateTime toFixedDateTime = LocalDateTime.of(2024, 8, 14, 23, 59, 59);

        //when //then
        assertThatThrownBy(() -> NoticeValidate.validateToFixedDateTime(toFixedDateTime, currentDateTime))
            .isInstanceOf(AppException.class)
            .hasMessage("고정 종료일시를 올바르게 입력해주세요.");
    }

    @DisplayName("상단 고정 종료일시가 null인 경우 검증을 하지 않는다.")
    @Test
    void toFixedDateTimeIsNull() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 8, 15, 0, 0, 0);
        LocalDateTime toFixedDateTime = null;

        //when
        boolean result = NoticeValidate.validateToFixedDateTime(toFixedDateTime, currentDateTime);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("공지사항 상단 고정 종료일시의 유효성을 검증한다.")
    @Test
    void validateToFixedDateTime() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 8, 15, 0, 0, 0);
        LocalDateTime toFixedDateTime = LocalDateTime.of(2024, 8, 15, 0, 0, 0);

        //when
        boolean result = NoticeValidate.validateToFixedDateTime(toFixedDateTime, currentDateTime);

        //then
        assertThat(result).isTrue();
    }
}