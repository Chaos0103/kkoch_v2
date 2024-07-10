package com.kkoch.user.api.service.pointlog;

import com.kkoch.user.IntegrationTestSupport;
import com.kkoch.user.api.PageResponse;
import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.member.Point;
import com.kkoch.user.domain.member.repository.MemberRepository;
import com.kkoch.user.domain.pointlog.Bank;
import com.kkoch.user.domain.pointlog.PointLog;
import com.kkoch.user.domain.pointlog.PointStatus;
import com.kkoch.user.domain.pointlog.repository.PointLogRepository;
import com.kkoch.user.domain.pointlog.repository.response.PointLogResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class PointLogQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private PointLogQueryService pointLogQueryService;

    @Autowired
    private PointLogRepository pointLogRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원 고유키를 입력 받아 포인트 내역 목록을 조회한다.")
    @Test
    void getPointLogs() {
        //given
        Member member = createMember();
        PointLog pointLog1 = createPointLog(member, false, PointStatus.CHARGE);
        PointLog pointLog2 = createPointLog(member, false, PointStatus.RETURN);
        PointLog pointLog3 = createPointLog(member, false, PointStatus.PAYMENT);
        PointLog pointLog4 = createPointLog(member, true, PointStatus.CHARGE);

        PageRequest page = PageRequest.of(0, 10);

        //when
        PageResponse<PointLogResponse> response = pointLogQueryService.getPointLogs(member.getMemberKey(), page);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("currentPage", 1)
            .hasFieldOrPropertyWithValue("size", 10)
            .hasFieldOrPropertyWithValue("isFirst", true)
            .hasFieldOrPropertyWithValue("isLast", true);

        assertThat(response.getContent()).hasSize(3)
            .extracting("pointLogId", "bank", "amount", "status")
            .containsExactly(
                tuple(pointLog3.getId(), Bank.SHINHAN, 10000, PointStatus.PAYMENT),
                tuple(pointLog2.getId(), Bank.SHINHAN, 10000, PointStatus.RETURN),
                tuple(pointLog1.getId(), Bank.SHINHAN, 10000, PointStatus.CHARGE)
            );
    }

    private Member createMember() {
        Member member = Member.builder()
            .memberKey(generateMemberKey())
            .email("ssafy@ssafy.com")
            .pwd(passwordEncoder.encode("ssafy1234!"))
            .name("김싸피")
            .tel("010-1234-1234")
            .businessNumber("123-12-12345")
            .point(Point.builder()
                .value(0)
                .build())
            .build();
        return memberRepository.save(member);
    }

    private PointLog createPointLog(Member member, boolean isDeleted, PointStatus status) {
        PointLog pointLog = PointLog.builder()
            .isDeleted(isDeleted)
            .bank(Bank.SHINHAN)
            .amount(10000)
            .status(status)
            .member(member)
            .build();
        return pointLogRepository.save(pointLog);
    }
}