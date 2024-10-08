package com.ssafy.board_service.domain.notice.repository;

import com.ssafy.board_service.IntegrationTestSupport;
import com.ssafy.board_service.domain.notice.Notice;
import com.ssafy.board_service.domain.notice.repository.response.NoticeDetailResponse;
import com.ssafy.board_service.domain.notice.repository.response.NoticeResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        createNotice(false, "서비스", LocalDateTime.of(2024, 8, 15, 0, 0, 0));
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

    @DisplayName("고정되지 않은 공지사항 조회시 조회할 키워드가 빈 값이면 검색 조건에서 제외한다.")
    @NullAndEmptySource
    @ParameterizedTest
    void findNotFixedAllByNoticeTitleContaining(String keyword) {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 8, 15, 7, 0, 0);
        PageRequest pageable = PageRequest.of(0, 10);

        Notice notice1 = createNotice(false, "서비스 공지사항 제목", LocalDateTime.of(2024, 8, 15, 0, 0, 0));
        Notice notice2 = createNotice(false, "서비스 공지사항", LocalDateTime.of(2024, 8, 15, 0, 0, 0));
        Notice notice3 = createNotice(false, "공지사항 제목", LocalDateTime.of(2024, 8, 15, 0, 0, 0));
        Notice notice4 = createNotice(false, "공지사항", LocalDateTime.of(2024, 8, 15, 6, 59, 59));
        Notice notice5 = createNotice(false, "서비스", LocalDateTime.of(2024, 8, 15, 0, 0, 0));
        createNotice(false, "공지사항", LocalDateTime.of(2024, 8, 15, 7, 0, 0));
        createNotice(true, "공지사항", LocalDateTime.of(2024, 8, 15, 0, 0, 0));

        //when
        List<NoticeResponse> content = noticeQueryRepository.findNotFixedAllByNoticeTitleContaining(keyword, currentDateTime, pageable);

        //then
        assertThat(content).hasSize(5)
            .extracting("id", "title", "isFixed", "createdDateTime")
            .containsExactly(
                tuple(notice5.getId(), notice5.getNoticeTitle(), false, notice5.getCreatedDateTime()),
                tuple(notice4.getId(), notice4.getNoticeTitle(), false, notice4.getCreatedDateTime()),
                tuple(notice3.getId(), notice3.getNoticeTitle(), false, notice3.getCreatedDateTime()),
                tuple(notice2.getId(), notice2.getNoticeTitle(), false, notice2.getCreatedDateTime()),
                tuple(notice1.getId(), notice1.getNoticeTitle(), false, notice1.getCreatedDateTime())
            );
    }

    @DisplayName("고정된 공지사항 목록을 조회한다.")
    @Test
    void findFixedAll() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 8, 15, 7, 0, 0);

        createNotice(false, "공지사항 제목", LocalDateTime.of(2024, 8, 15, 6, 59, 59));
        Notice notice1 = createNotice(false, "공지사항 제목", LocalDateTime.of(2024, 8, 15, 7, 0, 0));
        Notice notice2 = createNotice(false, "공지사항 제목", LocalDateTime.of(2024, 8, 15, 7, 0, 1));
        createNotice(true, "공지사항 제목", LocalDateTime.of(2024, 8, 15, 8, 0, 0));

        //when
        List<NoticeResponse> content = noticeQueryRepository.findFixedAll(currentDateTime);

        //then
        assertThat(content).hasSize(2)
            .extracting("id", "isFixed", "createdDateTime")
            .containsExactly(
                tuple(notice2.getId(), true, notice2.getCreatedDateTime()),
                tuple(notice1.getId(), true, notice1.getCreatedDateTime())
            );
    }

    @DisplayName("공지사항 제목에 조회할 키워드가 포함된 고정되지 않은 공지사항 목록 갯수를 조회한다.")
    @Test
    void countNotFixedByNoticeTitleContaining() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 8, 15, 7, 0, 0);
        String keyword = "공지사항";

        createNotice(false, "서비스 공지사항 제목", LocalDateTime.of(2024, 8, 15, 0, 0, 0));
        createNotice(false, "서비스 공지사항", LocalDateTime.of(2024, 8, 15, 0, 0, 0));
        createNotice(false, "공지사항 제목", LocalDateTime.of(2024, 8, 15, 0, 0, 0));
        createNotice(false, "공지사항", LocalDateTime.of(2024, 8, 15, 6, 59, 59));
        createNotice(false, "서비스", LocalDateTime.of(2024, 8, 15, 0, 0, 0));
        createNotice(false, "공지사항", LocalDateTime.of(2024, 8, 15, 7, 0, 0));
        createNotice(true, "공지사항", LocalDateTime.of(2024, 8, 15, 0, 0, 0));

        //when
        int total = noticeQueryRepository.countNotFixedByNoticeTitleContaining(keyword, currentDateTime);

        //then
        assertThat(total).isEqualTo(4);
    }

    @DisplayName("고정되지 않은 공지사항 갯수 조회시 조회할 키워드가 빈 값이면 검색 조건에서 제외한다.")
    @NullAndEmptySource
    @ParameterizedTest
    void countNotFixedByNoticeTitleContaining(String keyword) {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 8, 15, 7, 0, 0);

        createNotice(false, "서비스 공지사항 제목", LocalDateTime.of(2024, 8, 15, 0, 0, 0));
        createNotice(false, "서비스 공지사항", LocalDateTime.of(2024, 8, 15, 0, 0, 0));
        createNotice(false, "공지사항 제목", LocalDateTime.of(2024, 8, 15, 0, 0, 0));
        createNotice(false, "공지사항", LocalDateTime.of(2024, 8, 15, 6, 59, 59));
        createNotice(false, "서비스", LocalDateTime.of(2024, 8, 15, 0, 0, 0));
        createNotice(false, "공지사항", LocalDateTime.of(2024, 8, 15, 7, 0, 0));
        createNotice(true, "공지사항", LocalDateTime.of(2024, 8, 15, 0, 0, 0));

        //when
        int total = noticeQueryRepository.countNotFixedByNoticeTitleContaining(keyword, currentDateTime);

        //then
        assertThat(total).isEqualTo(5);
    }

    @DisplayName("공지사항 ID로 공지사항을 상세 조회한다.")
    @Test
    void findDetailById() {
        //given
        Notice notice = createNotice(false, "서비스 점검 안내", LocalDateTime.of(2024, 8, 15, 7, 0, 0));

        //when
        Optional<NoticeDetailResponse> findNotice = noticeQueryRepository.findDetailById(notice.getId());

        //then
        assertThat(findNotice).isPresent().get()
            .hasFieldOrPropertyWithValue("id", notice.getId())
            .hasFieldOrPropertyWithValue("title", notice.getNoticeTitle())
            .hasFieldOrPropertyWithValue("content", notice.getNoticeContent())
            .hasFieldOrPropertyWithValue("createdDateTime", notice.getCreatedDateTime());
    }

    @DisplayName("등록되지 않은 공지사항 ID로 공지사항 상세 조회시 빈 객체를 반환한다.")
    @Test
    void findDetailByIdWithoutNotice() {
        //given //when
        Optional<NoticeDetailResponse> findNotice = noticeQueryRepository.findDetailById(1);

        //then
        assertThat(findNotice).isEmpty();
    }

    @DisplayName("삭제된 공지사항 ID로 공지사항 상세 조회시 빈 객체를 반환한다.")
    @Test
    void findDetailByIdIsDeletedIsTrue() {
        //given
        Notice notice = createNotice(true, "서비스 점검 안내", LocalDateTime.of(2024, 8, 15, 7, 0, 0));

        //when
        Optional<NoticeDetailResponse> findNotice = noticeQueryRepository.findDetailById(notice.getId());

        //then
        assertThat(findNotice).isEmpty();
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