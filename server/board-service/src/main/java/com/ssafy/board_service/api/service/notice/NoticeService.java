package com.ssafy.board_service.api.service.notice;

import com.ssafy.board_service.api.service.notice.request.NoticeCreateServiceRequest;
import com.ssafy.board_service.api.service.notice.response.NoticeCreateResponse;
import com.ssafy.board_service.domain.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeCreateResponse createNotice(String memberKey, LocalDateTime currentDateTime, NoticeCreateServiceRequest request) {
        return null;
    }
}
