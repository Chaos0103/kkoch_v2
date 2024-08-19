package com.ssafy.board_service.api.service.notice;

import com.ssafy.board_service.api.PageResponse;
import com.ssafy.board_service.domain.notice.repository.NoticeQueryRepository;
import com.ssafy.board_service.domain.notice.repository.response.NoticeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeQueryService {

    private final NoticeQueryRepository noticeQueryRepository;

    public PageResponse<NoticeResponse> searchNotFixedNotices(int pageNumber, String keyword, LocalDateTime currentDateTime) {
        return null;
    }
}
