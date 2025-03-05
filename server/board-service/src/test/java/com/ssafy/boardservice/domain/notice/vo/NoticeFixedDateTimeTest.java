package com.ssafy.boardservice.domain.notice.vo;

import com.ssafy.common.global.exception.NoticeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NoticeFixedDateTimeTest {

    @DisplayName("입력 받은 고정일시가 현재일시보다 과거라면 예외가 발생한다.")
    @Test
    void invalidNoticeFixedDateTime() {
        //given
        LocalDateTime fixedDateTime = LocalDateTime.of(2025, 1, 15, 17, 59, 59);
        LocalDateTime current = LocalDateTime.of(2025, 1, 15, 18, 0, 0);

        //when //then
        assertThatThrownBy(() -> NoticeFixedDateTime.of(fixedDateTime, current))
            .isInstanceOf(NoticeException.class)
            .hasMessage("공지사항 고정 종료일시를 올바르게 입력해주세요.");
    }

    @DisplayName("입력 받은 고정일시가 유효하면 고정일시 객체를 생성한다.")
    @Test
    void validNoticeFixedDateTime() {
        //given
        LocalDateTime fixedDateTime = LocalDateTime.of(2025, 1, 15, 18, 0, 0);
        LocalDateTime current = LocalDateTime.of(2025, 1, 15, 18, 0, 0);

        //when
        NoticeFixedDateTime createdNoticeFixedDateTime = NoticeFixedDateTime.of(fixedDateTime, current);

        //then
        assertThat(createdNoticeFixedDateTime)
            .isNotNull()
            .satisfies(noticeFixedDateTime ->
                assertThat(noticeFixedDateTime.getValue()).isEqualTo(fixedDateTime)
            );
    }

    @DisplayName("고정 여부를 반환한다.")
    @Test
    void isFixed() {
        //given
        LocalDateTime fixedDateTime = LocalDateTime.of(2025, 1, 15, 18, 0, 0);
        LocalDateTime current = LocalDateTime.of(2025, 1, 15, 18, 0, 0);
        NoticeFixedDateTime noticeFixedDateTime = NoticeFixedDateTime.of(fixedDateTime, current);

        //when
        boolean isFixed = noticeFixedDateTime.isFixed(current);

        //then
        assertThat(isFixed).isFalse();
    }
}