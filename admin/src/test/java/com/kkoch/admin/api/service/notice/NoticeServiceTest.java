package com.kkoch.admin.api.service.notice;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.api.service.notice.dto.NoticeCreateServiceRequest;
import com.kkoch.admin.api.service.notice.dto.NoticeModifyServiceRequest;
import com.kkoch.admin.domain.admin.Admin;
import com.kkoch.admin.domain.admin.repository.AdminRepository;
import com.kkoch.admin.domain.notice.Notice;
import com.kkoch.admin.domain.notice.repository.NoticeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class NoticeServiceTest extends IntegrationTestSupport {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private AdminRepository adminRepository;

    @DisplayName("공지사항 정보를 입력 받아 신규 공지사항을 등록한다.")
    @Test
    void createNotice() {
        //given


        //when

        //then

    }

    @DisplayName("[공지사항 수정]")
    @Test
    void modifyNotice() {
//        Admin admin = insertAdmin();
//        Notice notice = insertNotice(admin, true, "공지 사항", "공지 내용");
        NoticeModifyServiceRequest dto = NoticeModifyServiceRequest.builder()
                .title("수정제목")
                .content("내용수정")
                .build();

//        Long noticeId = noticeService.modifyNotice(notice.getId(), dto);

//        Optional<Notice> findNotice = noticeRepository.findById(noticeId);

//        assertThat(findNotice).isPresent();
//        assertThat(findNotice.get().getTitle()).isEqualTo("수정제목");
    }

    @DisplayName("[공지사항 삭제]")
    @Test
    void removeNotice() {
//        Admin admin = insertAdmin();
//        Notice notice = insertNotice(admin, true, "공지 사항", "공지 내용");

//        Long noticeId = noticeService.removeNotice(notice.getId());

//        Optional<Notice> findNotice = noticeRepository.findById(noticeId);
//        Notice setNotice = findNotice.get();

//        assertThat(setNotice.isActive()).isFalse();

    }

    private Admin createAdmin() {
        return null;
    }

    private Notice insertNotice(Admin admin, boolean active, String title, String content) {
        return null;
    }
}