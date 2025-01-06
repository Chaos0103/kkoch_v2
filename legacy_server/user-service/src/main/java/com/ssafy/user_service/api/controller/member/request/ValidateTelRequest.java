package com.ssafy.user_service.api.controller.member.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ValidateTelRequest {

    @NotBlank(message = "연락처를 입력해주세요.")
    private String tel;

    @Builder
    private ValidateTelRequest(String tel) {
        this.tel = tel;
    }
}
