package com.kkoch.user.api.service.alarm;

import com.kkoch.user.IntegrationTestSupport;
import com.kkoch.user.api.service.alarm.request.AlarmCreateServiceRequest;
import com.kkoch.user.api.service.alarm.response.AlarmCreateResponse;
import com.kkoch.user.domain.alarm.Alarm;
import com.kkoch.user.domain.alarm.repository.AlarmRepository;
import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.member.Point;
import com.kkoch.user.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class AlamServiceTest extends IntegrationTestSupport {

    @Autowired
    private AlamService alamService;

    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("알림 정보를 입력 받아 신규 알림을 등록한다.")
    @Test
    void createAlarm() {
        //given
        Member member = createMember();
        AlarmCreateServiceRequest request = AlarmCreateServiceRequest.builder()
            .content("거래 예약한 물품이 낙찰되셨습니다.")
            .build();

        //when
        AlarmCreateResponse response = alamService.createAlarm(member.getMemberKey(), request);

        //then
        assertThat(response).isNotNull();

        Optional<Alarm> alarm = alarmRepository.findById(response.getAlarmId());
        assertThat(alarm).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("content", "거래 예약한 물품이 낙찰되셨습니다.");
    }

    @DisplayName("회원 고유키를 입력 받아 열지 않은 알림을 모두 연다.")
    @Test
    void openAllAlarm() {
        //given
        Member member = createMember();
        Alarm alarm1 = createAlarm(member, false);
        Alarm alarm2 = createAlarm(member, false);
        Alarm alarm3 = createAlarm(member, true);

        //when
        int openedCount = alamService.openAllAlarm(member.getMemberKey());

        //then
        assertThat(openedCount).isEqualTo(2);
        List<Alarm> alarms = alarmRepository.findAll();
        assertThat(alarms)
            .extracting("isOpened")
            .containsExactlyInAnyOrder(true, true, true);
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

    private Alarm createAlarm(Member member, boolean isOpened) {
        Alarm alarm = Alarm.builder()
            .isDeleted(false)
            .content("alarm content")
            .isOpened(isOpened)
            .member(member)
            .build();
        return alarmRepository.save(alarm);
    }
}