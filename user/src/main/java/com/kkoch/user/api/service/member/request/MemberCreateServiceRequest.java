package com.kkoch.user.api.service.member.request;

import com.kkoch.user.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberCreateServiceRequest {

    private final String email;
    private final String pwd;
    private final String name;
    private final String tel;
    private final String businessNumber;

    @Builder
    private MemberCreateServiceRequest(String email, String pwd, String name, String tel, String businessNumber) {
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.tel = tel;
        this.businessNumber = businessNumber;
    }

    public Member toEntity(String encryptedPwd) {
        return Member.create(email, encryptedPwd, name, tel, businessNumber);
    }
}
