package com.ssafy.board_service.api.service.notice;

import com.ssafy.board_service.IntegrationTestSupport;
import com.ssafy.board_service.api.ApiResponse;
import com.ssafy.board_service.api.client.MemberIdResponse;
import com.ssafy.board_service.api.client.MemberServiceClient;
import com.ssafy.board_service.api.service.notice.request.NoticeCreateServiceRequest;
import com.ssafy.board_service.api.service.notice.request.NoticeModifyServiceRequest;
import com.ssafy.board_service.api.service.notice.response.NoticeCreateResponse;
import com.ssafy.board_service.api.service.notice.response.NoticeModifyResponse;
import com.ssafy.board_service.domain.notice.Notice;
import com.ssafy.board_service.domain.notice.repository.NoticeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class NoticeServiceTest extends IntegrationTestSupport {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private NoticeRepository noticeRepository;

    @MockBean
    private MemberServiceClient memberServiceClient;

    @DisplayName("공지사항 등록시 고정일시가 null이라면 고정을 하지 않는다.")
    @Test
    void createNoticeWithoutToFixedDateTime() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 8, 10, 9, 0, 0);

        NoticeCreateServiceRequest request = NoticeCreateServiceRequest.builder()
            .title("서비스 점검 안내")
            .content("2024.08.15 00:00 ~ 07:00 서비스 점검 예정입니다.")
            .toFixedDateTime(null)
            .build();

        MemberIdResponse memberId = MemberIdResponse.builder()
            .memberId(1L)
            .build();
        ApiResponse<MemberIdResponse> clientResponse = ApiResponse.ok(memberId);

        given(memberServiceClient.searchMemberId())
            .willReturn(clientResponse);

        //when
        NoticeCreateResponse response = noticeService.createNotice(currentDateTime, request);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("title", "서비스 점검 안내")
            .hasFieldOrPropertyWithValue("isFixed", false)
            .hasFieldOrPropertyWithValue("createdDateTime", LocalDateTime.of(2024, 8, 10, 9, 0, 0));

        List<Notice> notices = noticeRepository.findAll();
        assertThat(notices).hasSize(1);
    }

    @DisplayName("공지사항 정보를 입력 받아 공지사항을 등록한다.")
    @Test
    void createNotice() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 8, 10, 9, 0, 0);

        NoticeCreateServiceRequest request = NoticeCreateServiceRequest.builder()
            .title("서비스 점검 안내")
            .content("2024.08.15 00:00 ~ 07:00 서비스 점검 예정입니다.")
            .toFixedDateTime("2024-08-15T07:00:00")
            .build();

        MemberIdResponse memberId = MemberIdResponse.builder()
            .memberId(1L)
            .build();
        ApiResponse<MemberIdResponse> clientResponse = ApiResponse.ok(memberId);

        given(memberServiceClient.searchMemberId())
            .willReturn(clientResponse);

        //when
        NoticeCreateResponse response = noticeService.createNotice(currentDateTime, request);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("title", "서비스 점검 안내")
            .hasFieldOrPropertyWithValue("isFixed", true)
            .hasFieldOrPropertyWithValue("createdDateTime", LocalDateTime.of(2024, 8, 10, 9, 0, 0));

        List<Notice> notices = noticeRepository.findAll();
        assertThat(notices).hasSize(1);
    }

    @DisplayName("공지사항 수정시 고정일시가 null이라면 현재 시간을 입력한다.")
    @Test
    void modifyNoticeWithoutToFixedDateTime() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 8, 15, 7, 0, 0);

        Notice notice = createNotice(1L);

        NoticeModifyServiceRequest request = NoticeModifyServiceRequest.builder()
            .title("(수정)서비스 점검 안내")
            .content("2024.08.15 02:00 ~ 07:00 서비스 점검 예정입니다.")
            .toFixedDateTime(null)
            .build();

        MemberIdResponse memberId = MemberIdResponse.builder()
            .memberId(2L)
            .build();
        ApiResponse<MemberIdResponse> clientResponse = ApiResponse.ok(memberId);

        given(memberServiceClient.searchMemberId())
            .willReturn(clientResponse);

        //when
        NoticeModifyResponse response = noticeService.modifyNotice(notice.getId(), currentDateTime, request);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("title", "(수정)서비스 점검 안내")
            .hasFieldOrPropertyWithValue("isFixed", false)
            .hasFieldOrPropertyWithValue("modifiedDateTime", currentDateTime);

        Optional<Notice> findNotice = noticeRepository.findById(notice.getId());
        assertThat(findNotice).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("createdBy", 1L)
            .hasFieldOrPropertyWithValue("lastModifiedBy", 2L)
            .hasFieldOrPropertyWithValue("noticeTitle", "(수정)서비스 점검 안내")
            .hasFieldOrPropertyWithValue("noticeContent", "2024.08.15 02:00 ~ 07:00 서비스 점검 예정입니다.")
            .hasFieldOrPropertyWithValue("toFixedDateTime", currentDateTime);
    }

    @DisplayName("공지사항 정보를 입력 받아 공지사항을 수정한다.")
    @Test
    void modifyNotice() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 8, 15, 7, 0, 0);

        Notice notice = createNotice(1L);

        NoticeModifyServiceRequest request = NoticeModifyServiceRequest.builder()
            .title("(수정)서비스 점검 안내")
            .content("2024.08.15 02:00 ~ 07:00 서비스 점검 예정입니다.")
            .toFixedDateTime("2024-08-15T07:00:00")
            .build();

        MemberIdResponse memberId = MemberIdResponse.builder()
            .memberId(2L)
            .build();
        ApiResponse<MemberIdResponse> clientResponse = ApiResponse.ok(memberId);

        given(memberServiceClient.searchMemberId())
            .willReturn(clientResponse);

        //when
        NoticeModifyResponse response = noticeService.modifyNotice(notice.getId(), currentDateTime, request);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("title", "(수정)서비스 점검 안내")
            .hasFieldOrPropertyWithValue("isFixed", false)
            .hasFieldOrPropertyWithValue("modifiedDateTime", currentDateTime);

        Optional<Notice> findNotice = noticeRepository.findById(notice.getId());
        assertThat(findNotice).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("createdBy", 1L)
            .hasFieldOrPropertyWithValue("lastModifiedBy", 2L)
            .hasFieldOrPropertyWithValue("noticeTitle", "(수정)서비스 점검 안내")
            .hasFieldOrPropertyWithValue("noticeContent", "2024.08.15 02:00 ~ 07:00 서비스 점검 예정입니다.");
    }

    private Notice createNotice(long memberId) {
        Notice notice = Notice.builder()
            .isDeleted(false)
            .createdBy(memberId)
            .lastModifiedBy(memberId)
            .noticeTitle("서비스 점검 안내")
            .noticeContent("<h1>2024.08.15 00:00 ~ 07:00 서비스 점검 예정입니다.</h1>")
            .toFixedDateTime(LocalDateTime.of(2024, 8, 15, 7, 0))
            .build();
        return noticeRepository.save(notice);
    }
}