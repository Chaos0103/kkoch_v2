package com.kkoch.admin.api.controller.admin.request;

import com.kkoch.admin.api.service.admin.request.AdminPwdModifyServiceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class AdminPwdModifyRequest {

    @NotBlank(message = "현재 비밀번호를 입력해주세요.")
    private String currentPwd;

    @NotBlank(message = "신규 비밀번호를 입력해주세요.")
    private String newPwd;

    @Builder
    private AdminPwdModifyRequest(String currentPwd, String newPwd) {
        this.currentPwd = currentPwd;
        this.newPwd = newPwd;
    }

    public AdminPwdModifyServiceRequest toServiceRequest() {
        return AdminPwdModifyServiceRequest.of(currentPwd, newPwd);
    }
}
