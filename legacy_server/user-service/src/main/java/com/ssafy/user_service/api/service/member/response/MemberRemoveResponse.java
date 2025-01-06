package com.ssafy.user_service.api.service.member.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemberRemoveResponse {

    private LocalDateTime withdrawnDateTime;

    @Builder
    private MemberRemoveResponse(LocalDateTime withdrawnDateTime) {
        this.withdrawnDateTime = withdrawnDateTime;
    }

    public static MemberRemoveResponse of(LocalDateTime withdrawnDateTime) {
        return new MemberRemoveResponse(withdrawnDateTime);
    }
}
