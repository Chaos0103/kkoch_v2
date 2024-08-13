package com.ssafy.user_service.api.service.member.request;

import lombok.Builder;
import lombok.Getter;

import static com.ssafy.user_service.api.service.member.MemberValidate.validateTel;

@Getter
public class MemberTelModifyServiceRequest {

    private final String tel;

    @Builder
    private MemberTelModifyServiceRequest(String tel) {
        this.tel = tel;
    }

    public static MemberTelModifyServiceRequest of(String tel) {
        validateTel(tel);

        return new MemberTelModifyServiceRequest(tel);
    }
}
