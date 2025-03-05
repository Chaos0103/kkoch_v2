package com.ssafy.boardservice.domain.notice.vo;

import com.ssafy.common.global.exception.NoticeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NoticeTitleTest {

    @DisplayName("입력 받은 제목의 길이가 50자를 초과하면 예외가 발생한다.")
    @Test
    void invalidTitleLength() {
        //given
        String title = "가".repeat(51);

        //when //then
        assertThatThrownBy(() -> NoticeTitle.of(title))
            .isInstanceOf(NoticeException.class)
            .hasMessage("공지사항 제목은 최대 50자 입니다.");
    }

    @DisplayName("입력 받은 제목이 유효하면 제목 객체를 생성한다.")
    @Test
    void validTitle() {
        //given
        String title = "가".repeat(50);

        //when
        NoticeTitle createdTitle = NoticeTitle.of(title);

        //then
        assertThat(createdTitle)
            .isNotNull()
            .satisfies(t ->
                assertThat(t.getValue()).isEqualTo(title)
            );
    }
}