package com.ssafy.userservice.api.service.member.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberRemoveResponse {

    private final LocalDateTime withdrawnDateTime;

    @Builder
    private MemberRemoveResponse(LocalDateTime withdrawnDateTime) {
        this.withdrawnDateTime = withdrawnDateTime;
    }


    public static MemberRemoveResponse of(LocalDateTime currentDateTime) {
        return new MemberRemoveResponse(currentDateTime);
    }
}
