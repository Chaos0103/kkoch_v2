package com.ssafy.boardservice.api.service.notice;

import com.ssafy.boardservice.api.client.UserServiceClient;
import com.ssafy.boardservice.api.service.notice.request.NoticeCreateServiceRequest;
import com.ssafy.boardservice.api.service.notice.request.NoticeModifyServiceRequest;
import com.ssafy.boardservice.api.service.notice.response.NoticeCreateResponse;
import com.ssafy.boardservice.api.service.notice.response.NoticeModifyResponse;
import com.ssafy.boardservice.api.service.notice.response.NoticeRemoveResponse;
import com.ssafy.boardservice.domain.notice.Notice;
import com.ssafy.boardservice.domain.notice.repository.NoticeRepository;
import com.ssafy.common.global.exception.MemberException;
import com.ssafy.common.global.exception.NoticeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final UserServiceClient userServiceClient;

    public NoticeCreateResponse createNotice(NoticeCreateServiceRequest request, LocalDateTime current) {
        Long memberId = getMemberIdOrThrows();

        Notice notice = request.toEntity(memberId, current);
        Notice savedNotice = noticeRepository.save(notice);

        return NoticeCreateResponse.of(savedNotice, current);
    }

    public NoticeModifyResponse modifyNotice(Integer noticeId, NoticeModifyServiceRequest request, LocalDateTime current) {
        Long memberId = getMemberIdOrThrows();

        Notice notice = findByIdOrThrows(noticeId);

        notice.modify(memberId, request.getTitle(), request.getContent(), request.getFixedDateTime(current));

        return NoticeModifyResponse.of(notice, current);
    }

    public NoticeRemoveResponse removeNotice(Integer noticeId) {
        Long memberId = getMemberIdOrThrows();

        Notice notice = findByIdOrThrows(noticeId);

        notice.remove(memberId);

        return NoticeRemoveResponse.of(notice);
    }

    private Long getMemberIdOrThrows() {
        try {
            return userServiceClient.searchMemberId();
        } catch (MemberException e) {
            log.warn(e.getMessage());
            throw NoticeException.of(e.getErrorCode());
        }
    }

    private Notice findByIdOrThrows(Integer noticeId) {
        return noticeRepository.findById(noticeId)
            .orElseThrow(() -> NoticeException.notFound(noticeId));
    }
}
