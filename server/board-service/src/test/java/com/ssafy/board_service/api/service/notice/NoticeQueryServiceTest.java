package com.ssafy.board_service.api.service.notice;

import com.ssafy.board_service.IntegrationTestSupport;
import com.ssafy.board_service.api.PageResponse;
import com.ssafy.board_service.domain.notice.Notice;
import com.ssafy.board_service.domain.notice.repository.NoticeRepository;
import com.ssafy.board_service.domain.notice.repository.response.NoticeDetailResponse;
import com.ssafy.board_service.domain.notice.repository.response.NoticeResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class NoticeQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private NoticeQueryService noticeQueryService;

    @Autowired
    private NoticeRepository noticeRepository;

    @DisplayName("페이지 번호와 조회할 키워드를 입력 받아 공지사항 목록을 조회한다.")
    @Test
    void searchNotFixedNotices() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 8, 15, 7, 0, 0);
        String keyword = "공지사항";

        Notice notice1 = createNotice(false, "서비스 공지사항 제목", LocalDateTime.of(2024, 8, 15, 0, 0, 0));
        Notice notice2 = createNotice(false, "서비스 공지사항", LocalDateTime.of(2024, 8, 15, 0, 0, 0));
        Notice notice3 = createNotice(false, "공지사항 제목", LocalDateTime.of(2024, 8, 15, 0, 0, 0));
        Notice notice4 = createNotice(false, "공지사항", LocalDateTime.of(2024, 8, 15, 6, 59, 59));
        createNotice(false, "서비스", LocalDateTime.of(2024, 8, 15, 0, 0, 0));
        createNotice(false, "공지사항", LocalDateTime.of(2024, 8, 15, 7, 0, 0));
        createNotice(true, "공지사항", LocalDateTime.of(2024, 8, 15, 0, 0, 0));

        //when
        PageResponse<NoticeResponse> response = noticeQueryService.searchNotFixedNotices(0, keyword, currentDateTime);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("currentPage", 1)
            .hasFieldOrPropertyWithValue("size", 10)
            .hasFieldOrPropertyWithValue("isFirst", true)
            .hasFieldOrPropertyWithValue("isLast", true);

        assertThat(response.getContent()).hasSize(4)
            .extracting("id", "title", "isFixed", "createdDateTime")
            .containsExactly(
                tuple(notice4.getId(), notice4.getNoticeTitle(), false, notice4.getCreatedDateTime()),
                tuple(notice3.getId(), notice3.getNoticeTitle(), false, notice3.getCreatedDateTime()),
                tuple(notice2.getId(), notice2.getNoticeTitle(), false, notice2.getCreatedDateTime()),
                tuple(notice1.getId(), notice1.getNoticeTitle(), false, notice1.getCreatedDateTime())
            );
    }

    @DisplayName("고정된 공지사항 목록을 조회한다.")
    @Test
    void searchFixedNotices() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 8, 15, 7, 0, 0);

        createNotice(false, "공지사항 제목", LocalDateTime.of(2024, 8, 15, 6, 59, 59));
        Notice notice1 = createNotice(false, "공지사항 제목", LocalDateTime.of(2024, 8, 15, 7, 0, 0));
        Notice notice2 = createNotice(false, "공지사항 제목", LocalDateTime.of(2024, 8, 15, 7, 0, 1));
        createNotice(true, "공지사항 제목", LocalDateTime.of(2024, 8, 15, 8, 0, 0));

        //when
        FixedNoticeResponse response = noticeQueryService.searchFixedNotices(currentDateTime);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("size", 2);
        assertThat(response.getContent()).hasSize(2)
            .extracting("id", "isFixed", "createdDateTime")
            .containsExactly(
                tuple(notice2.getId(), true, notice2.getCreatedDateTime()),
                tuple(notice1.getId(), true, notice1.getCreatedDateTime())
            );
    }

    @DisplayName("공지사항을 상세 조회한다.")
    @Test
    void searchNotice() {
        //given
        Notice notice = createNotice(false, "서비스 긴급 점검", LocalDateTime.of(2024, 8, 15, 7, 0, 0));

        //when
        NoticeDetailResponse response = noticeQueryService.searchNotice(notice.getId());

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("id", notice.getId())
            .hasFieldOrPropertyWithValue("title", notice.getNoticeTitle())
            .hasFieldOrPropertyWithValue("content", notice.getNoticeContent())
            .hasFieldOrPropertyWithValue("createdDateTime", notice.getCreatedDateTime());
    }

    private Notice createNotice(boolean isDeleted, String noticeTitle, LocalDateTime toFixedDateTime) {
        Notice notice = Notice.builder()
            .isDeleted(isDeleted)
            .createdBy(1L)
            .lastModifiedBy(1L)
            .noticeTitle(noticeTitle)
            .noticeContent("notice content")
            .toFixedDateTime(toFixedDateTime)
            .build();
        return noticeRepository.save(notice);
    }
}