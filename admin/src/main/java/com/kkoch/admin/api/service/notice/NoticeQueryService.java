package com.kkoch.admin.api.service.notice;

import com.kkoch.admin.api.PageResponse;
import com.kkoch.admin.domain.notice.repository.response.NoticeResponse;
import com.kkoch.admin.domain.notice.repository.NoticeQueryRepository;
import com.kkoch.admin.domain.notice.repository.dto.NoticeSearchCond;
import com.kkoch.admin.domain.notice.repository.response.NoticeDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeQueryService {

    private final NoticeQueryRepository noticeQueryRepository;

    public PageResponse<NoticeResponse> searchNotices(NoticeSearchCond cond, Pageable pageable) {
        int total = noticeQueryRepository.countByCond(cond);

        List<Integer> noticeIds = noticeQueryRepository.findAllIdByCond(cond, pageable);
        if (noticeIds.isEmpty()) {
            return PageResponse.empty(pageable, total);
        }

        List<NoticeResponse> content = noticeQueryRepository.findAllByIdIn(noticeIds);

        return PageResponse.create(content, pageable, total);
    }

    public NoticeDetailResponse searchNotice(int noticeId) {
        return noticeQueryRepository.findById(noticeId)
            .orElseThrow(() -> new NoSuchElementException("등록되지 않은 공지사항입니다."));
    }


    public List<NoticeResponse> getAllNotices() {
        return noticeQueryRepository.getAllNotices();
    }

    public NoticeResponse getNotice(Long noticeId) {
        return noticeQueryRepository.getNotice(noticeId)
            .orElseThrow(NoSuchElementException::new);
    }
}
