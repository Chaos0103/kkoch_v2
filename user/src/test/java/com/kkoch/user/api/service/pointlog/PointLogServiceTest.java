package com.kkoch.user.api.service.pointlog;

import com.kkoch.user.IntegrationTestSupport;
import com.kkoch.user.api.service.pointlog.request.PointLogCreateServiceRequest;
import com.kkoch.user.api.service.pointlog.response.PointLogCreateResponse;
import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.member.Point;
import com.kkoch.user.domain.member.repository.MemberRepository;
import com.kkoch.user.domain.pointlog.Bank;
import com.kkoch.user.domain.pointlog.PointLog;
import com.kkoch.user.domain.pointlog.PointStatus;
import com.kkoch.user.domain.pointlog.repository.PointLogRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class PointLogServiceTest extends IntegrationTestSupport {

    @Autowired
    private PointLogService pointLogService;

    @Autowired
    private PointLogRepository pointLogRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("포인트 내역 정보를 입력 받아 신규 포인트 내역을 등록한다.")
    @Test
    void createPointLog() {
        //given
        Member member = createMember();
        PointLogCreateServiceRequest request = PointLogCreateServiceRequest.builder()
            .bank("SHINHAN")
            .amount(10000)
            .status("CHARGE")
            .build();

        //when
        PointLogCreateResponse response = pointLogService.createPointLog(member.getMemberKey(), request);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("bank", Bank.SHINHAN)
            .hasFieldOrPropertyWithValue("amount", 10000)
            .hasFieldOrPropertyWithValue("status", PointStatus.CHARGE)
            .hasFieldOrPropertyWithValue("balance", 10000L);

        List<PointLog> pointLogs = pointLogRepository.findAll();
        assertThat(pointLogs).hasSize(1);
    }

    private Member createMember() {
        Member member = Member.builder()
            .isDeleted(false)
            .memberKey(UUID.randomUUID().toString())
            .email("ssafy@ssafy.com")
            .pwd(passwordEncoder.encode("ssafy1234!"))
            .name("김싸피")
            .tel("010-1234-1234")
            .businessNumber("123-12-12345")
            .point(Point.builder()
                .value(0)
                .build()
            ).build();
        return memberRepository.save(member);
    }
}