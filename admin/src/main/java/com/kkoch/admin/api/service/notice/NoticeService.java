package com.kkoch.admin.api.service.notice;

import com.kkoch.admin.api.service.notice.dto.NoticeCreateServiceRequest;
import com.kkoch.admin.api.service.notice.dto.NoticeModifyServiceRequest;
import com.kkoch.admin.api.service.notice.response.NoticeCreateResponse;
import com.kkoch.admin.api.service.notice.response.NoticeModifyResponse;
import com.kkoch.admin.api.service.notice.response.NoticeRemoveResponse;
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

    public NoticeCreateResponse createNotice(int adminId, NoticeCreateServiceRequest request) {
        Admin admin = findAdminBy(adminId);

        Notice notice = request.toEntity(admin);
        Notice savedNotice = noticeRepository.save(notice);

        return NoticeCreateResponse.of(savedNotice);
    }

    public NoticeModifyResponse modifyNotice(int adminId, int noticeId, NoticeModifyServiceRequest request) {
        Admin admin = findAdminBy(adminId);
        Notice notice = findNoticeBy(noticeId);

        notice.edit(request.getTitle(), request.getContent(), admin);

        return NoticeModifyResponse.of(notice);
    }

    public NoticeRemoveResponse removeNotice(int adminId, int noticeId) {
        Admin admin = findAdminBy(adminId);
        Notice notice = findNoticeBy(noticeId);

        notice.remove(admin);

        return NoticeRemoveResponse.of(notice);
    }

    private Admin findAdminBy(int adminId) {
        return adminRepository.findById(adminId)
            .orElseThrow(() -> new NoSuchElementException("등록되지 않은 관리자입니다."));
    }

    private Notice findNoticeBy(int noticeId) {
        return noticeRepository.findById(noticeId)
            .orElseThrow(() -> new NoSuchElementException("등록되지 않은 공지사항입니다."));
    }
}
