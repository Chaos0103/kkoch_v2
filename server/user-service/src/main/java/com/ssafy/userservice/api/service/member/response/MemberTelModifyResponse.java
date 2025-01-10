package com.ssafy.userservice.api.service.member.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberTelModifyResponse {

    private final String modifiedTel;
    private final LocalDateTime telModifiedDateTime;

    @Builder
    private MemberTelModifyResponse(String modifiedTel, LocalDateTime telModifiedDateTime) {
        this.modifiedTel = modifiedTel;
        this.telModifiedDateTime = telModifiedDateTime;
    }

    public static MemberTelModifyResponse of(String tel, LocalDateTime currentDateTime) {
        return new MemberTelModifyResponse(tel, currentDateTime);
    }
}
