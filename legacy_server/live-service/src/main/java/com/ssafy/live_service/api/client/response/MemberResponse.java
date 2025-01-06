package com.ssafy.live_service.api.client.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponse {

    private Long memberId;
    private String memberKey;

    @Builder
    private MemberResponse(Long memberId, String memberKey) {
        this.memberId = memberId;
        this.memberKey = memberKey;
    }
}
