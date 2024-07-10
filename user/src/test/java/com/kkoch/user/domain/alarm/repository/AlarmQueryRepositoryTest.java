package com.kkoch.user.domain.alarm.repository;

import com.kkoch.user.IntegrationTestSupport;
import com.kkoch.user.api.controller.alarm.response.AlarmResponse;
import com.kkoch.user.domain.alarm.Alarm;
import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.member.Point;
import com.kkoch.user.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class AlarmQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private AlarmQueryRepository alarmQueryRepository;

    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원의 모든 알람을 조회할 수 있다.")
    @Test
    void findTop10ByMemberKey() {
        //given
        Member member = createMember();
        Alarm alarm1 = createAlarm(member, false, true);
        Alarm alarm2 = createAlarm(member, false, false);
        Alarm alarm3 = createAlarm(member, true, true);
        Alarm alarm4 = createAlarm(member, true, false);

        //when
        List<AlarmResponse> responses = alarmQueryRepository.findTop10ByMemberKey(member.getMemberKey());

        //then
        assertThat(responses).hasSize(2)
            .extracting("alarmId", "isOpened")
            .containsExactly(
                tuple(alarm2.getId(), false),
                tuple(alarm1.getId(), true)
            );
    }

    private Member createMember() {
        Member member = Member.builder()
            .isDeleted(false)
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

    private Alarm createAlarm(Member member, boolean isDeleted, boolean isOpened) {
        Alarm alarm = Alarm.builder()
            .isDeleted(isDeleted)
            .content("alarm content")
            .isOpened(isOpened)
            .member(member)
            .build();
        return alarmRepository.save(alarm);
    }
}