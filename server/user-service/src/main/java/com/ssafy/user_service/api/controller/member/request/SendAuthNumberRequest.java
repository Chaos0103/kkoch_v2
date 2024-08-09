package com.ssafy.user_service.api.controller.member.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SendAuthNumberRequest {

    private String email;

    @Builder
    private SendAuthNumberRequest(String email) {
        this.email = email;
    }
}
