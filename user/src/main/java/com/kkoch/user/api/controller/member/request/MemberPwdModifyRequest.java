package com.kkoch.user.api.controller.member.request;

import com.kkoch.user.api.service.member.request.MemberPwdModifyServiceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class MemberPwdModifyRequest {

    @NotBlank(message = "현재 비밀번호를 입력해주세요.")
    private String currentPwd;

    @NotBlank(message = "새로운 비밀번호를 입력해주세요.")
    private String newPwd;

    @Builder
    private MemberPwdModifyRequest(String currentPwd, String newPwd) {
        this.currentPwd = currentPwd;
        this.newPwd = newPwd;
    }

    public MemberPwdModifyServiceRequest toServiceRequest() {
        return MemberPwdModifyServiceRequest.of(currentPwd, newPwd);
    }
}
