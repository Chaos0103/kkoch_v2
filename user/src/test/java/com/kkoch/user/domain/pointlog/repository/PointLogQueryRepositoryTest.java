package com.kkoch.user.domain.pointlog.repository;

import com.kkoch.user.IntegrationTestSupport;
import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.member.Point;
import com.kkoch.user.domain.member.repository.MemberRepository;
import com.kkoch.user.domain.pointlog.Bank;
import com.kkoch.user.domain.pointlog.PointLog;
import com.kkoch.user.domain.pointlog.PointStatus;
import com.kkoch.user.domain.pointlog.repository.response.PointLogResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class PointLogQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private PointLogQueryRepository pointLogQueryRepository;

    @Autowired
    private PointLogRepository pointLogRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원 고유키를 입력 받아 포인트 내역 ID 목록을 조회한다.")
    @Test
    void findAllIdByMemberKey() {
        //given
        Member member = createMember();
        PointLog pointLog1 = createPointLog(member, false);
        PointLog pointLog2 = createPointLog(member, true);

        PageRequest page = PageRequest.of(0, 10);

        //when
        List<Long> pointLogIds = pointLogQueryRepository.findAllIdByMemberKey(member.getMemberKey(), page);

        //then
        assertThat(pointLogIds).hasSize(1)
            .containsExactly(pointLog1.getId());
    }

    @DisplayName("포인트 내역 ID 목록으로 포인트 내역 목록을 조회한다.")
    @Test
    void findAllByIdIn() {
        //given
        Member member = createMember();
        PointLog pointLog1 = createPointLog(member, false);
        PointLog pointLog2 = createPointLog(member, true);

        List<Long> pointLogIds = List.of(pointLog1.getId());

        //when
        List<PointLogResponse> content = pointLogQueryRepository.findAllByIdIn(pointLogIds);

        //then
        assertThat(content).hasSize(1)
            .extracting("pointLogId", "bank", "amount", "status")
            .containsExactly(
                tuple(pointLog1.getId(), Bank.SHINHAN, 10000, PointStatus.CHARGE)
            );
    }

    @DisplayName("회원 고유키를 입력 받아 포인트 내역 전체 갯수를 조회한다.")
    @Test
    void countByMemberKey() {
        //given
        Member member = createMember();
        PointLog pointLog1 = createPointLog(member, false);
        PointLog pointLog2 = createPointLog(member, true);

        //when
        int count = pointLogQueryRepository.countByMemberKey(member.getMemberKey());

        //then
        assertThat(count).isEqualTo(1);
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

    private PointLog createPointLog(Member member, boolean isDeleted) {
        PointLog pointLog = PointLog.builder()
            .isDeleted(isDeleted)
            .bank(Bank.SHINHAN)
            .amount(10000)
            .status(PointStatus.CHARGE)
            .member(member)
            .build();
        return pointLogRepository.save(pointLog);
    }
}