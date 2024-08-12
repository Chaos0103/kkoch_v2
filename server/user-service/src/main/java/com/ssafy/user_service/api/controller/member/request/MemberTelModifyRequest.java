package com.ssafy.user_service.api.controller.member.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberTelModifyRequest {

    private String tel;

    @Builder
    private MemberTelModifyRequest(String tel) {
        this.tel = tel;
    }
}
