package com.ssafy.userservice.api.service.member.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberPasswordModifyResponse {

    private final LocalDateTime passwordModifiedDateTime;

    @Builder
    private MemberPasswordModifyResponse(LocalDateTime passwordModifiedDateTime) {
        this.passwordModifiedDateTime = passwordModifiedDateTime;
    }

    public static MemberPasswordModifyResponse of(LocalDateTime currentDateTime) {
        return new MemberPasswordModifyResponse(currentDateTime);
    }
}
