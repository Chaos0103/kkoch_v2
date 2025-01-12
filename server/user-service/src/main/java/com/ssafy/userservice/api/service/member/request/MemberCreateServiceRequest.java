package com.ssafy.userservice.api.service.member.request;

import com.ssafy.userservice.domain.member.Member;
import com.ssafy.userservice.domain.member.enums.Role;
import com.ssafy.userservice.domain.member.vo.Email;
import com.ssafy.userservice.domain.member.vo.Name;
import com.ssafy.userservice.domain.member.vo.Password;
import com.ssafy.userservice.domain.member.vo.Tel;
import lombok.Builder;

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

    public static MemberCreateServiceRequest of(String email, String password, String name, String tel, String role) {
        return new MemberCreateServiceRequest(email, password, name, tel, Role.of(role));
    }

    public Member toEntity() {
        return Member.create(role, getEmail(), getPassword(), getName(), getTel());
    }

    public Email getEmail() {
        return Email.of(email);
    }

    public Tel getTel() {
        return Tel.of(tel);
    }

    private Password getPassword() {
        return Password.of(password);
    }

    private Name getName() {
        return Name.of(name);
    }
}
