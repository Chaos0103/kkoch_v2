package com.kkoch.user.api.service.member.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberPwdModifyServiceRequest {

    private final String currentPwd;
    private final String nextPwd;

    @Builder
    private MemberPwdModifyServiceRequest(String currentPwd, String nextPwd) {
        this.currentPwd = currentPwd;
        this.nextPwd = nextPwd;
    }

    public static MemberPwdModifyServiceRequest of(String currentPwd, String nextPwd) {
        return new MemberPwdModifyServiceRequest(currentPwd, nextPwd);
    }
}
