package com.ssafy.boardservice.domain.notice.repository;

import com.ssafy.boardservice.api.client.UserServiceClient;
import com.ssafy.boardservice.domain.notice.Notice;
import com.ssafy.boardservice.domain.notice.repository.response.NoticeDisplayResponse;
import com.ssafy.boardservice.domain.notice.repository.response.NoticeListDisplayResponse;
import com.ssafy.boardservice.domain.notice.vo.NoticeFixedDateTime;
import com.ssafy.boardservice.domain.notice.vo.NoticeTitle;
import common.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class NoticeQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    NoticeQueryRepository noticeQueryRepository;

    @Autowired
    NoticeRepository noticeRepository;

    @MockitoBean
    UserServiceClient userServiceClient;

    @DisplayName("입력 받은 키워드가 고정되지 않은 공지사항 제목에 포함되어 있는 공지사항 목록을 조회한다.")
    @Test
    void findPagingByTitleContainingAndNotFixed() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2025, 3, 1, 18, 0, 0);
        Notice notice1 = createNotice(false, "공지사항 제목입니다.", currentDateTime.minusSeconds(1));
        Notice notice2 = createNotice(false, "공지사항 제목", currentDateTime.minusSeconds(1));
        Notice notice3 = createNotice(false, "제목입니다.", currentDateTime.minusSeconds(1));
        createNotice(true, "공지사항 제목입니다.", currentDateTime.minusSeconds(1));
        createNotice(false, "공지사항 제목입니다.", currentDateTime);
        createNotice(false, "공지사항 제목입니다.", currentDateTime.plusSeconds(1));

        Pageable pageable = PageRequest.of(0, 3);

        //when
        List<NoticeListDisplayResponse> findNotices = noticeQueryRepository.findPagingByTitleContainingAndNotFixed("제목", currentDateTime, pageable);

        //then
        assertThat(findNotices).hasSize(3)
            .extracting("noticeId", "title", "isFixed")
            .containsExactly(
                tuple(notice3.getId(), "제목입니다.", false),
                tuple(notice2.getId(), "공지사항 제목", false),
                tuple(notice1.getId(), "공지사항 제목입니다.", false)
            );
    }

    @DisplayName("고정된 공지사항 목록을 조회한다.")
    @Test
    void findAllByFixed() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2025, 3, 1, 18, 0, 0);
        createNotice(false, "공지사항 제목입니다.", currentDateTime.minusSeconds(1));
        Notice notice1 = createNotice(false, "공지사항 제목입니다.", currentDateTime);
        Notice notice2 = createNotice(false, "공지사항 제목입니다.", currentDateTime.plusSeconds(1));
        createNotice(true, "공지사항 제목입니다.", currentDateTime.plusSeconds(1));

        //when
        List<NoticeListDisplayResponse> findNotices = noticeQueryRepository.findAllByFixed(currentDateTime);

        //then
        assertThat(findNotices).hasSize(2)
            .extracting("noticeId", "title", "isFixed")
            .containsExactly(
                tuple(notice2.getId(), "공지사항 제목입니다.", true),
                tuple(notice1.getId(), "공지사항 제목입니다.", true)
            );
    }

    @DisplayName("입력 받은 키워드가 고정되지 않은 공지사항 제목에 포함되어 있는 공지사항 총 갯수를 조회한다.")
    @Test
    void countsByTitleContainingAndNotFixed() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2025, 3, 1, 18, 0, 0);
        createNotice(false, "공지사항 제목입니다.", currentDateTime.minusSeconds(1));
        createNotice(false, "공지사항 제목", currentDateTime.minusSeconds(1));
        createNotice(false, "제목입니다.", currentDateTime.minusSeconds(1));
        createNotice(true, "공지사항 제목입니다.", currentDateTime.minusSeconds(1));
        createNotice(false, "공지사항 제목입니다.", currentDateTime);
        createNotice(false, "공지사항 제목입니다.", currentDateTime.plusSeconds(1));

        //when
        int counts = noticeQueryRepository.countsByTitleContainingAndNotFixed("제목", currentDateTime);

        //then
        assertThat(counts).isEqualTo(3);
    }

    @DisplayName("공지사항 ID가 일치하는 공지사항을 조회한다.")
    @Test
    void findDisplayById() {
        //given
        LocalDateTime dateTime = LocalDateTime.of(2025, 3, 1, 0, 0, 0);
        Notice notice = createNotice(false, "공지사항 제목입니다.", dateTime);
        int noticeId = notice.getId();

        //when
        Optional<NoticeDisplayResponse> findNoticeDisplayResponse = noticeQueryRepository.findDisplayById(noticeId);

        //then
        assertThat(findNoticeDisplayResponse)
            .isPresent()
            .hasValueSatisfying(response -> {
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