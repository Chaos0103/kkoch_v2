package com.kkoch.user.api.controller.member.request;

import com.kkoch.user.api.service.member.dto.MemberCreateServiceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class MemberCreateRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String loginPw;

    @NotBlank
    private String name;

    @NotBlank
    private String tel;

    @NotBlank
    private String businessNumber;

    @Builder
    private MemberCreateRequest(String email, String loginPw, String name, String tel, String businessNumber) {
        this.email = email;
        this.loginPw = loginPw;
        this.name = name;
        this.tel = tel;
        this.businessNumber = businessNumber;
    }

    public MemberCreateServiceRequest toJoinMemberDto() {
        return MemberCreateServiceRequest.builder()
            .email(this.email)
            .pwd(this.loginPw)
            .name(this.name)
            .tel(this.tel)
            .businessNumber(this.businessNumber)
            .build();
    }
}
