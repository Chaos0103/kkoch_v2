package com.ssafy.boardservice.api.service.notice;

import com.ssafy.boardservice.api.client.UserServiceClient;
import com.ssafy.boardservice.api.service.notice.request.NoticeCreateServiceRequest;
import com.ssafy.boardservice.api.service.notice.request.NoticeModifyServiceRequest;
import com.ssafy.boardservice.api.service.notice.response.NoticeCreateResponse;
import com.ssafy.boardservice.api.service.notice.response.NoticeModifyResponse;
import com.ssafy.boardservice.api.service.notice.response.NoticeRemoveResponse;
import com.ssafy.boardservice.domain.notice.Notice;
import com.ssafy.boardservice.domain.notice.repository.NoticeRepository;
import com.ssafy.boardservice.domain.notice.vo.NoticeFixedDateTime;
import com.ssafy.boardservice.domain.notice.vo.NoticeTitle;
import com.ssafy.common.global.exception.MemberException;
import com.ssafy.common.global.exception.NoticeException;
import com.ssafy.common.global.exception.code.ErrorCode;
import common.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

class NoticeServiceTest extends IntegrationTestSupport {

    @Autowired
    NoticeService noticeService;

    @Autowired
    NoticeRepository noticeRepository;

    @MockitoBean
    UserServiceClient userServiceClient;

    @DisplayName("공지사항 등록 시 회원 정보가 존재하지 않으면 예외가 발생한다.")
    @Test
    void createNoticeWhenNotFoundMember() {
        //given
        LocalDateTime current = LocalDateTime.of(2025, 1, 1, 0, 0, 0);

        given(userServiceClient.searchMemberId())
            .willThrow(MemberException.of(ErrorCode.MEMBER_NOT_FOUND));

        NoticeCreateServiceRequest request = NoticeCreateServiceRequest.builder()
            .title("신규 공지사항 제목입니다.")
            .content("신규 공지사항 내용입니다.")
            .toFixedDateTime(LocalDateTime.of(2025, 1, 30, 0, 0, 0))
            .build();

        //when
        assertThatThrownBy(() -> noticeService.createNotice(request, current))
            .isInstanceOf(NoticeException.class)
            .hasMessage("회원을 찾을 수 없습니다.");

        //then
        assertThat(noticeRepository.findAll()).isEmpty();
    }

    @DisplayName("공지사항 정보를 입력 받아 신규 공지사항을 등록한다.")
    @Test
    void createNotice() {
        //given
        LocalDateTime current = LocalDateTime.of(2025, 1, 1, 0, 0, 0);

        given(userServiceClient.searchMemberId())
            .willReturn(1L);

        NoticeCreateServiceRequest request = NoticeCreateServiceRequest.builder()
            .title("신규 공지사항 제목입니다.")
            .content("신규 공지사항 내용입니다.")
            .toFixedDateTime(LocalDateTime.of(2025, 1, 30, 0, 0, 0))
            .build();

        //when
        NoticeCreateResponse noticeCreateResponse = noticeService.createNotice(request, current);

        //then
        assertThat(noticeCreateResponse)
            .isNotNull()
            .satisfies(response -> {
                assertThat(response.getNoticeId()).isNotNull();
                assertThat(response.getTitle()).isEqualTo("신규 공지사항 제목입니다.");
                assertThat(response.getIsFixed()).isTrue();
                assertThat(response.getCreatedDateTime()).isNotNull();
            });

        assertThat(noticeRepository.findAll()).hasSize(1);
    }

    @DisplayName("공지사항 수정 시 회원 정보가 존재하지 않으면 예외가 발생한다.")
    @Test
    void modifyNoticeWhenNotFoundMember() {
        //given
        LocalDateTime current = LocalDateTime.of(2025, 1, 1, 0, 0, 0);

        given(userServiceClient.searchMemberId())
            .willThrow(MemberException.of(ErrorCode.MEMBER_NOT_FOUND));

        NoticeModifyServiceRequest request = NoticeModifyServiceRequest.builder()
            .title("수정된 공지사항 제목입니다.")
            .content("수정된 공지사항 내용입니다.")
            .toFixedDateTime(LocalDateTime.of(2025, 1, 15, 18, 0, 0))
            .build();

        Notice notice = createNotice(current);
        int validNoticeId = notice.getId();

        //when
        assertThatThrownBy(() -> noticeService.modifyNotice(validNoticeId, request, current))
            .isInstanceOf(NoticeException.class)
            .hasMessage("회원을 찾을 수 없습니다.");

        //then
        assertThat(noticeRepository.findAll()).hasSize(1);
    }

