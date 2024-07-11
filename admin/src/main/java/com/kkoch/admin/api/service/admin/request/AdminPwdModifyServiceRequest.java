package com.kkoch.admin.api.service.admin.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class AdminPwdModifyServiceRequest {

    private final String currentPwd;
    private final String newPwd;

    @Builder
    private AdminPwdModifyServiceRequest(String currentPwd, String newPwd) {
        this.currentPwd = currentPwd;
        this.newPwd = newPwd;
    }

    public static AdminPwdModifyServiceRequest of(String currentPwd, String newPwd) {
        return new AdminPwdModifyServiceRequest(currentPwd, newPwd);
    }
}
