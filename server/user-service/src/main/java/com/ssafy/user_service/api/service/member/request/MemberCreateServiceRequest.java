package com.ssafy.user_service.api.service.member.request;

import com.ssafy.user_service.domain.member.Member;
import com.ssafy.user_service.domain.member.vo.Role;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.ssafy.user_service.domain.member.MemberValidate.*;

@Getter
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
        validateEmail(email);
        validatePassword(password);
        validateName(name);
        validateTel(tel);
        return new MemberCreateServiceRequest(email, password, name, tel, Role.valueOf(role));
    }

    public Member toEntity(PasswordEncoder encoder) {
        String encodedPwd = encoder.encode(password);
        return Member.create(role, email, encodedPwd, name, tel);
    }
}
