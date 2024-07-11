package com.kkoch.admin.api.service.notice;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.api.service.notice.dto.NoticeCreateServiceRequest;
import com.kkoch.admin.api.service.notice.dto.NoticeModifyServiceRequest;
import com.kkoch.admin.api.service.notice.response.NoticeCreateResponse;
import com.kkoch.admin.api.service.notice.response.NoticeModifyResponse;
import com.kkoch.admin.api.service.notice.response.NoticeRemoveResponse;
import com.kkoch.admin.domain.admin.Admin;
import com.kkoch.admin.domain.admin.AdminAuth;
import com.kkoch.admin.domain.admin.repository.AdminRepository;
import com.kkoch.admin.domain.notice.Notice;
import com.kkoch.admin.domain.notice.repository.NoticeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class NoticeServiceTest extends IntegrationTestSupport {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private AdminRepository adminRepository;

    @DisplayName("공지사항 정보를 입력 받아 공지사항을 신규 등록한다.")
    @Test
    void createNotice() {
        //given
        Admin admin = createAdmin();

        NoticeCreateServiceRequest request = NoticeCreateServiceRequest.builder()
            .title("notice title")
            .content("notice content")
            .build();

        //when
        NoticeCreateResponse response = noticeService.createNotice(admin.getId(), request);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("title", "notice title");

        Optional<Notice> notice = noticeRepository.findById(response.getNoticeId());
        assertThat(notice).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("title", "notice title")
            .hasFieldOrPropertyWithValue("content", "notice content")
            .hasFieldOrPropertyWithValue("createdBy", admin.getId())
            .hasFieldOrPropertyWithValue("lastModifiedBy", admin.getId());
    }

    @DisplayName("수정할 공지사항 정보를 입력 받아 공지사항을 수정한다.")
    @Test
    void modifyNotice() {
        //given
        Admin admin = createAdmin();
        Notice notice = createNotice(admin);

        NoticeModifyServiceRequest request = NoticeModifyServiceRequest.builder()
            .title("modify notice title")
            .content("modify notice content")
            .build();

        //when
        NoticeModifyResponse response = noticeService.modifyNotice(admin.getId(), notice.getId(), request);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("noticeId", notice.getId());

        Optional<Notice> findNotice = noticeRepository.findById(notice.getId());
        assertThat(findNotice).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("title", "modify notice title")
            .hasFieldOrPropertyWithValue("content", "modify notice content");
    }

    @DisplayName("공지사항 ID를 입력 받아 공지사항을 삭제한다.")
    @Test
    void removeNotice() {
        //given
        Admin admin = createAdmin();
        Notice notice = createNotice(admin);

        //when
        NoticeRemoveResponse response = noticeService.removeNotice(admin.getId(), notice.getId());

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("noticeId", notice.getId());

        Optional<Notice> findNotice = noticeRepository.findById(notice.getId());
        assertThat(findNotice).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("isDeleted", true);
    }

    private Admin createAdmin() {
        Admin admin = Admin.builder()
            .isDeleted(false)
            .email("admin@ssafy.com")
            .pwd(passwordEncoder.encode("ssafy1234!"))
            .name("김관리")
            .tel("010-1234-1234")
            .auth(AdminAuth.MASTER)
            .build();
        return adminRepository.save(admin);
    }

    private Notice createNotice(Admin admin) {
        Notice notice = Notice.builder()
            .isDeleted(false)
            .createdBy(admin.getId())
            .lastModifiedBy(admin.getId())
            .title("notice title")
            .content("notice content")
            .build();
        return noticeRepository.save(notice);
    }
}