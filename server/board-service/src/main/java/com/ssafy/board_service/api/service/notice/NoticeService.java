package com.ssafy.board_service.api.service.notice;

import com.ssafy.board_service.api.ApiResponse;
import com.ssafy.board_service.api.client.response.MemberIdResponse;
import com.ssafy.board_service.api.client.MemberServiceClient;
import com.ssafy.board_service.api.service.notice.request.NoticeCreateServiceRequest;
import com.ssafy.board_service.api.service.notice.request.NoticeModifyServiceRequest;
import com.ssafy.board_service.api.service.notice.response.NoticeCreateResponse;
import com.ssafy.board_service.api.service.notice.response.NoticeModifyResponse;
import com.ssafy.board_service.api.service.notice.response.NoticeRemoveResponse;
import com.ssafy.board_service.domain.notice.Notice;
import com.ssafy.board_service.domain.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

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

    public NoticeModifyResponse modifyNotice(int noticeId, LocalDateTime currentDateTime, NoticeModifyServiceRequest request) {
        Long memberId = findMemberId();

        Notice notice = findNoticeById(noticeId);
        request.modifyEntity(notice, memberId, currentDateTime);

        return NoticeModifyResponse.of(notice, currentDateTime);
    }

    public NoticeRemoveResponse removeNotice(int noticeId, LocalDateTime currentDateTime) {
        Long memberId = findMemberId();

        Notice notice = findNoticeById(noticeId);
        notice.remove(memberId);

        return NoticeRemoveResponse.of(notice, currentDateTime);
    }

    private Long findMemberId() {
        ApiResponse<MemberIdResponse> response = memberServiceClient.searchMemberId();
        return response.getData().getMemberId();
    }

    private Notice findNoticeById(int noticeId) {
        return noticeRepository.findById(noticeId)
            .orElseThrow(() -> new NoSuchElementException("등록되지 않은 공지사항입니다."));
    }
}
