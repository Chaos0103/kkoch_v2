package com.ssafy.user_service.api.controller.member.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SendAuthNumberRequest {

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @Builder
    private SendAuthNumberRequest(String email) {
        this.email = email;
    }
}
