package com.ssafy.user_service.domain.member.repository.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberIdResponse {

    private Long memberId;

    @Builder
    private MemberIdResponse(Long memberId) {
        this.memberId = memberId;
    }
}

