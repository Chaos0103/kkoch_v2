package com.kkoch.user.api.controller.member.request;

import com.kkoch.user.api.service.member.request.MemberRemoveServiceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class MemberRemoveRequest {

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String pwd;

    @Builder
    private MemberRemoveRequest(String pwd) {
        this.pwd = pwd;
    }

    public MemberRemoveServiceRequest toServiceRequest() {
        return MemberRemoveServiceRequest.builder()
            .pwd(pwd)
            .build();
    }
}
