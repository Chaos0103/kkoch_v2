package com.kkoch.admin.api.service.notice;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.api.PageResponse;
import com.kkoch.admin.domain.notice.repository.response.NoticeResponse;
import com.kkoch.admin.domain.notice.Notice;
import com.kkoch.admin.domain.notice.repository.NoticeRepository;
import com.kkoch.admin.domain.notice.repository.dto.NoticeSearchCond;
import com.kkoch.admin.domain.notice.repository.response.NoticeDetailResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.*;

class NoticeQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private NoticeQueryService noticeQueryService;

    @Autowired
    private NoticeRepository noticeRepository;

    @DisplayName("검색 조건을 입력 받아 공지사항 목록을 조회한다.")
    @Test
    void searchNotices() {
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
        PageResponse<NoticeResponse> response = noticeQueryService.searchNotices(cond, pageRequest);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("currentPage", 1)
            .hasFieldOrPropertyWithValue("size", 10)
            .hasFieldOrPropertyWithValue("isFirst", true)
            .hasFieldOrPropertyWithValue("isLast", true);
        assertThat(response.getContent()).hasSize(3)
            .extracting("noticeId", "title")
            .containsExactly(
                tuple(notice3.getId(), "서비스 점검 공지사항"),
                tuple(notice2.getId(), "서비스 점검"),
                tuple(notice1.getId(), "점검 공지사항")
            );
    }

    @DisplayName("검색 조건을 입력 받아 공지사항 목록을 조회한다.")
    @Test
    void searchNoticesIsEmpty() {
        //given
        Notice notice1 = createNotice(false, "점검 공지사항");
        Notice notice2 = createNotice(false, "서비스 점검");
        Notice notice3 = createNotice(false, "서비스 점검 공지사항");
        Notice notice4 = createNotice(true, "서비스 점검 공지사항");

        PageRequest pageRequest = PageRequest.of(1, 10);
        NoticeSearchCond cond = NoticeSearchCond.builder()
            .keyword("점검")
            .build();

        //when
        PageResponse<NoticeResponse> response = noticeQueryService.searchNotices(cond, pageRequest);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("currentPage", 2)
            .hasFieldOrPropertyWithValue("size", 10)
            .hasFieldOrPropertyWithValue("isFirst", false)
            .hasFieldOrPropertyWithValue("isLast", true);
        assertThat(response.getContent()).isEmpty();
    }

    @DisplayName("공지사항 ID를 입력 받아 공지사항을 조회한다.")
    @Test
    void searchNotice() {
        //given
        Notice notice = createNotice(false, "점검 공지사항");

        //when
        NoticeDetailResponse response = noticeQueryService.searchNotice(notice.getId());

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("noticeId", notice.getId())
            .hasFieldOrPropertyWithValue("title", notice.getTitle());
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