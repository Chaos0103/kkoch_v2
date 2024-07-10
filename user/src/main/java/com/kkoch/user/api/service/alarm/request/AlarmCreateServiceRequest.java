package com.kkoch.user.api.service.alarm.request;

import com.kkoch.user.domain.alarm.Alarm;
import com.kkoch.user.domain.member.Member;
import lombok.Builder;

public class AlarmCreateServiceRequest {

    private final String content;

    @Builder
    private AlarmCreateServiceRequest(String content) {
        this.content = content;
    }

    public static AlarmCreateServiceRequest of(String content) {
        return new AlarmCreateServiceRequest(content);
    }

    public Alarm toEntity(Member member) {
        return Alarm.create(content, member);
    }
}
