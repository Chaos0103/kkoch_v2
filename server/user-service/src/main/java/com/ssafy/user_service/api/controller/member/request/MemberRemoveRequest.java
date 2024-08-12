package com.ssafy.user_service.api.controller.member.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberRemoveRequest {

    private String password;

    @Builder
    private MemberRemoveRequest(String password) {
        this.password = password;
    }
}
