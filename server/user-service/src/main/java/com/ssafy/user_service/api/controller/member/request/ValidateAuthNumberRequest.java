package com.ssafy.user_service.api.controller.member.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ValidateAuthNumberRequest {

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "인증 번호를 입력해주세요.")
    private String authNumber;

    @Builder
    private ValidateAuthNumberRequest(String email, String authNumber) {
        this.email = email;
        this.authNumber = authNumber;
    }
}
