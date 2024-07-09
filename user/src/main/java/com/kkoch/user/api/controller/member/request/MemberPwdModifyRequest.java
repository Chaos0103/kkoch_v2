package com.kkoch.user.api.controller.member.request;

import com.kkoch.user.api.service.member.request.MemberPwdModifyServiceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class MemberPwdModifyRequest {

    @NotBlank
    private String currentPwd;

    @NotBlank
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
