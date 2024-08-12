package com.ssafy.user_service.api.controller.member.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberRemoveRequest {

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @Builder
    private MemberRemoveRequest(String password) {
        this.password = password;
    }
}