    @DisplayName("공지사항 수정 시 입력 받은 공지사항 ID와 일치하는 공지사항이 존재하지 않으면 예외가 발생한다.")
    @Test
    void modifyNoticeWhenNotFoundNotice() {
        //given
        LocalDateTime current = LocalDateTime.of(2025, 1, 1, 0, 0, 0);

        given(userServiceClient.searchMemberId())
            .willReturn(1L);

        NoticeModifyServiceRequest request = NoticeModifyServiceRequest.builder()
            .title("수정된 공지사항 제목입니다.")
            .content("수정된 공지사항 내용입니다.")
            .toFixedDateTime(LocalDateTime.of(2025, 1, 15, 18, 0, 0))
            .build();

        int invalidNoticeId = 1;

        //when
        assertThatThrownBy(() -> noticeService.modifyNotice(invalidNoticeId, request, current))
            .isInstanceOf(NoticeException.class)
            .hasMessage("공지사항을 찾을 수 없습니다.");

        //then
        assertThat(noticeRepository.findAll()).isEmpty();
    }

    @DisplayName("수정할 공지사항 정보를 입력 받아 공지사항을 수정한다.")
    @Test
    void modifyNotice() {
        //given
        LocalDateTime current = LocalDateTime.of(2025, 1, 1, 0, 0, 0);

        Notice notice = createNotice(current);

        given(userServiceClient.searchMemberId())
            .willReturn(1L);

        NoticeModifyServiceRequest request = NoticeModifyServiceRequest.builder()
            .title("수정된 공지사항 제목입니다.")
            .content("수정된 공지사항 내용입니다.")
            .toFixedDateTime(LocalDateTime.of(2025, 1, 15, 18, 0, 0))
            .build();

        //when
        NoticeModifyResponse noticeModifyResponse = noticeService.modifyNotice(notice.getId(), request, current);

        //then
        assertThat(noticeModifyResponse)
            .isNotNull()
            .satisfies(response -> {
                assertThat(response.getNoticeId()).isEqualTo(notice.getId());
                assertThat(response.getTitle()).isEqualTo("수정된 공지사항 제목입니다.");
                assertThat(response.getIsFixed()).isTrue();
            });
    }

    @DisplayName("공지사항 삭제 시 회원 정보가 존재하지 않으면 예외가 발생한다.")
    @Test
    void removeNoticeWhenNotFoundMember() {
        //given
        LocalDateTime current = LocalDateTime.of(2025, 1, 1, 0, 0, 0);

        given(userServiceClient.searchMemberId())
            .willThrow(NoticeException.of(ErrorCode.MEMBER_NOT_FOUND));

        Notice notice = createNotice(current);
        int validNoticeId = notice.getId();

        //when
        assertThatThrownBy(() -> noticeService.removeNotice(validNoticeId))
            .isInstanceOf(NoticeException.class)
            .hasMessage("회원을 찾을 수 없습니다.");

        //then
        assertThat(noticeRepository.findAll()).hasSize(1);
    }

    @DisplayName("공지사항 삭제 시 입력 받은 공지사항 ID와 일치하는 공지사항이 존재하지 않으면 예외가 발생한다.")
    @Test
    void removeNoticeWhenNotFoundNotice() {
        //given
        given(userServiceClient.searchMemberId())
            .willReturn(1L);

        int invalidNoticeId = 1;

        //when
        assertThatThrownBy(() -> noticeService.removeNotice(invalidNoticeId))
            .isInstanceOf(NoticeException.class)
            .hasMessage("공지사항을 찾을 수 없습니다.");

        //then
        assertThat(noticeRepository.findAll()).isEmpty();
    }

    @DisplayName("공지사항을 삭제한다.")
    @Test
    void removeNotice() {
        //given
        LocalDateTime current = LocalDateTime.of(2025, 1, 1, 0, 0, 0);

        Notice notice = createNotice(current);

        given(userServiceClient.searchMemberId())
            .willReturn(1L);

        //when
        NoticeRemoveResponse noticeRemoveResponse = noticeService.removeNotice(notice.getId());

        //then
        assertThat(noticeRemoveResponse).isNotNull();

        Optional<Notice> findNotice = noticeRepository.findById(notice.getId());
        assertThat(findNotice)
            .isPresent()
            .hasValueSatisfying(n -> assertThat(n.getIsDeleted()).isTrue());
    }

    private Notice createNotice(LocalDateTime current) {
        Notice notice = Notice.builder()
            .isDeleted(false)
            .createdBy(1L)
            .lastModifiedBy(1L)
            .title(NoticeTitle.of("공지사항 제목입니다."))
            .content("공지사항 내용입니다.")
            .toFixedDateTime(NoticeFixedDateTime.of(LocalDateTime.of(2025, 1, 1, 0, 0), current))
            .build();
        return noticeRepository.save(notice);
    }
}