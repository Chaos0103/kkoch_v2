package com.ssafy.board_service.domain.notice.repository;

import com.ssafy.board_service.IntegrationTestSupport;
import com.ssafy.board_service.domain.notice.Notice;
import com.ssafy.board_service.domain.notice.repository.response.NoticeResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class NoticeQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private NoticeQueryRepository noticeQueryRepository;

    @Autowired
    private NoticeRepository noticeRepository;

    @DisplayName("공지사항 제목에 조회할 키워드가 포함된 고정되지 않은 공지사항 목록을 조회한다.")
    @Test
    void findNotFixedAllByNoticeTitleContaining() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 8, 15, 7, 0, 0);
        String keyword = "공지사항";
        PageRequest pageable = PageRequest.of(0, 10);

        Notice notice1 = createNotice(false, "서비스 공지사항 제목", LocalDateTime.of(2024, 8, 15, 0, 0, 0));
        Notice notice2 = createNotice(false, "서비스 공지사항", LocalDateTime.of(2024, 8, 15, 0, 0, 0));
        Notice notice3 = createNotice(false, "공지사항 제목", LocalDateTime.of(2024, 8, 15, 0, 0, 0));
        Notice notice4 = createNotice(false, "공지사항", LocalDateTime.of(2024, 8, 15, 6, 59, 59));
        createNotice(false, "공지사항", LocalDateTime.of(2024, 8, 15, 7, 0, 0));
        createNotice(true, "공지사항", LocalDateTime.of(2024, 8, 15, 0, 0, 0));

        //when
        List<NoticeResponse> content = noticeQueryRepository.findNotFixedAllByNoticeTitleContaining(keyword, currentDateTime, pageable);

        //then
        assertThat(content).hasSize(4)
            .extracting("id", "title", "isFixed", "createdDateTime")
            .containsExactly(
                tuple(notice4.getId(), notice4.getNoticeTitle(), false, notice4.getCreatedDateTime()),
                tuple(notice3.getId(), notice3.getNoticeTitle(), false, notice3.getCreatedDateTime()),
                tuple(notice2.getId(), notice2.getNoticeTitle(), false, notice2.getCreatedDateTime()),
                tuple(notice1.getId(), notice1.getNoticeTitle(), false, notice1.getCreatedDateTime())
            );
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