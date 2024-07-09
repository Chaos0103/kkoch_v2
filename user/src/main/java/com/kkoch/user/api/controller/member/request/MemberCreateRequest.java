package com.kkoch.user.api.controller.member.request;

import com.kkoch.user.api.service.member.request.MemberCreateServiceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class MemberCreateRequest {

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String loginPw;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "연락처를 입력해주세요.")
    private String tel;

    @NotBlank(message = "사업자 번호를 입력해주세요.")
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
