package com.kkoch.admin.api.controller.admin.request;

import com.kkoch.admin.api.service.admin.request.AdminCreateServiceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class AdminCreateRequest {

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String pwd;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "연락처를 입력해주세요.")
    private String tel;

    @NotBlank(message = "관리 권한을 입력해주세요.")
    private String auth;

    @Builder
    private AdminCreateRequest(String email, String pwd, String name, String tel, String auth) {
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.tel = tel;
        this.auth = auth;
    }

    public AdminCreateServiceRequest toServiceRequest() {
        return AdminCreateServiceRequest.of(email, pwd, name, tel, auth);
    }
}
