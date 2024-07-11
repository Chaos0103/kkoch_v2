package com.kkoch.admin.api.service.admin.request;

import com.kkoch.admin.domain.admin.Admin;
import com.kkoch.admin.domain.admin.AdminAuth;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class AdminCreateServiceRequest {

    private final String email;
    private final String pwd;
    private final String name;
    private final String tel;
    private final String auth;

    @Builder
    private AdminCreateServiceRequest(String email, String pwd, String name, String tel, String auth) {
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.tel = tel;
        this.auth = auth;
    }

    public static AdminCreateServiceRequest of(String email, String pwd, String name, String tel, String auth) {
        return new AdminCreateServiceRequest(email, pwd, name, tel, auth);
    }

    public Admin toEntity(String encodedPwd) {
        AdminAuth auth = AdminAuth.valueOf(this.auth);
        return Admin.create(email, encodedPwd, name, tel, auth);
    }
}
