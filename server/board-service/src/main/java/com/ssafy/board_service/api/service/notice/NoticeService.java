package com.ssafy.board_service.api.service.notice;

import com.ssafy.board_service.api.ApiResponse;
import com.ssafy.board_service.api.client.MemberIdResponse;
import com.ssafy.board_service.api.client.MemberServiceClient;
import com.ssafy.board_service.api.service.notice.request.NoticeCreateServiceRequest;
import com.ssafy.board_service.api.service.notice.response.NoticeCreateResponse;
import com.ssafy.board_service.domain.notice.Notice;
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
    private final MemberServiceClient memberServiceClient;

    public NoticeCreateResponse createNotice(LocalDateTime currentDateTime, NoticeCreateServiceRequest request) {
        Long memberId = findMemberId();

        Notice notice = request.toEntity(memberId, currentDateTime);
        Notice savedNotice = noticeRepository.save(notice);

        return NoticeCreateResponse.of(savedNotice, currentDateTime);
    }

    private Long findMemberId() {
        ApiResponse<MemberIdResponse> response = memberServiceClient.searchMemberId();
        return response.getData().getMemberId();
    }
}
