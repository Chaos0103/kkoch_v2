package com.ssafy.user_service.api.service.member.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemberPasswordModifyResponse {

    private LocalDateTime passwordModifiedDateTime;

    @Builder
    private MemberPasswordModifyResponse(LocalDateTime passwordModifiedDateTime) {
        this.passwordModifiedDateTime = passwordModifiedDateTime;
    }

    public static MemberPasswordModifyResponse of(LocalDateTime currentDateTime) {
        return new MemberPasswordModifyResponse(currentDateTime);
    }
}
