package com.ssafy.board_service.api.service.notice;

import com.ssafy.board_service.api.PageResponse;
import com.ssafy.board_service.common.util.PageUtils;
import com.ssafy.board_service.domain.notice.repository.NoticeQueryRepository;
import com.ssafy.board_service.domain.notice.repository.response.NoticeDetailResponse;
import com.ssafy.board_service.domain.notice.repository.response.NoticeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeQueryService {

    private final NoticeQueryRepository noticeQueryRepository;

    public PageResponse<NoticeResponse> searchNotFixedNotices(int pageNumber, String keyword, LocalDateTime currentDateTime) {
        Pageable pageable = PageUtils.of(pageNumber);

        List<NoticeResponse> content = noticeQueryRepository.findNotFixedAllByNoticeTitleContaining(keyword, currentDateTime, pageable);

        int total = noticeQueryRepository.countNotFixedByNoticeTitleContaining(keyword, currentDateTime);

        return PageResponse.create(content, pageable, total);
    }

    public FixedNoticeResponse searchFixedNotices(LocalDateTime currentDateTime) {
        List<NoticeResponse> content = noticeQueryRepository.findFixedAll(currentDateTime);

        return FixedNoticeResponse.of(content);
    }

    public NoticeDetailResponse searchNotice(int noticeId) {
        return noticeQueryRepository.findDetailById(noticeId)
            .orElseThrow(() -> new NoSuchElementException("등록되지 않는 공지사항입니다."));
    }
}
