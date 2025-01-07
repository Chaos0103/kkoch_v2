package com.ssafy.userservice.api.service.member.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberIdInnerClientResponse {

    private final Long memberId;

    @Builder
    private MemberIdInnerClientResponse(Long memberId) {

        this.memberId = memberId;
    }

    public static MemberIdInnerClientResponse of(Long memberId) {
        return new MemberIdInnerClientResponse(memberId);
    }
}

