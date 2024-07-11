package com.kkoch.admin.api.service.notice;

import com.kkoch.admin.api.service.notice.dto.NoticeCreateServiceRequest;
import com.kkoch.admin.api.service.notice.dto.NoticeModifyServiceRequest;
import com.kkoch.admin.domain.admin.Admin;
import com.kkoch.admin.domain.admin.repository.AdminRepository;
import com.kkoch.admin.domain.notice.Notice;
import com.kkoch.admin.domain.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final AdminRepository adminRepository;

    public Long createNotice(Integer adminId, NoticeCreateServiceRequest request) {
        Admin admin = findAdminBy(adminId);

        Notice notice = request.toEntity(admin);
        Notice savedNotice = noticeRepository.save(notice);

        return savedNotice.getId();
    }

    public Long modifyNotice(int adminId, Long noticeId, NoticeModifyServiceRequest request) {
        Admin admin = findAdminBy(adminId);
        Notice notice = findNoticeBy(noticeId);

        notice.edit(request.getTitle(), request.getContent(), admin);

        return notice.getId();
    }

    public Long removeNotice(int adminId, Long noticeId) {
        Admin admin = findAdminBy(adminId);
        Notice notice = findNoticeBy(noticeId);

        notice.remove(admin);

        return notice.getId();
    }

    private Admin findAdminBy(Integer adminId) {
        return adminRepository.findById(adminId)
            .orElseThrow(() -> new NoSuchElementException("등록되지 않은 관계자입니다."));
    }

    private Notice findNoticeBy(Long noticeId) {
        return noticeRepository.findById(noticeId)
            .orElseThrow(() -> new NoSuchElementException("등록되지 않은 공지사항입니다."));
    }
}
