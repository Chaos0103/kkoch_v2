package com.ssafy.user_service.api.controller.member.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ValidateTelRequest {

    private String tel;

    @Builder
    private ValidateTelRequest(String tel) {
        this.tel = tel;
    }
}
