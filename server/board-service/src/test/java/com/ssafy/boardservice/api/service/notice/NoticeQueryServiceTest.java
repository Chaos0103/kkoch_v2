package com.ssafy.boardservice.api.service.notice;

import com.ssafy.boardservice.api.client.UserServiceClient;
import com.ssafy.boardservice.domain.notice.Notice;
import com.ssafy.boardservice.domain.notice.repository.NoticeRepository;
import com.ssafy.boardservice.domain.notice.repository.response.NoticeDisplayResponse;
import com.ssafy.boardservice.domain.notice.repository.response.NoticeListDisplayResponse;
import com.ssafy.boardservice.domain.notice.vo.NoticeFixedDateTime;
import com.ssafy.boardservice.domain.notice.vo.NoticeTitle;
import com.ssafy.common.api.ListResponse;
import com.ssafy.common.api.PageResponse;
import com.ssafy.common.global.exception.NoticeException;
import common.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class NoticeQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    NoticeQueryService noticeQueryService;

    @Autowired
    NoticeRepository noticeRepository;

    @MockitoBean
    UserServiceClient userServiceClient;

    @DisplayName("입력 받은 키워드가 제목에 포함된 고정되지 않은 공지사항 목록을 조회한다.")
    @Test
    void searchNotFixedNotices() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2025, 3, 1, 18, 0, 0);
        createNotice(false, "공지사항 제목입니다.", currentDateTime.minusSeconds(1));
        Notice notice1 = createNotice(false, "공지사항 제목", currentDateTime.minusSeconds(1));
        Notice notice2 = createNotice(false, "제목입니다.", currentDateTime.minusSeconds(1));
        createNotice(true, "공지사항 제목입니다.", currentDateTime.minusSeconds(1));
        createNotice(false, "공지사항 제목입니다.", currentDateTime);
        createNotice(false, "공지사항 제목입니다.", currentDateTime.plusSeconds(1));

        Pageable pageable = PageRequest.of(0, 2);

        //when
        PageResponse<NoticeListDisplayResponse> responses = noticeQueryService.searchNotFixedNotices("제목", currentDateTime, pageable);

        //then
        assertThat(responses).isNotNull()
            .satisfies(response -> {
                assertThat(response.getSize()).isEqualTo(2);
                assertThat(response.getCurrentPage()).isOne();
                assertThat(response.getIsFirst()).isTrue();
                assertThat(response.getIsLast()).isFalse();
                assertThat(response.getContent()).hasSize(2)
                    .extracting("noticeId", "title", "isFixed")
                    .containsExactly(
                        tuple(notice2.getId(), "제목입니다.", false),
                        tuple(notice1.getId(), "공지사항 제목", false)
                    );
            });
    }

    @DisplayName("고정된 공지사항 목록을 조회한다.")
    @Test
    void searchFixedNotices() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2025, 3, 1, 18, 0, 0);
        createNotice(false, "공지사항 제목입니다.", currentDateTime.minusSeconds(1));
        Notice notice1 = createNotice(false, "공지사항 제목입니다.", currentDateTime);
        Notice notice2 = createNotice(false, "공지사항 제목입니다.", currentDateTime.plusSeconds(1));
        createNotice(true, "공지사항 제목입니다.", currentDateTime.plusSeconds(1));

        //when
        ListResponse<NoticeListDisplayResponse> responses = noticeQueryService.searchFixedNotices(currentDateTime);

        //then
        assertThat(responses).isNotNull()
            .satisfies(response -> {
                assertThat(response.getSize()).isEqualTo(2);
                assertThat(response.getContent()).hasSize(2)
                    .extracting("noticeId", "title", "isFixed")
                    .containsExactly(
                        tuple(notice2.getId(), "공지사항 제목입니다.", true),
                        tuple(notice1.getId(), "공지사항 제목입니다.", true)
                    );
            });
    }

    @DisplayName("공지사항 ID가 일치하는 공지사항이 존재하지 않으면 예외가 발생한다.")
    @Test
    void searchNoticeWhenNotFoundNotice() {
        //given
        int invalidNoticeId = 1;

        //when //then
        assertThatThrownBy(() -> noticeQueryService.searchNotice(invalidNoticeId))
            .isInstanceOf(NoticeException.class)
            .hasMessage("공지사항을 찾을 수 없습니다.");
    }

    @DisplayName("공지사항 ID가 일치하는 공지사항을 조회한다.")
    @Test
    void searchNotice() {
        //given
        LocalDateTime dateTime = LocalDateTime.of(2025, 3, 1, 0, 0, 0);
        Notice notice = createNotice(false, "공지사항 제목입니다.", dateTime);
        int noticeId = notice.getId();

        //when
        NoticeDisplayResponse content = noticeQueryService.searchNotice(noticeId);

        //then
        assertThat(content)
            .satisfies(response -> {
                assertThat(response.getNoticeId()).isEqualTo(noticeId);
                assertThat(response.getTitle()).isEqualTo("공지사항 제목입니다.");
                assertThat(response.getContent()).isEqualTo("공지사항 내용입니다.");
                assertThat(response.getCreatedDateTime()).isNotNull();
            });
    }

    private Notice createNotice(boolean isDeleted, String title, LocalDateTime dateTime) {
        Notice notice = Notice.builder()
            .isDeleted(isDeleted)
            .createdBy(1L)
            .lastModifiedBy(1L)
            .title(NoticeTitle.of(title))
            .content("공지사항 내용입니다.")
            .toFixedDateTime(NoticeFixedDateTime.of(dateTime, dateTime))
            .build();
        return noticeRepository.save(notice);
    }
}