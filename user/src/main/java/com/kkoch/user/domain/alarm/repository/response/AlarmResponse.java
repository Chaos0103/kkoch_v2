package com.kkoch.user.domain.alarm.repository.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class AlarmResponse {

    private long alarmId;
    private String content;
    private Boolean isOpened;
    private LocalDateTime createdDateTime;

    @Builder
    private AlarmResponse(Long alarmId, String content, boolean isOpened, LocalDateTime createdDateTime) {
        this.alarmId = alarmId;
        this.content = content;
        this.isOpened = isOpened;
        this.createdDateTime = createdDateTime;
    }
}
