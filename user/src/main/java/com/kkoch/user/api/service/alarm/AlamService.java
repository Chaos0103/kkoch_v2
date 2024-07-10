package com.kkoch.user.api.service.alarm;

import com.kkoch.user.api.service.alarm.request.AlarmCreateServiceRequest;
import com.kkoch.user.api.service.alarm.response.AlarmCreateResponse;
import com.kkoch.user.domain.alarm.Alarm;
import com.kkoch.user.domain.alarm.repository.AlarmQueryRepository;
import com.kkoch.user.domain.alarm.repository.AlarmRepository;
import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class AlamService {

    private final AlarmRepository alarmRepository;
    private final AlarmQueryRepository alarmQueryRepository;
    private final MemberRepository memberRepository;

    public AlarmCreateResponse createAlarm(String memberKey, AlarmCreateServiceRequest request) {
        Member member = memberRepository.findByMemberKey(memberKey)
            .orElseThrow(() -> new NoSuchElementException("등록되지 않은 회원입니다."));

        Alarm alarm = request.toEntity(member);
        Alarm savedAlarm = alarmRepository.save(alarm);

        return AlarmCreateResponse.of(savedAlarm);
    }

    public int openAllAlarm(String memberKey) {
        List<Alarm> alarms = alarmQueryRepository.findAllByMemberKey(memberKey);

        alarms.forEach(Alarm::open);

        return alarms.size();
    }
}
