package com.kkoch.admin.domain.notice.repository;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.domain.notice.repository.response.NoticeResponse;
import com.kkoch.admin.domain.notice.Notice;
import com.kkoch.admin.domain.notice.repository.dto.NoticeSearchCond;
import com.kkoch.admin.domain.notice.repository.response.NoticeDetailResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class NoticeQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private NoticeQueryRepository noticeQueryRepository;

    @Autowired
    private NoticeRepository noticeRepository;

    @DisplayName("검색 조건을 입력 받아 공지사항 ID 목록을 조회한다.")
    @Test
    void findAllIdByCond() {
        //given
        Notice notice1 = createNotice(false, "점검 공지사항");
        Notice notice2 = createNotice(false, "서비스 점검");
        Notice notice3 = createNotice(false, "서비스 점검 공지사항");
        Notice notice4 = createNotice(true, "서비스 점검 공지사항");

        PageRequest pageRequest = PageRequest.of(0, 10);
        NoticeSearchCond cond = NoticeSearchCond.builder()
            .keyword("점검")
            .build();

        //when
        List<Integer> noticeIds = noticeQueryRepository.findAllIdByCond(cond, pageRequest);

        //then
        assertThat(noticeIds).hasSize(3)
            .containsExactly(notice3.getId(), notice2.getId(), notice1.getId());
    }

    @DisplayName("공지사항 ID 목록을 입력 받아 공지사항 목록을 조회한다.")
    @Test
    void findAllByIdIn() {
        //given
        Notice notice1 = createNotice(false, "점검 공지사항");
        Notice notice2 = createNotice(false, "서비스 점검");
        Notice notice3 = createNotice(false, "서비스 점검 공지사항");
        List<Integer> noticeIds = List.of(notice3.getId(), notice2.getId(), notice1.getId());

        //when
        List<NoticeResponse> content = noticeQueryRepository.findAllByIdIn(noticeIds);

        //then
        assertThat(content).hasSize(3)
            .extracting("noticeId", "title")
            .containsExactly(
                tuple(notice3.getId(), "서비스 점검 공지사항"),
                tuple(notice2.getId(), "서비스 점검"),
                tuple(notice1.getId(), "점검 공지사항")
            );
    }

    @DisplayName("검색 조건을 입력 받아 공지사항 총 갯수를 조회한다.")
    @Test
    void countByCond() {
        //given
        Notice notice1 = createNotice(false, "점검 공지사항");
        Notice notice2 = createNotice(false, "서비스 점검");
        Notice notice3 = createNotice(false, "서비스 점검 공지사항");
        Notice notice4 = createNotice(true, "서비스 점검 공지사항");

        NoticeSearchCond cond = NoticeSearchCond.builder()
            .keyword("점검")
            .build();

        //when
        int total = noticeQueryRepository.countByCond(cond);

        //then
        assertThat(total).isEqualTo(3);
    }

    @DisplayName("공지사항 ID를 입력 받아 공지사항을 조회한다.")
    @Test
    void findById() {
        //given
        Notice notice = createNotice(false, "점검 공지사항");

        //when
        Optional<NoticeDetailResponse> content = noticeQueryRepository.findById(notice.getId());

        //then
        assertThat(content).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("noticeId", notice.getId())
            .hasFieldOrPropertyWithValue("title", "점검 공지사항");
    }

    @DisplayName("삭제된 공지사항 조회시 빈 객체를 반환한다.")
    @Test
    void findByIdIsDeleted() {
        //given
        Notice notice = createNotice(true, "점검 공지사항");

        //when
        Optional<NoticeDetailResponse> content = noticeQueryRepository.findById(notice.getId());

        //then
        assertThat(content).isEmpty();
    }

    @DisplayName("입력 받은 공지사항 ID와 일치하는 공지사항이 없다면 빈 객체를 반환한다.")
    @Test
    void findByIdWithoutNotice() {
        //given //when
        Optional<NoticeDetailResponse> content = noticeQueryRepository.findById(1);

        //then
        assertThat(content).isEmpty();
    }

    private Notice createNotice(boolean isDeleted, String title) {
        Notice notice = Notice.builder()
            .isDeleted(isDeleted)
            .createdBy(1)
            .lastModifiedBy(1)
            .title(title)
            .content("notice content")
            .build();
        return noticeRepository.save(notice);
    }
}