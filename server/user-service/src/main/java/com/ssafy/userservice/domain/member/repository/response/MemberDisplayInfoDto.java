package com.ssafy.userservice.domain.member.repository.response;

import static com.ssafy.userservice.api.service.member.Masking.maskingEmail;
import static com.ssafy.userservice.api.service.member.Masking.maskingTel;

public class MemberDisplayInfoDto {

    private String email;
    private String name;
    private String tel;

    public String getMaskingEmail() {
        return maskingEmail(email);
    }

    public String getUsername() {
        return name;
    }

    public String getMaskingTel() {
        return maskingTel(tel);
    }
}
