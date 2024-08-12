package com.ssafy.user_service.api.service.member.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemberTelModifyResponse {

    private String modifiedTel;
    private LocalDateTime telModifiedDateTime;

    @Builder
    private MemberTelModifyResponse(String modifiedTel, LocalDateTime telModifiedDateTime) {
        this.modifiedTel = modifiedTel;
        this.telModifiedDateTime = telModifiedDateTime;
    }

    public static MemberTelModifyResponse of(String modifiedTel, LocalDateTime telModifiedDateTime) {
        return new MemberTelModifyResponse(modifiedTel, telModifiedDateTime);
    }
}
