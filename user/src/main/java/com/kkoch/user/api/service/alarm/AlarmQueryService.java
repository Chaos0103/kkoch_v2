package com.kkoch.user.api.service.alarm;

import com.kkoch.user.domain.alarm.repository.response.AlarmResponse;
import com.kkoch.user.domain.alarm.repository.AlarmQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AlarmQueryService {

    private final AlarmQueryRepository alarmQueryRepository;

    public List<AlarmResponse> searchAlarms(String memberKey) {
        return alarmQueryRepository.findTop10ByMemberKey(memberKey);
    }
}
