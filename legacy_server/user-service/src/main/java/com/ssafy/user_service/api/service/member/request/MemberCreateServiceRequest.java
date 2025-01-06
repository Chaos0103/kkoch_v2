package com.ssafy.user_service.api.service.member.request;

import com.ssafy.user_service.domain.member.Member;
import com.ssafy.user_service.domain.member.repository.MemberRepository;
import com.ssafy.user_service.domain.member.vo.Role;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.ssafy.user_service.domain.member.MemberValidate.*;

public class MemberCreateServiceRequest {

    private final String email;
    private final String password;
    private final String name;
    private final String tel;
    private final Role role;

    @Builder
    private MemberCreateServiceRequest(String email, String password, String name, String tel, String role) {
        this.email = validateEmail(email);
        this.password = validatePassword(password);
        this.name = validateName(name);
        this.tel = validateTel(tel);
        this.role = validateRole(role);
    }

    public static MemberCreateServiceRequest of(String email, String password, String name, String tel, String role) {
        return new MemberCreateServiceRequest(email, password, name, tel, role);
    }

    public Member toEntity(PasswordEncoder encoder) {
        String encodedPwd = encoder.encode(password);
        return Member.create(role, email, encodedPwd, name, tel);
    }

    public boolean isDuplicatedEmail(MemberRepository memberRepository) {
        return memberRepository.existsByEmail(email);
    }

    public boolean isDuplicatedTel(MemberRepository memberRepository) {
        return memberRepository.existsByTel(tel);
    }
}
