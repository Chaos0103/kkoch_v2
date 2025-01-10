package com.ssafy.userservice.api.service.member.request;

import com.ssafy.userservice.domain.member.Member;
import com.ssafy.userservice.domain.member.enums.Role;
import com.ssafy.userservice.domain.member.vo.Email;
import com.ssafy.userservice.domain.member.vo.Tel;
import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MemberCreateServiceRequest {

    private final String email;
    private final String password;
    private final String name;
    private final String tel;
    private final Role role;

    @Builder
    private MemberCreateServiceRequest(String email, String password, String name, String tel, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.tel = tel;
        this.role = role;
    }

    public Member toEntity(PasswordEncoder encoder) {
        return Member.create(role, email, password, name, tel, encoder);
    }

    public Email getEmail() {
        return Email.of(email);
    }

    public Tel getTel() {
        return Tel.of(tel);
    }
}
