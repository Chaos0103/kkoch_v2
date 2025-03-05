package com.ssafy.boardservice.api.service.notice;

import com.ssafy.boardservice.domain.notice.repository.NoticeQueryRepository;
import com.ssafy.boardservice.domain.notice.repository.response.NoticeDisplayResponse;
import com.ssafy.boardservice.domain.notice.repository.response.NoticeListDisplayResponse;
import com.ssafy.common.api.ListResponse;
import com.ssafy.common.api.PageResponse;
import com.ssafy.common.global.exception.NoticeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeQueryService {

    private final NoticeQueryRepository noticeQueryRepository;

    public PageResponse<NoticeListDisplayResponse> searchNotFixedNotices(String keyword, LocalDateTime currentDateTime, Pageable pageable) {
        List<NoticeListDisplayResponse> content = noticeQueryRepository.findPagingByTitleContainingAndNotFixed(keyword, currentDateTime, pageable);

        int total = noticeQueryRepository.countsByTitleContainingAndNotFixed(keyword, currentDateTime);

        return PageResponse.create(content, pageable, total);
    }

    public ListResponse<NoticeListDisplayResponse> searchFixedNotices(LocalDateTime currentDateTime) {
        List<NoticeListDisplayResponse> content = noticeQueryRepository.findAllByFixed(currentDateTime);

        return ListResponse.of(content);
    }

    public NoticeDisplayResponse searchNotice(int noticeId) {
        return noticeQueryRepository.findDisplayById(noticeId)
            .orElseThrow(() -> NoticeException.notFound(noticeId));
    }
}
