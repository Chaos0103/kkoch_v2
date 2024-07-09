package com.kkoch.user.api.service.member.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberPwdModifyServiceRequest {

    private final String currentPwd;
    private final String newPwd;

    @Builder
    private MemberPwdModifyServiceRequest(String currentPwd, String newPwd) {
        this.currentPwd = currentPwd;
        this.newPwd = newPwd;
    }

    public static MemberPwdModifyServiceRequest of(String currentPwd, String newPwd) {
        return new MemberPwdModifyServiceRequest(currentPwd, newPwd);
    }
}
