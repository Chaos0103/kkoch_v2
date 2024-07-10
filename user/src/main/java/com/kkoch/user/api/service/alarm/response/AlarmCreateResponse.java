package com.kkoch.user.api.service.alarm.response;

import com.kkoch.user.domain.alarm.Alarm;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AlarmCreateResponse {

    private final long alarmId;
    private final LocalDateTime createdDateTime;

    @Builder
    private AlarmCreateResponse(long alarmId, LocalDateTime createdDateTime) {
        this.alarmId = alarmId;
        this.createdDateTime = createdDateTime;
    }

    public static AlarmCreateResponse of(Alarm alarm) {
        return new AlarmCreateResponse(alarm.getId(), alarm.getCreatedDateTime());
    }
}
